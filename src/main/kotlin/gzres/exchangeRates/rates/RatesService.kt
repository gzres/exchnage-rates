package gzres.exchangeRates.rates

import gzres.exchangeRates.rates.dao.Rate
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RatesService(
        @Autowired
        private val repository: RatesRepository) {

    private val logger: Logger = LogManager.getLogger()

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
                    ).count() == 0
            ) {

                repository.insert(rate)
            }
        } catch (e: Exception) {
            logger.error("Error during adding data to db", e)
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
