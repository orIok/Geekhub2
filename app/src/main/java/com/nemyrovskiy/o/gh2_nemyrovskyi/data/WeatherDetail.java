package com.nemyrovskiy.o.gh2_nemyrovskyi.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WeatherDetail implements Serializable {
    @SerializedName("city")
    public City city;
    @SerializedName("cod")
    public int responseStatus;
    @SerializedName("message")
    public double message;
    @SerializedName("list")
    public Weather[] weathers;

}
