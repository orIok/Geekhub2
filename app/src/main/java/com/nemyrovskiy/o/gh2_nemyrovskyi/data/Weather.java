package com.nemyrovskiy.o.gh2_nemyrovskyi.data;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weather implements Serializable {
    @SerializedName("dt")
    public String date;
    @SerializedName("main")
    public WeatherMain main;
    @SerializedName("weather")
    public WeatherDetails[] details;
    @SerializedName("wind")
    public WindDetails wind;
    @SerializedName("dt_txt")
    public String dtTxt;


    public class WeatherMain implements Serializable {
        @SerializedName("temp")
        public double temp;
        @SerializedName("temp_min")
        public double tempMin;
        @SerializedName("temp_max")
        public double tempMax;
        @SerializedName("pressure")
        public double pressure;
        @SerializedName("sea_level")
        public double seaLvl;
        @SerializedName("grnd_level")
        public double grndLvl;
        @SerializedName("humidity")
        public double humidity;
        @SerializedName("temp_kf")
        public double tempKf;


    }

    public class WeatherDetails implements Serializable {
        @SerializedName("id")
        public int id;
        @SerializedName("main")
        public String main;
        @SerializedName("description")
        public String description;
        @SerializedName("icon")
        public String icon;
    }

    public class WindDetails implements Serializable {

        @SerializedName("speed")
        public double speed;
        @SerializedName("deg")
        public double deg;
    }


}
