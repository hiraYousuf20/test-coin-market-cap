Feature: View Cryptocurrency, As a user I want to retrieve the ids of some currencies
  Scenario: Task 1.
    Given I set GET cryptocurrency api header
    When I retrieve the ids of Bitcoin, Tether and Ethereum using cryptocurrency map
    And I receive a valid response with the ids of mentioned currencies
    Then Use the retrieved ids in the price conversion call

  Scenario: Task 2.
    Given I set GET cryptocurrency api header
    When I execute the info call with id
    Then confirm the following assertions

  Scenario: Task 3.
    Given I set GET cryptocurrency api header
    When I execute the info call with first 10 ids
    Then Check the currencies have 'mineable' tag
    And Confirm that the correct cryptocurrencies are printed out