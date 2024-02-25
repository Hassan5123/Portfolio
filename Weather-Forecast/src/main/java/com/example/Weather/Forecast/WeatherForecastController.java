package com.example.Weather.Forecast;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@RestController // Marks the class as a controller where every method returns a domain object instead of a view
public class WeatherForecastController {
    // RestTemplate is used to make HTTP requests to external services
    private final RestTemplate restTemplate = new RestTemplate();
    // ObjectMapper is used to convert objects to/from JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    // @GetMapping annotation indicates that this method will process HTTP GET requests
    @GetMapping("/getWeather")
    // The method returns a map containing string as key and object as value
    public Map<String, Object> getWeather(@RequestParam String city) { //@RequestParam: Indicates that the method parameter should be bound to a web request parameter.
        String apiKey = "bb8e5f6438984a5c9955023d76b06730"; // My Unique Weatherbit API key
        // apiUrl is the URL to which the request will be sent
        String apiUrl = "https://api.weatherbit.io/v2.0/current?city=" + city + "&key=" + apiKey;

        try {
            // Fetch the response from the Weatherbit API as a string
            String response = restTemplate.getForObject(apiUrl, String.class);
            // Convert the JSON string to a Map and return it
            return objectMapper.readValue(response, Map.class);
        } catch (Exception e) {
            // In case of an error, return a map with an error message
            return Map.of("error", "Error fetching weather data: " + e.getMessage());
        }
    }

    // Method to get the weekly weather forecast
    @GetMapping("/getWeeklyForecast")
    public Map<String, Object> getWeeklyForecast(@RequestParam String city) {
        String apiKey = "bb8e5f6438984a5c9955023d76b06730";
        String apiUrl = "https://api.weatherbit.io/v2.0/forecast/daily?city=" + city + "&key=" + apiKey;

        try {
            String response = restTemplate.getForObject(apiUrl, String.class);
            return objectMapper.readValue(response, Map.class);
        } catch (Exception e) {
            return Map.of("error", "Error fetching weekly forecast data: " + e.getMessage());
        }
    }

}
