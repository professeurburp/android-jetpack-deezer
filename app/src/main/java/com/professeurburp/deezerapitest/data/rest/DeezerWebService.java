package com.professeurburp.deezerapitest.data.rest;

import androidx.lifecycle.LiveData;

import com.professeurburp.deezerapitest.data.common.ApiResponse;
import com.professeurburp.deezerapitest.data.model.AlbumDetails;
import com.professeurburp.deezerapitest.data.model.AlbumOverview;
import com.professeurburp.deezerapitest.data.model.User;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeezerWebService {

    public static String DEEZER_ENDPOINT = "https://api.deezer.com/2.0/";


    @GET("user/{user}")
    LiveData<ApiResponse<User>> getUser(@Path("user") int userId);

    /**
     * List user albums
     *
     * @param userId user id to look for albums
     * @return The list of the user's albums, within two layers of encapsulation.
     * <p>
     * ApiResponse is a generic object that encapsulates network errors
     * DeezerListResponse is the encapsulation for 'list' related requests specific to Deezer OpenAPI.
     * The fact is HTTP Code 200 is always returned (provided there is no network error, of course)
     * for these related requests, and DeezerListResponse contains all eventual error info (especially
     * empty results)
     * <p>
     * We therefore have a useless layer of encapsulation. This should be fixed and simplified, by using
     * specific DeezerListCallAdapterFactory/DeezerListCallAdapter that would be used for that matter.
     * <p>
     * No time so far, but this is definitely something to be done.
     */
    @GET("user/{user}/albums")
    LiveData<ApiResponse<DeezerListResponse<AlbumOverview>>> listAlbums(@Path("user") int userId, @Query("limit") int limit);

    /**
     * Retrieve album detailed information, with artist name and picture, and track list.
     *
     * @param albumId Id for the album to be retrieved
     * @return Album detailed information, as a LiveData, in a response wrapper easing network error management.
     */
    @GET("album/{albumId}")
    LiveData<ApiResponse<AlbumDetails>> getAlbumDetails(@Path("albumId") int albumId);
}