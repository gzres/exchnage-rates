package gzres.exchangeRates.rates

import gzres.exchangeRates.rates.dao.ExchangeType
import gzres.exchangeRates.rates.dao.ProviderEnum
import gzres.exchangeRates.rates.dao.Rate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface RatesRepository : MongoRepository<Rate, UUID> {
    fun findByDate(date: LocalDate): List<Rate>
    fun findByProviderAndDate(providerEnum: ProviderEnum, date: LocalDate): List<Rate>
    fun findByProviderAndDateAndSourceCurrencyAndTargetCurrencyAndType(
            providerEnum: ProviderEnum,
            date: LocalDate,
            sourceCurrency: Currency,
            targetCurrency: Currency,
            exchangeType: ExchangeType
    ): List<Rate>
}
