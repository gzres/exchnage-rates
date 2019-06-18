package gzres.exchangeRates.tasks

import gzres.exchangeRates.providers.RatesProvider
import gzres.exchangeRates.rates.RatesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDate

abstract class Task(
        @Autowired
        protected val ratesService: RatesService
) {
    protected abstract val provider: RatesProvider

    @Scheduled
    abstract fun action()

    fun importRates(date: LocalDate): Boolean {
        val rates = provider.getExchangeRates(date)

        return ratesService.addRates(rates)
    }
}
