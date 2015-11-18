package com.nemyrovskiy.o.gh2_nemyrovskyi.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class City implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
}
