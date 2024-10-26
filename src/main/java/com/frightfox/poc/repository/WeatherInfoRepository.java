
        package com.frightfox.poc.repository;

        import org.springframework.data.jpa.repository.JpaRepository;

import com.frightfox.poc.model.WeatherInfo;

import java.time.LocalDate;
        import java.util.Optional;

        public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
            Optional<WeatherInfo> findByPincodeAndDate(String pincode, LocalDate date);
        }
        