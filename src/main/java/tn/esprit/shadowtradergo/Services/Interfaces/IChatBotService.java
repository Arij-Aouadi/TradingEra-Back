package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.openai.OutputDto;

import java.io.IOException;

public interface IChatBotService {
     String sendChatgptRequest(String body) throws IOException, InterruptedException;
        OutputDto sendPrompt(String prompt) throws Exception;
}
