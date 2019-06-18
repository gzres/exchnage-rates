package gzres.exchangeRates.providers

import gzres.exchangeRates.rates.dao.Rate
import java.time.LocalDate

interface Provider {
    fun getExchangeRates(date: LocalDate): List<Rate>
}
