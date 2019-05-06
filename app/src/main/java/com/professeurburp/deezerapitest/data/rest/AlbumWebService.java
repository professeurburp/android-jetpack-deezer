package com.professeurburp.deezerapitest.data.rest;

import androidx.lifecycle.LiveData;

import com.professeurburp.deezerapitest.data.common.ApiResponse;
import com.professeurburp.deezerapitest.data.model.AlbumOverview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlbumWebService {

        /**
         * List user albums
         * @param user user id to look for albums
         * @return The list of the user's albums, within two layers of encapsulation.
         *
         * ApiResponse is a generic object that encapsulates network errors
         * DeezerUserResponse is the encapsulation for 'user' related requests specific to Deezer OpenAPI.
         * The fact is HTTP Code 200 is always returned for user related requests, and DeezerUserResponse
         * contains all error info.
         *
         * We therefore have a useless layer of encapsulation. This should be fixed and simplified, by using
         * specific CallAdapterFactory, CallAdapter that would be used for that matter.
         *
         * No time so far, but this is definitely something to be done.
         */
        @GET("user/{user}/albums")
        LiveData<ApiResponse<DeezerUserResponse<AlbumOverview>>> listAlbums(@Path("user") String user);
}