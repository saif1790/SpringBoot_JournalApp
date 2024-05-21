package com.demo.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
public class WeatherResponse {
    private Location location;
    private Current current;

    @Getter
    @Setter
    public static class Current {
        @JsonProperty("last_updated_epoch")
        private int lastUpdatedEpoch;
        @JsonProperty("last_updated")
        private String lastUpdated;
        @JsonProperty("temp_c")
        private int tempC;
        @JsonProperty("temp_f")
        private double tempF;
        @JsonProperty("is_day")
        private int isDay;
        @JsonProperty("wind_mph")
        private double windMph;
        @JsonProperty("wind_kph")
        private int windKph;
        @JsonProperty("humidity")
        private int humidity;
        @JsonProperty("feelslike_c")
        private double feelslikeC;
        @JsonProperty("feelslike_f")
        private double feelslikeF;
        @Autowired
        private Condition condition;
    }

    @Setter
    @Getter
    public static class Location{
        @JsonProperty("name")
        private String cityName;
        private String region;
        private String country;
        private double lat;
        private double lon;

        @JsonProperty("tz_id")
        private String tzId;

        @JsonProperty("localtime_epoch")
        private int localtimeEpoch;

        @JsonProperty("localtime")
        private String localTime;
    }
}




