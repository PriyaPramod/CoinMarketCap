#Author: kspramod13@gmail.com
@BackEndTest @CoinMarketCap
Feature: API Automation at https://coinmarketcap.com/

  Scenario Outline: Validate conversion
    Given "<Call>" responce using end point "<EndPoint>"
    Then I validate the Status code for "<StatusCode>"
    Then Get the Ids for "<Currency>"
      | Currency     |
      | Bitcoin      |
      | Ethereum     |
      | Tether       |
      | Binance Coin |
    And Convert the above currencies to "<ConversionEndPoint>"
      | ConvertCurrencySymbol |
      | BOB                   |

    Examples: 
      | Call | EndPoint               | StatusCode | ConversionEndPoint         |
      | Get  | /v1/cryptocurrency/map |        200 | /v1/tools/price-conversion |

  Scenario Outline: Validate technical documentation
    Given Retrieve the "<EthereumID>" technical documentation using "<EndPoint>"
    Then I validate the Status code for "<StatusCode>"
    Then Verify for the "<Logo>" "<TechnicalDoc>" "<Currency>" "<DateAdded>" "<Tags>"

    Examples: 
      | EthereumID | EndPoint                | StatusCode | Logo                                                         | TechnicalDoc                                      | Currency | DateAdded                | Tags     |
      |       1027 | /v1/cryptocurrency/info |        200 | https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png | https://github.com/ethereum/wiki/wiki/White-Paper | ETH      | 2015-08-07T00:00:00.000Z | mineable |

  Scenario Outline: Validate extra point
    Given Retrieve the first "<Occurance>" from cryptocurrency "<EndPoint>" and check mineable tag associated with them and correct cryptocurrencies have been printed

    Examples: 
      | Occurance | EndPoint                |
      |        10 | /v1/cryptocurrency/info |
