package gzres.exchangeRates.tasks

import gzres.exchangeRates.providers.ecb.ECBProvider
import gzres.exchangeRates.rates.RatesService
import gzres.exchangeRates.rates.dao.ProviderEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ECBTask(
        @Autowired
        override val provider: ECBProvider,
        @Autowired
        ratesService: RatesService
) : Task(ratesService) {

    @Scheduled(cron = "\${tasks.ecb.startTime}")
    override fun action() {
        importRates(LocalDate.now())
        logger.info("Importing rates from ${ProviderEnum.ECB} completed")
    }

}
