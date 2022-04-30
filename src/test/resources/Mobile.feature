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

    Scenario: Explore Linkedin app
      Given I launch Linkedin app
      Then On the Homescreen, swipe left and verify the text on each screen till the last slide
      Then I tap on the sign in button
      Then I enter a valid email and password
      Then I tap on continue button to login
      Then I tap on the search bar
      Then I enter 'Callsign' in the search bar
      Then I should see the See all results option
      Then I should see callsign in all the results returned in the list
      Then I tap on the arrow icon to go back
      Then I tap on the message icon
      Then I tap on the search bar
      Then I select the My Connections radio button
      Then I should see that the My Connections text appears beneath with a cross/cancel mark
      Then I tap on the arrow to go back to homescreen
      Then I tap on the profile photo
      Then I tap on settings
      Then I scroll down and should see the linkedin banner text


