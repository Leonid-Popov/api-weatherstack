@all
Feature: Tests for weatherstak api

  @Test
  @CheckData
  Scenario Outline: User check data correct
    When User send get request weather from city <City>
    Then Check status code <Code>
    Then Check location name <City>
    Then Check country <Country>
    Examples:
      | City        | Code | Country        |
      | Novosibirsk | 200  | Russia         |
      | Moscow      | 200  | Russia         |
      | London      | 200  | United Kingdom |
      | Shanghai    | 200  | China          |

  @CheckAccessKey
  Scenario Outline: User check access key value error code and error type
    When User send request with invalid access key for city <City>
    Then Check response error code <Code> and error type <InfoMessage>
    Examples:
      | City        | Code | InfoMessage        |
      | Novosibirsk | 101  | invalid_access_key |

  @CheckRequestFailed
  Scenario Outline: User check request errors
    When User send get request weather from city <City>
    Then Check response error code <Code> and error type <InfoMessage>
    Examples:
      | City         | Code | InfoMessage    |
      | NonExistCity | 615  | request_failed |
      |              | 601  | missing_query  |

  @CheckLanguageRequest
  Scenario Outline: User check language error
    When User get weather from city <City> with language <Lang>
    Then Check response error code <Code> and error type <InfoMessage>
    Examples:
      | City   | Code | InfoMessage                | Lang |
      | Moscow | 105  | function_access_restricted | gang |
      | Moscow | 605  | invalid_language           | en   |

#    Тест с ошибкой, показывает результаты вывода в лог
  @CheckDataNegative
  Scenario Outline: Negative test with error
    When User send get request weather from city <City>
    Then Check status code <Code>
    Then Check location name <City>
    Then Check country <Country>
    Examples:
      | City        | Code | Country |
      | Novosibirsk | 200  | China   |
