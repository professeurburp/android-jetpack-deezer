package com.professeurburp.deezerapitest.data.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * AlbumOverview is a model entity that is used to store basic album info.
 * It is used to display simple album info.
 */
@Entity
public class AlbumOverview {

    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("cover")
    private String cover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AlbumOverview)) {
            return false;
        }

        AlbumOverview otherAlbum = (AlbumOverview) obj;

        return Objects.equals(id, otherAlbum.getId()) &&
                Objects.equals(title, otherAlbum.getTitle()) &&
                Objects.equals(cover, otherAlbum.getCover());
    }
}
