#Author: Pramod K Siddaramaiah
@FrontEndTest @CoinMarketCap
Feature: FrontEndTest

  Scenario Outline: Frontend Task one
    When Select Show rows dropdown value to "<RowsValue>"
    Then Verify "<RowsValue>" are displayed

    Examples: 
      | RowsValue |
      |       100 |

  Scenario Outline: Frontend Task Two
    When I add filter
    Given Filter records by "<Filter1>" "<StartRange>" "<EndRange>" and "<Filter2>" "<StartPRange>" "<EndPRange>"
    Then Verify Check records displayed on page are correct as per the filter applied "<Filter1>" "<StartRange>" "<EndRange>" and "<Filter2>" "<StartPRange>" "<EndPRange>"

    Examples: 
      | Filter1    | Filter2 | StartRange | EndRange    | StartPRange | EndPRange |
      | Market Cap | Price   | 1000000000 | 10000000000 |         101 |      1000 |
