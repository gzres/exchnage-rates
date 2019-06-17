package gzres.exchangeRates.providers

import gzres.exchangeRates.rates.dao.Rate

interface Provider {
    fun getExchangeRates(): List<Rate>
}
