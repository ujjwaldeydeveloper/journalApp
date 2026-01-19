package com.example.journalApp.service;

import com.example.journalApp.api_response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey = "0098e097dd64e3d72c5f4632c70f6dd3";
    private static final String API = "http://api.weatherstack.com/current?access_key=" + apiKey + "&query=";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String url = API + city;
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
