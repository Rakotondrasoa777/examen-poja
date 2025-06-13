package com.exam.hazavao.endpoint.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {
    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getDefinitionInMalagasy(String word) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost request = new HttpPost(apiUrl);
            request.setHeader("Authorization", "Bearer " + apiKey);
            request.setHeader("Content-Type", "application/json");

            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-3.5-turbo");

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "user", "content", "Hazavao amin'ny teny malagasy ny teny hoe: " + word));
            body.put("messages", messages);

            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(body));
            request.setEntity(entity);

            return httpClient.execute(request, response ->
                    objectMapper.readTree(response.getEntity().getContent())
                            .get("choices")
                            .get(0)
                            .get("message")
                            .get("content")
                            .asText()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return "Nisy olana teo am-pamaranana ilay teny.";
        }
    }
}
