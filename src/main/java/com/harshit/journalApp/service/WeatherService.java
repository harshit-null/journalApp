package com.harshit.journalApp.service;


import com.harshit.journalApp.api_response.WeatherResponse;
import com.harshit.journalApp.cache.AppCache;
import com.harshit.journalApp.constant.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${my.apiKey}")
    private String apiKey;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather of " + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
            String finalAPI = appCache.appCache.get(AppCache.keys.weather_api.toString()).replace(PlaceHolders.CITY, city).replace(PlaceHolders.API_KEY, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            response.getStatusCode();
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather of " + city, body, 300l);
            }
            return body;

        }

    }

}
