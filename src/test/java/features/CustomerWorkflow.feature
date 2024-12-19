Feature: Customer Workflow with Hooks

  @Critical
  Scenario: Create a new customer
    Given the API base URL is configured
    When I send a POST request to create a customer
    Then the customer is created successfully with status code 201

  Scenario: Retrieve the created customer
    Given the API base URL is configured
    When I send a GET request to retrieve the customer
    Then the customer details are retrieved with status code 200

  Scenario: Update the customer email
    Given the API base URL is configured
    When I send a PUT request to update the customer email
    Then the customer email is updated successfully with status code 200

  Scenario: Delete the created customer
    Given the API base URL is configured
    When I send a DELETE request to delete the customer
    Then the customer is deleted successfully with status code 200
