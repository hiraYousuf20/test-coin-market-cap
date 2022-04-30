package config;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class EnvGlobals {
    public static Response response;// API response object
    public static RequestSpecification requestSpecification; // given store object
    public static RequestSpecification REQUEST;

    public static String AuthorizationToken = "1c6d3019-1c32-4f69-b562-a7ffd460a608";

    public static String btcId;
    public static String ethId;
    public static String usdtId;
    public static Map<String,String> params = new HashMap<>();
}
