/*
 * Swagger PetNutrition YAML
 * Pet Nutrition
 *
 * OpenAPI spec version: 1.0.0
 * Contact: pzybrick@gmail.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.api;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.Pair;
import io.swagger.client.ProgressRequestBody;
import io.swagger.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import io.swagger.client.model.LotTreeItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotApi {
    private ApiClient apiClient;

    public LotApi() {
        this(Configuration.getDefaultApiClient());
    }

    public LotApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for findLotTree
     * @param lotNumber Manufacturer Lot Number (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call findLotTreeCall(String lotNumber, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/canine/nutrition/findLotTree";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (lotNumber != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("lotNumber", lotNumber));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/xml", "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/xml", "application/json", "multipart/form-data", "application/x-www-form-urlencoded"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "petnutrition_auth" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call findLotTreeValidateBeforeCall(String lotNumber, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'lotNumber' is set
        if (lotNumber == null) {
            throw new ApiException("Missing the required parameter 'lotNumber' when calling findLotTree(Async)");
        }
        

        com.squareup.okhttp.Call call = findLotTreeCall(lotNumber, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Finds Lot Ingredients Blockchains by Manufacturer Lot Number
     * Finds Lot Ingredients Blockchains by Manufacturer Lot Number
     * @param lotNumber Manufacturer Lot Number (required)
     * @return LotTreeItem
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public LotTreeItem findLotTree(String lotNumber) throws ApiException {
        ApiResponse<LotTreeItem> resp = findLotTreeWithHttpInfo(lotNumber);
        return resp.getData();
    }

    /**
     * Finds Lot Ingredients Blockchains by Manufacturer Lot Number
     * Finds Lot Ingredients Blockchains by Manufacturer Lot Number
     * @param lotNumber Manufacturer Lot Number (required)
     * @return ApiResponse&lt;LotTreeItem&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<LotTreeItem> findLotTreeWithHttpInfo(String lotNumber) throws ApiException {
        com.squareup.okhttp.Call call = findLotTreeValidateBeforeCall(lotNumber, null, null);
        Type localVarReturnType = new TypeToken<LotTreeItem>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Finds Lot Ingredients Blockchains by Manufacturer Lot Number (asynchronously)
     * Finds Lot Ingredients Blockchains by Manufacturer Lot Number
     * @param lotNumber Manufacturer Lot Number (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call findLotTreeAsync(String lotNumber, final ApiCallback<LotTreeItem> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = findLotTreeValidateBeforeCall(lotNumber, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<LotTreeItem>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for findManLots
     * @param fromDate Start date of range, inclusive (optional)
     * @param toDate End date of range, inclusive (optional)
     * @param range Maximum number of returned values (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call findManLotsCall(String fromDate, String toDate, Integer range, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/canine/nutrition/findAllManLots";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (fromDate != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("fromDate", fromDate));
        if (toDate != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("toDate", toDate));
        if (range != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("range", range));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/xml", "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/xml", "application/json", "multipart/form-data", "application/x-www-form-urlencoded"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "petnutrition_auth" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call findManLotsValidateBeforeCall(String fromDate, String toDate, Integer range, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        

        com.squareup.okhttp.Call call = findManLotsCall(fromDate, toDate, range, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Finds Manufacturer Lot Numbers by Optional Date Range and Limit
     * Finds Manufacturer Lot Numbers by Optional Date Range and Limit
     * @param fromDate Start date of range, inclusive (optional)
     * @param toDate End date of range, inclusive (optional)
     * @param range Maximum number of returned values (optional)
     * @return List&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<String> findManLots(String fromDate, String toDate, Integer range) throws ApiException {
        ApiResponse<List<String>> resp = findManLotsWithHttpInfo(fromDate, toDate, range);
        return resp.getData();
    }

    /**
     * Finds Manufacturer Lot Numbers by Optional Date Range and Limit
     * Finds Manufacturer Lot Numbers by Optional Date Range and Limit
     * @param fromDate Start date of range, inclusive (optional)
     * @param toDate End date of range, inclusive (optional)
     * @param range Maximum number of returned values (optional)
     * @return ApiResponse&lt;List&lt;String&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<String>> findManLotsWithHttpInfo(String fromDate, String toDate, Integer range) throws ApiException {
        com.squareup.okhttp.Call call = findManLotsValidateBeforeCall(fromDate, toDate, range, null, null);
        Type localVarReturnType = new TypeToken<List<String>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Finds Manufacturer Lot Numbers by Optional Date Range and Limit (asynchronously)
     * Finds Manufacturer Lot Numbers by Optional Date Range and Limit
     * @param fromDate Start date of range, inclusive (optional)
     * @param toDate End date of range, inclusive (optional)
     * @param range Maximum number of returned values (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call findManLotsAsync(String fromDate, String toDate, Integer range, final ApiCallback<List<String>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = findManLotsValidateBeforeCall(fromDate, toDate, range, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<String>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
