package com.professeurburp.deezerapitest.data.model;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.professeurburp.deezerapitest.data.persistence.converters.AlbumDetailsTypeConverters;
import com.professeurburp.deezerapitest.data.rest.DeezerListResponse;

import org.itishka.gsonflatten.Flatten;

import java.util.Date;
import java.util.List;

@Entity
@TypeConverters(AlbumDetailsTypeConverters.class)
public class AlbumDetails extends AlbumOverview {

    @Expose
    @Flatten("artist::name")
    private String artistName;

    @Expose
    @Flatten("artist::picture")
    private String artistPicture;

    @Expose
    @SerializedName("release_date")
    private Date releaseDate;

    @Expose
    @SerializedName("duration")
    private int duration;

    @Expose
    @SerializedName("fans")
    private int fanNumber;

    @Expose
    @SerializedName("rating")
    private int rating;

    @Expose
    @SerializedName("nb_tracks")
    private int trackNumber;

    @Expose
    @SerializedName("tracks")
    private DeezerListResponse<TrackInfo> trackList;


    // --- Getters ---
    public String getArtistName() {
        return artistName;
    }

    public String getArtistPicture() {
        return artistPicture;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public int getFanNumber() {
        return fanNumber;
    }

    public int getRating() {
        return rating;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public DeezerListResponse<TrackInfo> getTrackList() {
        return trackList;
    }

    // -- Setters ---
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setArtistPicture(String artistPicture) {
        this.artistPicture = artistPicture;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setFanNumber(int fanNumber) {
        this.fanNumber = fanNumber;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public void setTrackList(DeezerListResponse<TrackInfo> trackList) {
        this.trackList = trackList;
    }
}
