package com.professeurburp.deezerapitest.data.common;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.professeurburp.deezerapitest.utils.ExecutorPool;
import com.professeurburp.deezerapitest.vo.Resource;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * A generic class that can provide a resource backed by both a local database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final ExecutorPool executorPool;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource(ExecutorPool executorPool) {
        this.executorPool = executorPool;

        result.setValue(Resource.loading(null));

        // TODO: Add method to check if data should be saved.
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));

        result.addSource(
                apiResponse,
                response -> {
                    result.removeSource(apiResponse);
                    result.removeSource(dbSource);

                    Class responseType = response.getClass();

                    if (ApiSuccessResponse.class.isAssignableFrom(responseType)) {
                        executorPool.diskIO().execute(() -> {
                            saveCallResult(processResponse((ApiSuccessResponse<RequestType>) response));

                            executorPool.mainThread().execute(() -> {
                                // We specially request a new live data,
                                // otherwise we will get immediately last cached value,
                                // which may not be updated with latest results received from network.
                                result.addSource(loadFromDb(),
                                        newData -> setValue(Resource.success(newData)));
                            });
                        });
                    } else if (ApiEmptyResponse.class.isAssignableFrom(responseType)) {
                        executorPool.mainThread().execute(() -> {
                            // reload from disk whatever we had, as we have no results
                            result.addSource(loadFromDb(),
                                    newData -> setValue(Resource.success(newData)));
                        });
                    } else if (ApiErrorResponse.class.isAssignableFrom(responseType)) {
                        // Error while fetching, we can use
                        onFetchFailed();
                        result.addSource(dbSource,
                                newData -> setValue(Resource.error(((ApiErrorResponse<RequestType>) response).getErrorMessage(), newData)));
                    }
                });
    }

    private void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    private RequestType processResponse(ApiSuccessResponse<RequestType> response) {
        return response.getBody();
    }

    @WorkerThread
    protected abstract void saveCallResult(RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
