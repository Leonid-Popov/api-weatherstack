package com.weatherstak.api;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import static cucumber.api.SnippetType.UNDERSCORE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "com.weatherstak.api",
        tags = "@all",
        snippets = UNDERSCORE
)
public class WeatherTest {

}

