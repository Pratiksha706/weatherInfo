
        package com.frightfox.poc.controller;

        import org.springframework.web.bind.annotation.*;

import com.frightfox.poc.model.WeatherInfo;
import com.frightfox.poc.service.WeatherService;

import org.springframework.http.ResponseEntity;
        import java.time.LocalDate;

        @RestController
        @RequestMapping("/api/weather")
        public class WeatherController {
            private final WeatherService weatherService;

            public WeatherController(WeatherService weatherService) {
                this.weatherService = weatherService;
            }

            @GetMapping
            public ResponseEntity<WeatherInfo> getWeather(@RequestParam String pincode, @RequestParam String forDate) {
                LocalDate date = LocalDate.parse(forDate);
                WeatherInfo weatherInfo = weatherService.getWeatherInfo(pincode, date);
                return ResponseEntity.ok(weatherInfo);
            }
        }
        