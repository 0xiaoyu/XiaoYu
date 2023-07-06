package com.yu.utils;

import com.yu.utils.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest
public class TranslationTest {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testTranslation(){
        String s = translationService.getTranslation("An object that maps keys to values.");
    }

    @Test
    public void restTemplate(){

        String url = "http://localhost:10001/utils/randomCode";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> m = new LinkedMultiValueMap<>();
        m.add("i","3");
        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(m,headers);

        Map map = restTemplate.postForObject(url, request, Map.class);
        System.out.println(map);
        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(url, m, Map.class);
        Map body = mapResponseEntity.getBody();
        System.out.println(body);
    }
}
