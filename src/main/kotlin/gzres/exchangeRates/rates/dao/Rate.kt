package gzres.exchangeRates.rates.dao

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document
class Rate(
        date: LocalDate,
        sourceCurrency: Currency,
        targetCurrency: Currency,
        exchangeRate: Double,
        type: ExchangeType,
        provider: ProviderEnum
) {

    @Id
    var id: UUID = UUID.randomUUID()
    var date: LocalDate = date
    var sourceCurrency: Currency = sourceCurrency
    var targetCurrency: Currency = targetCurrency
    var exchangeRate: Double = exchangeRate
    var type: ExchangeType = type
    var provider: ProviderEnum = provider
}
