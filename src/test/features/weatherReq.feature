@all
Feature: Tests for weatherstak api

  @CheckData
  Scenario Outline: User check data correct
    When User get weather from city <City>
    Then Check status code <Code>
    Then Check location name <City>
    Then Check country <Country>
    Examples:
      | City        | Code | Country |
      | Novosibirsk | 200  | Russia  |
      | Moscow      | 200  | Russia  |
#      | London      | 200  | United Kingdom |
#      | Shanghai    | 200  | China          |

#  @CheckErrorInfo
#  Scenario Outline: User check error code and info message
#    When User send error request for city <City>
#    Then Check response error code <Code> and error type <InfoMessage>
#    Examples:
#      | City        | Code | InfoMessage        |
#      | Novosibirsk | 101  | invalid_access_key |
