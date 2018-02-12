package com.ashishlakhmani.homeautomation;

public class TemperatureDetails {
    private String temperature;
    private String humidity;
    private String dateTime;

    public TemperatureDetails(String temperature, String humidity, String dateTime) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.dateTime = dateTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getDateTime() {
        return dateTime;
    }
}
