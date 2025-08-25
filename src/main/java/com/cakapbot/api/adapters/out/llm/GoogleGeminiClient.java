package com.cakapbot.api.adapters.out.llm;

import com.cakapbot.api.ports.LlmClient;
import com.cakapbot.api.domain.ai.PromptSpec;
import com.cakapbot.api.domain.chat.BotTurn;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("googleGeminiClient")
public class GoogleGeminiClient implements LlmClient {

    @Value("${google.api.key}")
    private String apiKey;

    private Client client;

    @PostConstruct
    private void postConstruct() {
        client = Client.builder().apiKey(apiKey).build();
    }



    public BotTurn complete(PromptSpec spec) {
        String geminiSystemPrompt = spec.system() + "Do not add any text / characters other than the approved JSON format";


        Content systemInstruction = Content.fromParts(Part.fromText(spec.system()));

        Map<String, Schema> responseSchema = Map.of(
                "message", Schema.builder().type(Type.Known.STRING).build(),
                "translation", Schema.builder().type(Type.Known.STRING).build(),
                "hints", Schema.builder().type(Type.Known.ARRAY).items(Schema.builder().type(Type.Known.STRING)).build()
        );

        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseJsonSchema(Schema.builder().type(Type.Known.OBJECT).properties(responseSchema).build())
                .systemInstruction(systemInstruction)
                .build();

        GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash-lite",
                spec.user(), config);
        String llmMessage = response.text();

        ObjectMapper mapper = new ObjectMapper();
        try {

            if (llmMessage.startsWith("```json")) {
                llmMessage = llmMessage.replace("```json", "").replace("```", "");
            }
            return mapper.readValue(llmMessage, BotTurn.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("could not parse botTurn from llm response");
        }
    }

}
