package com.anild.springpt4.service;

import com.anild.springpt4.model.ChatGPTRequest;
import com.anild.springpt4.model.ChatGPTResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ChatGPTService {

    @Value("${GPT_KEY}")
    private String GPT_KEY;

    @Value("${GPT_URL}")
    private String GPT_URL;
    public String processSearch(String query) throws IOException {

        ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        chatGPTRequest.setPrompt(query);


        String url = GPT_URL;
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization","Bearer " + GPT_KEY);

        Gson gson = new Gson();
        String body = gson.toJson(chatGPTRequest);
        log.info("body: " + body );

        final StringEntity entity = new StringEntity(body);
        post.setEntity(entity);

        try(CloseableHttpClient httpClient = HttpClients.custom().build();CloseableHttpResponse response = httpClient.execute(post)){

            String responseBody = EntityUtils.toString(response.getEntity());

            log.info("Response body: " + responseBody);
            ChatGPTResponse chatGPTResponse = gson.fromJson(responseBody, ChatGPTResponse.class);
            return chatGPTResponse.getChoices().get(0).getText();
        }catch (Exception e){
            return "failed";
        }


    }
}
