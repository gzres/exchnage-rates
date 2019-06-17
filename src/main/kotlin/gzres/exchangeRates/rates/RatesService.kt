package gzres.exchangeRates.rates

import gzres.exchangeRates.rates.dao.Rate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RatesService(
        @Autowired
        private val repository: RatesRepository) {

    fun addRate(rate: Rate): Boolean {
        var successful = true

        try {
            if (
                    repository.findByProviderAndDateAndSourceCurrencyAndTargetCurrencyAndType(
                            rate.provider,
                            rate.date,
                            rate.sourceCurrency,
                            rate.targetCurrency,
                            rate.type
                    ).count() > 1
            ) {

                repository.save(rate)
            }
        } catch (e: Exception) {
            successful = false
        }

        return successful
    }

    fun addRates(rates: List<Rate>): Boolean {
        val results = arrayListOf<Boolean>()
        rates.forEach {
            results.add(addRate(it))
        }

        return !results.contains(false)
    }
}
