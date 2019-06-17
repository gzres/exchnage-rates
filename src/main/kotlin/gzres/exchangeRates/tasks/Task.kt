package gzres.exchangeRates.tasks

import gzres.exchangeRates.providers.RatesProvider
import gzres.exchangeRates.rates.RatesService
import org.springframework.scheduling.annotation.Scheduled

abstract class Task {

    protected abstract val ratesService: RatesService
    protected abstract val provider: RatesProvider

    @Scheduled
    abstract fun action()

    fun importRates(): Boolean {
        val rates = provider.getExchangeRates()

        return ratesService.addRates(rates)
    }
}
