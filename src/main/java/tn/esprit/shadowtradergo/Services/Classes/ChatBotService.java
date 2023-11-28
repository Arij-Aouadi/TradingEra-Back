package tn.esprit.shadowtradergo.Services.Classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.ChatBot;
import tn.esprit.shadowtradergo.Services.Interfaces.IChatBotService;
import tn.esprit.shadowtradergo.openai.Answer;
import tn.esprit.shadowtradergo.openai.Call;
import tn.esprit.shadowtradergo.openai.Choice;
import tn.esprit.shadowtradergo.openai.OutputDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
@Slf4j
@Service


public class ChatBotService implements IChatBotService {
        @Value("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")
    private String openaiApiKey;

    private ObjectMapper jsonMapper;

    @Value("${url}")
    private String URL;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.maxTokens}")
    private Integer max_tokens;

    @Value("${openai.temperature}")
    private Double temperature;

    private final HttpClient client = HttpClient.newHttpClient();

    public ChatBotService(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }
    @Override
    public String sendChatgptRequest(String body) throws IOException, InterruptedException {
     HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    @Override
    public OutputDto sendPrompt(String prompt) throws Exception {
        Call call = new Call(model, prompt, max_tokens, temperature);
        String responseBody = sendChatgptRequest(jsonMapper.writeValueAsString(call));
        System.out.println(responseBody);
        Answer answer = jsonMapper.readValue(responseBody, Answer.class);

        String text = null;
        if (answer != null && answer.getChoices() != null && !answer.getChoices().isEmpty()) {
            text = answer.getChoices().get(0).getText();
        }

        if (text == null) {
            // Handle the case when the response is empty or doesn't contain any choices
            throw new RuntimeException("Invalid response from the model");
        }

        OutputDto outputDto = new OutputDto(prompt, text);
        return outputDto;
    }
        //return null;

    }










