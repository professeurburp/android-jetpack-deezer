package com.professeurburp.deezerapitest.data.rest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeezerUserResponse<T> {
    @SerializedName("data")
    private List<T> values;
    private String checksum;
    private int total;
    private String next;

    public int getTotalResultsCount() {
        return total;
    }

    public List<T> getValues() {
        return values;
    }
}