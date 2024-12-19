package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import utils.Config;

public class Hooks {

    @Before(order = 1)
    public void setupBaseURI() {
        System.out.println("Setting up the base URI for API tests.");
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Before(order = 2)
    public void setupAuthentication() {
        System.out.println("Setting up authentication for API tests.");
        RestAssured.authentication = RestAssured.preemptive().basic(Config.CONSUMER_KEY, Config.CONSUMER_SECRET);
    }

    @Before("@Critical")
    public void setupForCriticalTests() {
        System.out.println("Setting up for critical scenarios.");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("Scenario failed: " + scenario.getName());
        } else {
            System.out.println("Scenario passed: " + scenario.getName());
        }
        System.out.println("Tearing down resources after scenario execution.");
    }
}
