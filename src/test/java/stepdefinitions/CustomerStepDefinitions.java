package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.Config;

import java.util.HashMap;

import static io.restassured.RestAssured.*;

public class CustomerStepDefinitions {

    static int customerId = -1;
    static String customerEmail;
    Response response;

    @Given("the API base URL is configured")
    public void theApiBaseUrlIsConfigured() {
        baseURI = Config.BASE_URL;
    }

    @When("I send a POST request to create a customer")
    public void iSendAPostRequestToCreateACustomer() {
        String randomString = generateRandomString(8);
        customerEmail = randomString + "@example.com";

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", customerEmail);
        requestBody.put("first_name", "John");
        requestBody.put("last_name", "Doe");
        requestBody.put("username", "user_" + randomString);
        requestBody.put("password", "password123");

        response = given()
                .auth().preemptive().basic(Config.CONSUMER_KEY, Config.CONSUMER_SECRET)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when().post();

        System.out.println("POST Response: " + response.asString());
        if (response.statusCode() == 201) {
            customerId = response.jsonPath().getInt("id");
        } else {
            System.err.println("Failed to create customer. Status code: " + response.statusCode());
        }
    }

    @Then("the customer is created successfully with status code {int}")
    public void theCustomerIsCreatedSuccessfullyWithStatusCode(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Customer creation failed.");
        Assert.assertTrue(customerId > 0, "Invalid customer ID.");
    }

    @When("I send a GET request to retrieve the customer")
    public void iSendAGetRequestToRetrieveTheCustomer() {
        if (customerId <= 0) {
            Assert.fail("Cannot retrieve customer. Invalid customer ID.");
        }

        response = given()
                .auth().preemptive().basic(Config.CONSUMER_KEY, Config.CONSUMER_SECRET)
                .when().get(baseURI + customerId);

        System.out.println("GET Response: " + response.asString());
    }

    @Then("the customer details are retrieved with status code {int}")
    public void theCustomerDetailsAreRetrievedWithStatusCode(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Failed to retrieve customer.");
        Assert.assertEquals(response.jsonPath().getString("email"), customerEmail, "Customer email mismatch.");
    }

    @When("I send a PUT request to update the customer email")
    public void iSendAPutRequestToUpdateTheCustomerEmail() {
        String updatedEmail = "updated_" + generateRandomString(5) + "@example.com";
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("email", updatedEmail);

        response = given()
                .auth().preemptive().basic(Config.CONSUMER_KEY, Config.CONSUMER_SECRET)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when().put(baseURI + customerId);

        System.out.println("PUT Response: " + response.asString());
        if (response.statusCode() == 200) {
            customerEmail = updatedEmail;
        } else {
            System.err.println("Failed to update customer email. Status code: " + response.statusCode());
        }
    }

    @Then("the customer email is updated successfully with status code {int}")
    public void theCustomerEmailIsUpdatedSuccessfullyWithStatusCode(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Customer email update failed.");
    }

    @When("I send a DELETE request to delete the customer")
    public void iSendADeleteRequestToDeleteTheCustomer() {
        response = given()
                .auth().preemptive().basic(Config.CONSUMER_KEY, Config.CONSUMER_SECRET)
                .when().delete(baseURI + customerId + "?force=true");

        System.out.println("DELETE Response: " + response.asString());
    }

    @Then("the customer is deleted successfully with status code {int}")
    public void theCustomerIsDeletedSuccessfullyWithStatusCode(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Customer deletion failed.");
    }

    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return builder.toString();
    }
}
