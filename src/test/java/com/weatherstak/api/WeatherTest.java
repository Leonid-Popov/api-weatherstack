package com.weatherstak.api;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "com.weatherstak.api",
        tags = "@all",
        snippets = SnippetType.UNDERSCORE
)
public class WeatherTest {

    @Test
    public void helloTest() {
        System.out.println("hello");
    }
}

