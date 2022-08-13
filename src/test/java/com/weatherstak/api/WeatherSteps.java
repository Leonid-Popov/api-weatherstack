package com.weatherstak.api;

import com.weatherstak.api.dto.ResponseCode;
import com.weatherstak.api.dto.WeatherResponse;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class WeatherSteps {

//    @AfterEach
//    void addAttachments(){
//        Attachments.Attach.log(Rest);
//    }

    public static final String ACCESS_KEY_VALUE = "387377f7e3a6b885540637bbc1588c87";
    private static final String URL = "http://api.weatherstack.com/";
    public static final String INVALID_ACCESS_KEY_VALUE = "387377f7e3a6b885540637bbc158";

    private Response response;

    private Response weatherRequest(String city) {
        return given()
                .baseUri(URL)
                .basePath("current?access_key=" + ACCESS_KEY_VALUE)
                .contentType(ContentType.JSON)
                .get(URL + "current?access_key={accessKeyValue}&query={city}",
                        ACCESS_KEY_VALUE, city)
                .then().log().all()
                .extract().response();
    }

    private Response errorRequest(String city) {
        return given()
                .baseUri(URL)
                .basePath("current?access_key=" + INVALID_ACCESS_KEY_VALUE)
                .contentType(ContentType.JSON)
                .get(URL + "current?access_key={accessKeyValue}&query={city}",
                        INVALID_ACCESS_KEY_VALUE, city)
                .then().log().all()
                .extract().response();
    }

    @Step("User get weather from city (.*)")
    @Then("User get weather from city (.*)")
    public void getWeather(String city) {
        response = weatherRequest(city);
    }

    @Step("Check location name (.*)")
    @When("Check location name (.*)")
    public void checkLocationName(String name) {
        WeatherResponse weatherResponse = response.as(WeatherResponse.class);
        Assertions.assertTrue(weatherResponse.getLocation().getName().contains(name));
    }

    @Step("Check status code (.*)")
    @When("Check status code (.*)")
    public void checkStatus(String code) {
        Assertions.assertEquals(code, String.valueOf(response.getStatusCode()));
    }

    @Step("Check country (.*)")
    @When("Check country (.*)")
    public void checkCountry(String country) {
        WeatherResponse weatherResponse = response.as(WeatherResponse.class);
        Assertions.assertEquals(country, String.valueOf(weatherResponse.getLocation().getCountry()));
    }

    @Step("User send error request for city (.*)")
    @When("User send error request for city (.*)")
    public void sendErrorReq(String city) {
        response = errorRequest(city);
    }

//    @Step("Check response error code (.*) and error type (.*)")
//    @When("Check response error code (.*) and error type (.*)")
//    public void checkCode(int code, String message) throws Throwable {
//        ResponseCode responseCode = response.as((ResponseCode.class));
//        Assertions.assertEquals(code, String.valueOf(code));
//        Assertions.assertEquals(message, String.valueOf(responseCode.getType()));
//    }

}
