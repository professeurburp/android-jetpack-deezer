package com.professeurburp.deezerapitest.data.rest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DeezerListResponse<T> {
    @SerializedName("data")
    private List<T> values;
    private String checksum;
    private int total;
    private String next;

    public int getTotalResultsCount() {
        return total;
    }

    public List<T> getValues() {
        if (values == null) {
            values = new ArrayList<>();
        }

        return values;
    }

    public void setValues(List<T> values) {
        if (values == null) {
            values = new ArrayList<>();
        }

        this.values = values;
    }
}