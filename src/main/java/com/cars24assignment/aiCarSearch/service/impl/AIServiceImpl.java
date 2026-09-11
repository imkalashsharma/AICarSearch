package com.cars24assignment.aiCarSearch.service.impl;

import com.cars24assignment.aiCarSearch.config.AppConfig;
import com.cars24assignment.aiCarSearch.exception.AIServiceException;
import com.cars24assignment.aiCarSearch.service.AIService;
import com.google.genai.Client;
import com.google.genai.gaos.models.errors.CreateInteractionClientError;
import com.google.genai.gaos.models.interactions.*;
import com.google.genai.gaos.models.operations.CreateInteractionRequestBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AIServiceImpl implements AIService {
    private final Client client;        // gemini client

    public AIServiceImpl(AppConfig appConfig) {
        this.client = Client.builder().apiKey(appConfig.getAiKey()).build();
    }

    @Override
    public String askGemini(String prompt) {
        CreateModelInteraction params = CreateModelInteraction
                .builder()
                .model(Model.of("gemini-3.8-flash"))
                .input(InteractionsInput.of(prompt))
                .build();

        try {
            Interaction interaction = client.interactions.create(CreateInteractionRequestBody.of(params))
                    .interaction()
                    .get();

            return extractOutput(interaction);
        } catch (CreateInteractionClientError ex) {
            if (ex.getMessage() != null && ex.getMessage().contains("429")) {

                throw new AIServiceException("AI service quota exceeded. Please try again later.");
            }

            throw new AIServiceException("AI service is currently unavailable.");
        }
    }

    private String extractOutput(Interaction interaction) {
        ModelOutputStep outputStep = interaction.steps()
                .orElseThrow(() -> new IllegalStateException("No steps returned"))
                .stream()
                .filter(ModelOutputStep.class::isInstance)
                .map(ModelOutputStep.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No model output found"));

        String output = outputStep.content()
                .orElse(List.of())
                .stream()
                .filter(TextContent.class::isInstance)
                .map(TextContent.class::cast)
                .map(TextContent::text)
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No text in model output"));

        return output.replace("```json", "")
                    .replace("```", "")
                    .trim();
    }
}
