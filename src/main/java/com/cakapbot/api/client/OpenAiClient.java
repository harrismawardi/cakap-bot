package com.cakapbot.api.client;

import com.cakapbot.api.interfaces.LlmClient;
import com.cakapbot.api.model.ai.PromptSpec;
import com.cakapbot.api.model.chat.BotTurn;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.StructuredResponseCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("openAiClient")
public class OpenAiClient implements LlmClient {
    private OpenAIClient client;

    @Value("${openai.api.key}")
    private String apiKey;

    @PostConstruct
    private void postConstruct() {
        client = OpenAIOkHttpClient.builder().apiKey(apiKey).build();
    }

    public BotTurn complete(PromptSpec spec) {
        StructuredResponseCreateParams<BotTurn> params = ResponseCreateParams.builder()
                .input(spec.user())
                .model(ChatModel.GPT_5_NANO)
                .text(BotTurn.class)
                .build();
        return client.responses().create(params)
                .output()
                .getLast()
                .asMessage()
                .content()
                .getFirst()
                .asOutputText();
    }


}
