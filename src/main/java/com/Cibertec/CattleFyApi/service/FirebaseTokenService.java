package com.Cibertec.CattleFyApi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseTokenService {

    @Value("${firebase.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String exchangeCustomTokenForIdToken(String customToken) {

        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=" + apiKey;

        Map<String, Object> body = new HashMap<>();
        body.put("token", customToken);
        body.put("returnSecureToken", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        return (String) response.getBody().get("idToken");
    }
}

