package com.weatherstak.api;

import com.weatherstak.api.dto.ResponseError;
import com.weatherstak.api.dto.WeatherResponse;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import static cucumber.api.SnippetType.UNDERSCORE;
import static io.restassured.RestAssured.given;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "com.weatherstak.api",
        tags = "@all",
        snippets = UNDERSCORE
)

public class WeatherStepsTest {

    public static final String ACCESS_KEY_VALUE = "387377f7e3a6b885540637bbc1588c87";
    public static final String INVALID_ACCESS_KEY_VALUE = "387377f7e3a6b885540637bbc158";
    private static final String URL = "http://api.weatherstack.com/";
    private Response response;

    //    Запрос погоды по городу
    private Response weatherRequest(String city) {
        return given()
//                .addFilter(new AllureRestAssured())
                .baseUri(URL)
                .basePath("current?access_key=" + ACCESS_KEY_VALUE)
                .contentType(ContentType.JSON)
                .get(URL + "current?access_key={accessKeyValue}&query={city}",
                        ACCESS_KEY_VALUE, city)
                .then().log().all()
                .extract().response();
    }

    //Запрос с невалидным ключом доступа
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

    //Запрос погоды по городу и языку
    private Response weatherReqWithLanguage(String city, String lang) {
        return given()
                .baseUri(URL)
                .basePath("current?access_key=" + ACCESS_KEY_VALUE)
                .contentType(ContentType.JSON)
                .get(URL + "current?access_key={accessKeyValue}&query={city}&language={lang}",
                        ACCESS_KEY_VALUE, city, lang)
                .then().log().all()
                .extract().response();
    }

    //Получение информации о погоде по городу
    @Step("User send get request weather from city {city}")
    @Then("User send get request weather from city (.*)")
    public void getWeather(String city) {
        response = weatherRequest(city);
    }

    //Получение информации о погоде по городу с доп параметром "язык"
    @Step("User get weather from city {city} with language {lang}")
    @Then("User get weather from city (.*) with language (.*)")
    public void weatherReqWithLang(String city, String lang) {
        response = weatherReqWithLanguage(city, lang);
    }

    //Проверка локации города
    @Step("Check location name {name}")
    @When("Check location name (.*)")
    public void checkLocationName(String name) {
        WeatherResponse weatherResponse = response.as(WeatherResponse.class);
        Assertions.assertTrue(weatherResponse.getLocation().getName().contains(name));
    }

    //Проверка кода ответа
    @Step("Check status code {code}")
    @When("Check status code (.*)")
    public void checkStatus(String code) {
        Assertions.assertEquals(code, String.valueOf(response.getStatusCode()));
    }

    //Проверка страны
    @Step("Check country {country}")
    @When("Check country (.*)")
    public void checkCountry(String country) {
        WeatherResponse weatherResponse = response.as(WeatherResponse.class);
        Assertions.assertEquals(country, String.valueOf(weatherResponse.getLocation().getCountry()));
    }

    //    Запрос с невалидным ключом доступа
    @Step("User send request with invalid access key for city {city}")
    @When("User send request with invalid access key for city (.*)")
    public void sendErrorReq(String city) {
        response = errorRequest(city);
    }

    //Проверка кода ошибки и типа ошибки
    @Step("Check response error code {code} and error type {message}")
    @When("Check response error code (\\d+) and error type (.*)")
    public void checkCode(int code, String message) {
        ResponseError responseError = response.as((ResponseError.class));
        Assertions.assertEquals(code, responseError.getError().getCode());
        Assertions.assertEquals(message, String.valueOf(responseError.getError().getType()));
    }

}
