
Feature: DemoQA Browser Windows Functionality Tests

  As a QA Engineer
  I want to verify browser windows operations
  So that I can ensure multi-window/tab functionality works correctly

  Background:
    Given user is on DemoQA Browser Windows page

  @TC001 @NewTab
  Scenario: Verify new tab functionality
    When user clicks on "New Tab" button
    Then a new tab should open
    And new tab should contain "This is a sample page" text
    And user should be able to return to main window

  @TC002 @NewWindow
  Scenario: Verify new window functionality
    When user clicks on "New Window" button
    Then a new window should open
    And new window should contain "This is a sample page" text
    And new window URL should be correct
    And user should be able to close new window and return to main

  @TC003 @MessageWindow
  Scenario: Verify new window message functionality
    When user clicks on "New Window Message" button
    Then a message window should open
    And message window should contain sharing message
    And user should be able to close message window

  @TC004 @AllButtons
  Scenario: Test all browser window buttons sequentially
    When user tests all browser window buttons
    Then all operations should complete successfully

  @TC005 @NegativeTest
  Scenario Outline: Verify button states and validations
    When page is loaded
    Then "<button>" should be <state>

    Examples:
      | button               | state       |
      | New Tab              | visible     |
      | New Window           | enabled     |
      | New Window Message   | clickable   |
      | Invalid Button       | not exist   |