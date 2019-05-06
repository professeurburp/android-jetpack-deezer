package com.professeurburp.deezerapitest.data.rest;

import androidx.lifecycle.LiveData;

import com.professeurburp.deezerapitest.data.common.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * @param <R>
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {

    private final Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {

        return new LiveData<ApiResponse<R>>() {
            private AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            postValue(ApiResponse.create(response));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable throwable) {
                            postValue(ApiResponse.create(throwable));
                        }
                    });
                }
            }
        };
    }
}