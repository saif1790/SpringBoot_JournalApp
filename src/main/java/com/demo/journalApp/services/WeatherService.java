package com.demo.journalApp.services;


import com.demo.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String apiKey = "33de8808dc2a437097752726242105";

    private static final String weatherAPI = "http://api.weatherapi.com/v1/current.json?q=CITY&key=API_KEY";
    /*https://api.weatherapi.com/v1/current.json?q=noida&key=33de8808dc2a437097752726242105*/

    @Autowired
    RestTemplate restTemplate;

    public WeatherResponse getWeather(String city)
    {
        String actualUrl = weatherAPI.replace("CITY", city).replace("API_KEY", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(actualUrl, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }

}
