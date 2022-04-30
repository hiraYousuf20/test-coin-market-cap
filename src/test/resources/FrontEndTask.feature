Feature: View Cryptocurrency Prices, As a user I want to select 100 rows to view 100 records
    Scenario: Task 1
        Given I launch Coin Market Website
        When Select 100 rows to be viewed from website
        Then I should see 100 rows displayed on the page

    Scenario: Task 2
        Given I launch Coin Market Website
        When I click on filter button
        Then I click on Add Filter button
        When I filter records by MarketCap 1B to 10B
        Then I click on Apply Filter button
        And I filter records by price 101 to 1000
        Then I click on Apply Filter button on Price
        Then I click on Show result Filter button
