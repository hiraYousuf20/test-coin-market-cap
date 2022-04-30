Feature: Test 2 different mobile apps

  Scenario: Join a call on Zoom
    Given I launch the zoom application
    Then I tap on Join a Meeting button
    Then I should see the Join button is disabled on Join a Meeting screen
    Then I enter 9 digits meeting Id
    Then I should see the join button is enabled
    Then I tap on the toggle button to turn off my video
    And I click on Join button
    Then I put the app in foreground
    And I launch it back again
    Then I should see the Invalid meeting ID text in pop-up

    Scenario:
