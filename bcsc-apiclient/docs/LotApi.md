# LotApi

All URIs are relative to *http://localhost/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findLotIngreds**](LotApi.md#findLotIngreds) | **GET** /canine/nutrition/findLotIngredsByManLotNumber | Finds Lot Ingredients Blockchains by Manufacturer Lot Number
[**findManLots**](LotApi.md#findManLots) | **GET** /canine/nutrition/findAllManLots | Finds Manufacturer Lot Numbers by Optional Date Range and Limit


<a name="findLotIngreds"></a>
# **findLotIngreds**
> List&lt;LotTreeItem&gt; findLotIngreds(lotNumber)

Finds Lot Ingredients Blockchains by Manufacturer Lot Number

Finds Lot Ingredients Blockchains by Manufacturer Lot Number

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.LotApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: petnutrition_auth
OAuth petnutrition_auth = (OAuth) defaultClient.getAuthentication("petnutrition_auth");
petnutrition_auth.setAccessToken("YOUR ACCESS TOKEN");

LotApi apiInstance = new LotApi();
String lotNumber = "lotNumber_example"; // String | Manufacturer Lot Number
try {
    List<LotTreeItem> result = apiInstance.findLotIngreds(lotNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LotApi#findLotIngreds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **lotNumber** | **String**| Manufacturer Lot Number |

### Return type

[**List&lt;LotTreeItem&gt;**](LotTreeItem.md)

### Authorization

[petnutrition_auth](../README.md#petnutrition_auth)

### HTTP request headers

 - **Content-Type**: application/xml, application/json, multipart/form-data, application/x-www-form-urlencoded
 - **Accept**: application/xml, application/json

<a name="findManLots"></a>
# **findManLots**
> List&lt;String&gt; findManLots(fromDate, toDate, range)

Finds Manufacturer Lot Numbers by Optional Date Range and Limit

Finds Manufacturer Lot Numbers by Optional Date Range and Limit

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.LotApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: petnutrition_auth
OAuth petnutrition_auth = (OAuth) defaultClient.getAuthentication("petnutrition_auth");
petnutrition_auth.setAccessToken("YOUR ACCESS TOKEN");

LotApi apiInstance = new LotApi();
String fromDate = "fromDate_example"; // String | Start date of range, inclusive
String toDate = "toDate_example"; // String | End date of range, inclusive
Integer range = 56; // Integer | Maximum number of returned values
try {
    List<String> result = apiInstance.findManLots(fromDate, toDate, range);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LotApi#findManLots");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **fromDate** | **String**| Start date of range, inclusive | [optional]
 **toDate** | **String**| End date of range, inclusive | [optional]
 **range** | **Integer**| Maximum number of returned values | [optional]

### Return type

**List&lt;String&gt;**

### Authorization

[petnutrition_auth](../README.md#petnutrition_auth)

### HTTP request headers

 - **Content-Type**: application/xml, application/json, multipart/form-data, application/x-www-form-urlencoded
 - **Accept**: application/xml, application/json

