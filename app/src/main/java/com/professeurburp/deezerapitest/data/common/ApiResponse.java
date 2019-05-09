package com.professeurburp.deezerapitest.data.common;

import android.util.ArrayMap;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Common class used by API responses.
 */

public class ApiResponse<T> {
    private static final String UNKNOWN_ERROR_MESSAGE = "UnknownError";

    public static <T> ApiErrorResponse<T> create(Throwable error) {
        return new ApiErrorResponse<>(
                error.getMessage() != null ?
                        error.getMessage() : "unknown error");
    }

    public static <T> ApiResponse<T> create(Response<T> response) {

        if (!response.isSuccessful()) {
            return parseError(response.errorBody());
        }

        T body = response.body();

        if (body == null || response.code() == 204) {
            return new ApiEmptyResponse<>();
        }

        return new ApiSuccessResponse<>(body, response.headers().get("link"));
    }

    private static <T> ApiResponse<T> parseError(ResponseBody responseBody) {
        String errorMessage;

        try {
            if (responseBody == null || "".equals(responseBody.string())) {
                return new ApiErrorResponse<>(UNKNOWN_ERROR_MESSAGE);
            } else {
                errorMessage = responseBody.string();
            }
        } catch (IOException ex) {
            return new ApiErrorResponse<>(UNKNOWN_ERROR_MESSAGE);
        }

        return new ApiErrorResponse<>(errorMessage);
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> extends ApiResponse<T> { }

class ApiSuccessResponse<T> extends ApiResponse<T> {
    private static final Pattern LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"");
    private static final Pattern PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)");
    private static final String NEXT_LINK = "next";

    private T body;
    private Map<String, String> links;
    private Integer nextPage;

    private ApiSuccessResponse(T body, Map<String, String> links){
        this.body = body;
        this.links = links;
    }

    ApiSuccessResponse(T body, String links){
        this(body, extractLinks(links));
    }

    public Integer getNextPage() {
        if (!links.containsKey(NEXT_LINK)) {
            return null;
        }

        Matcher matcher = PAGE_PATTERN.matcher(links.get(NEXT_LINK));
        if (!matcher.find() || matcher.groupCount() != 1) {
            return null;
        }

        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException ex) {
            Log.e(this.getClass().getName(),
                    String.format("Cannot parse next page from %s", links.get(NEXT_LINK)));

            return null;
        }
    }

    public Map<String, String> getLinks() {
        return links;
    }
    public T getBody() {
        return body;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }
    public void setBody(T body) {
        this.body = body;
    }

    private static Map<String, String> extractLinks(String linkHeader) {

        if (linkHeader == null  || "".equals(linkHeader)) {
            return Collections.emptyMap();
        }

        ArrayMap<String, String> links = new ArrayMap<>();
        Matcher matcher = LINK_PATTERN.matcher(linkHeader);

        while (matcher.find()) {
            int count = matcher.groupCount();
            if (count == 2) {
                links.put(matcher.group(2), matcher.group(1));
            }
        }
        return links;
    }
}

class ApiErrorResponse<T> extends ApiResponse<T> {

    private String errorMessage;

    ApiErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}