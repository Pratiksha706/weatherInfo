
        package com.frightfox.poc.model;

        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import java.time.LocalDate;

        @Entity
        public class WeatherInfo {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;
            private String pincode;
            private LocalDate date;
            private String weatherDescription;
            private double temperature;
            
            
			public WeatherInfo(Long id, String pincode, LocalDate date, String weatherDescription, double temperature) {
				super();
				this.id = id;
				this.pincode = pincode;
				this.date = date;
				this.weatherDescription = weatherDescription;
				this.temperature = temperature;
			}
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			public String getPincode() {
				return pincode;
			}
			public void setPincode(String pincode) {
				this.pincode = pincode;
			}
			public LocalDate getDate() {
				return date;
			}
			public void setDate(LocalDate date) {
				this.date = date;
			}
			public String getWeatherDescription() {
				return weatherDescription;
			}
			public void setWeatherDescription(String weatherDescription) {
				this.weatherDescription = weatherDescription;
			}
			public double getTemperature() {
				return temperature;
			}
			public void setTemperature(double temperature) {
				this.temperature = temperature;
			}

            
        }
        