package config;

import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReusableFunctions {

    // get response path array list
    public static ArrayList responseList(String key) {
        return EnvGlobals.response.then().
                extract().
                path(key);
    }

    // get response path
    public static String getResponsePath(String key) {
        return EnvGlobals.response.getBody().path(key).toString();
    }

    // set content type
    public static void contentType(String contentType) {
        EnvGlobals.REQUEST = RestAssured.given().contentType(contentType);
    }

    // For Event List
    // Given function with 2 parameters - API (parameters, headers)
    public static void givenParamHeader(Map<String, String> params, Map<String, String> headers) {
        contentType("application/json");
        EnvGlobals.requestSpecification = EnvGlobals.REQUEST.given()
                .queryParams(params)
//                .queryParam(String.valueOf(params))
                .headers(headers);
    }

    // When function with request type(Get, Post etc) & API endpoint
    public static void whenFunction(String requestType, String endPoint) {
        switch (requestType) {
            case "post":
                EnvGlobals.response =
                        EnvGlobals.requestSpecification
                                .when().log().all()
                                .post(endPoint);
                break;

            case "get":
                EnvGlobals.response =
                        EnvGlobals.requestSpecification
                                .when().log().all()
                                .get(endPoint);
                break;
            case "delete":
                EnvGlobals.response =
                        EnvGlobals.requestSpecification
                                .when().log().all()
                                .delete(endPoint);
                break;
            case "put":
                EnvGlobals.response =
                        EnvGlobals.requestSpecification
                                .when().log().all()
                                .put(endPoint);
                break;
            case "patch":
                EnvGlobals.response =
                        EnvGlobals.requestSpecification
                                .when().log().all()
                                .patch(endPoint);
                break;
        }

    }

    // Then function to verify status code
    public static void thenFunction(int statusCode) {
        //envGlobals.response.then().statusCode(statusCode);
        EnvGlobals.response.then().log().all().statusCode(statusCode);

    }

    // Hashmap for params, headers, form-data
    public static <K, V> Map<K, V> headers(Object... keyValues) {
        Map<K, V> map = new HashMap<>();

        for (int index = 0; index < keyValues.length / 2; index++) {
            map.put((K) keyValues[index * 2], (V) keyValues[index * 2 + 1]);
        }

        return map;
    }
}