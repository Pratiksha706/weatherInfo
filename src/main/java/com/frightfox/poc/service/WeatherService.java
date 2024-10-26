
package com.frightfox.poc.service;

import org.springframework.stereotype.Service;

import com.frightfox.poc.model.PincodeLocation;
import com.frightfox.poc.model.WeatherInfo;
import com.frightfox.poc.repository.PincodeLocationRepository;
import com.frightfox.poc.repository.WeatherInfoRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherService {
	private final WeatherInfoRepository weatherInfoRepository;
	private final PincodeLocationRepository pincodeLocationRepository;
	private final RestTemplate restTemplate;
	private final Cache<String, PincodeLocation> locationCache;

	private final String OPEN_WEATHER_API_KEY = "YOUR_API_KEY";
	private final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather";
	private final String GEOCODING_API_URL = "https://api.openweathermap.org/geo/1.0/zip";

	public WeatherService(WeatherInfoRepository weatherInfoRepository,
			PincodeLocationRepository pincodeLocationRepository, RestTemplateBuilder restTemplateBuilder) {
		this.weatherInfoRepository = weatherInfoRepository;
		this.pincodeLocationRepository = pincodeLocationRepository;
		this.restTemplate = restTemplateBuilder.build();
		this.locationCache = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(100).build();
	}

	public WeatherInfo getWeatherInfo(String pincode, LocalDate date) {
		return weatherInfoRepository.findByPincodeAndDate(pincode, date)
				.orElseGet(() -> fetchAndSaveWeatherInfo(pincode, date));
	}

	private WeatherInfo fetchAndSaveWeatherInfo(String pincode, LocalDate date) {
		PincodeLocation location = locationCache.get(pincode, this::fetchGeolocation);
		Map<String, Object> weatherData = fetchWeatherData(location);

		WeatherInfo weatherInfo = new WeatherInfo(null, pincode, null, pincode, 0);
		weatherInfo.setPincode(pincode);
		weatherInfo.setDate(date);
		weatherInfo.setWeatherDescription((String) weatherData.get("description"));
		weatherInfo.setTemperature((Double) weatherData.get("temp"));

		weatherInfoRepository.save(weatherInfo);
		return weatherInfo;
	}

	private PincodeLocation fetchGeolocation(String pincode) {
		PincodeLocation location = pincodeLocationRepository.findByPincode(pincode).orElseGet(() -> {
			String url = String.format("%s?zip=%s&appid=%s", GEOCODING_API_URL, pincode, OPEN_WEATHER_API_KEY);
			Map<String, Double> response = restTemplate.getForObject(url, Map.class);

			PincodeLocation newLocation = new PincodeLocation(url, 0, 0);
			newLocation.setPincode(pincode);
			newLocation.setLatitude(response.get("lat"));
			newLocation.setLongitude(response.get("lon"));

			pincodeLocationRepository.save(newLocation);
			return newLocation;
		});
		locationCache.put(pincode, location);
		return location;
	}

	private Map<String, Object> fetchWeatherData(PincodeLocation location) {
		String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric", WEATHER_API_URL, location.getLatitude(),
				location.getLongitude(), OPEN_WEATHER_API_KEY);

		Map<String, Object> response = restTemplate.getForObject(url, Map.class);
		return (Map<String, Object>) ((Map<String, Object>) response.get("main"));
	}
}
