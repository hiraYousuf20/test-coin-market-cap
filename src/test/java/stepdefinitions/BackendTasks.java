package stepdefinitions;

import config.ConfigProperties;
import config.ReusableFunctions;
import config.UrlEndPoints;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static config.EnvGlobals.AuthorizationToken;
import static config.EnvGlobals.btcId;
import static config.EnvGlobals.ethId;
import static config.EnvGlobals.params;
import static config.EnvGlobals.response;
import static config.EnvGlobals.usdtId;

public class BackendTasks {

    List<Object> responseList = new ArrayList<Object>();
    List<Object> responseListWithMineableTag = new ArrayList<Object>();

    /**
     * @apiNote Setting api header
     */
    @Given("^I set GET cryptocurrency api header$")
    public void I_set_GET_cryptocurrency_api_header() {
        params.put("sort", "cmc_rank");
        ReusableFunctions.givenParamHeader(params, ReusableFunctions.headers("X-CMC_PRO_API_KEY", AuthorizationToken));
    }

    /**
     * @apiNote Executing cryptocurrency/map call
     */
    @When("^I retrieve the ids of Bitcoin, Tether and Ethereum using cryptocurrency map$")
    public void I_retrieve_the_ids() {
        ReusableFunctions.whenFunction("get", ConfigProperties.BaseUrl + ConfigProperties.version + UrlEndPoints.cryptocurrency + UrlEndPoints.map);
    }

    /**
     * @apiNote This method retrieve ids of BTC, ETH and USDT via cryptocurrency/map call
     * @return List of ids of currencies
     */
    @And("^I receive a valid response with the ids of mentioned currencies$")
    public static List<String> I_receive_a_valid_response_with_the_ids_of_mentioned_currencies() {
        ReusableFunctions.thenFunction(200);
        List<Object> data = ReusableFunctions.responseList("data");
        List<String> ids = new ArrayList<>();

        for (Object datum : data) {
            Map<String, String> obj = ((HashMap) datum);
            String cryptoSymbol = obj.get("symbol");

            if (cryptoSymbol.equalsIgnoreCase("BTC")) {
                btcId = String.valueOf(obj.get("id"));
                System.out.println("id : " + btcId + "\nCypto Symbol :" + cryptoSymbol);
                System.out.println("<==========================================>");
                ids.add(btcId);
            } else if (cryptoSymbol.equalsIgnoreCase("ETH")) {
                ethId = String.valueOf(obj.get("id"));
                System.out.println("id : " + ethId + "\nCypto Symbol :" + cryptoSymbol);
                System.out.println("<==========================================>");
                ids.add(ethId);
            } else if (cryptoSymbol.equalsIgnoreCase("USDT")) {
                usdtId = String.valueOf(obj.get("id"));
                System.out.println("id : " + usdtId + "\nCypto Symbol :" + cryptoSymbol);
                System.out.println("<==========================================>");
                ids.add(usdtId);
            }
        }
        return ids;
    }

    /**
     * @apiNote This method converts the price of currencies(BTC, ETH and USDT)
     * into Bolivian Bolivian via /tools/price-conversion call.
     */
    @Then("^Use the retrieved ids in the price conversion call$")
    public static void price_conversion() {

        for (String id : I_receive_a_valid_response_with_the_ids_of_mentioned_currencies()) {
            Map<String, String> params = new HashMap<>();
            params.put("amount", "1");
            params.put("id", id);
            params.put("convert_id", "2889");

            ReusableFunctions.givenParamHeader(params, ReusableFunctions.headers("X-CMC_PRO_API_KEY", AuthorizationToken));
            ReusableFunctions.whenFunction("get", ConfigProperties.BaseUrl + ConfigProperties.version + UrlEndPoints.tools + UrlEndPoints.priceConversion);
            ReusableFunctions.thenFunction(200);
        }

    }


    /**
     * @apiNote Retrieving ETH information via cryptocurrency/info call
     */
    @When("^I execute the info call with id$")
    public static void I_execute_the_info_call_with_id() {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(1027));

        ReusableFunctions.givenParamHeader(params, ReusableFunctions.headers("X-CMC_PRO_API_KEY", AuthorizationToken));
        ReusableFunctions.whenFunction("get", ConfigProperties.BaseUrl + ConfigProperties.version + UrlEndPoints.cryptocurrency + UrlEndPoints.info);
    }

    /**
     * @apiNote Validating the ETH response values
     */
    @Then("^confirm the following assertions$")
    public static void confirm_the_following_assertions() {

        Assert.assertEquals(ReusableFunctions.getResponsePath("data.1027.logo"), "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png");
        Assert.assertEquals(ReusableFunctions.getResponsePath("data.1027.urls.technical_doc[0]"), "https://github.com/ethereum/wiki/wiki/White-Paper");
        Assert.assertEquals(ReusableFunctions.getResponsePath("data.1027.symbol"), "ETH");
        Assert.assertEquals(ReusableFunctions.getResponsePath("data.1027.date_added"), "2015-08-07T00:00:00.000Z");
        Assert.assertEquals(ReusableFunctions.getResponsePath("data.1027.tags[0]"), "mineable");
    }


    /**
     * @apiNote Retrieving first 10 currencies via id using cryptocurrency/info call
     */
    @When("^I execute the info call with first 10 ids$")
    public void I_execute_the_info_call_with_first_10_ids() {

        for (int i = 1; i <= 10; i++) {
            Map<String, String> params = new HashMap<>();
            params.put("id", String.valueOf(i));

            ReusableFunctions.givenParamHeader(params, ReusableFunctions.headers("X-CMC_PRO_API_KEY", AuthorizationToken));
            ReusableFunctions.whenFunction("get", ConfigProperties.BaseUrl + ConfigProperties.version + UrlEndPoints.cryptocurrency + UrlEndPoints.info);
            responseList.add(response.getBody().path("data." + i + ""));
        }
    }

    /**
     * @apiNote Filter currencies with mineable tag
     */
    @Then("^Check the currencies have 'mineable' tag$")
    public void check_currencies_have_mineable_tag() {

        for (int i = 0; i <= responseList.size() - 1; i++) {
            Map<String, Object> map = (Map<String, Object>) responseList.get(i);
            ArrayList list = (ArrayList) map.get("tags");
            if (list.contains("mineable")) {
                responseListWithMineableTag.add(map);
            }
        }
    }

    /**
     * @apiNote Validate if the currencies with mineable tag are printed
     */
    @And("^Confirm that the correct cryptocurrencies are printed out$")
    public void confirm_the_correct_cryptocurrencies_are_printed() {

        for (int i = 0; i <= responseListWithMineableTag.size() - 1; i++) {
            Map<String, Object> map = (Map<String, Object>) responseListWithMineableTag.get(i);
            ArrayList list = (ArrayList) map.get("tags");
            Assert.assertTrue(list.contains("mineable"));
            System.out.println(map);
        }
    }
}

