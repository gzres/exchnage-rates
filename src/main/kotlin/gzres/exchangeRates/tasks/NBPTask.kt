package gzres.exchangeRates.tasks

import gzres.exchangeRates.providers.nbp.NBPProvider
import gzres.exchangeRates.rates.RatesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class NBPTask(
        @Autowired
        override val provider: NBPProvider,
        @Autowired
        ratesService: RatesService
) : Task(ratesService) {

    @Scheduled(cron = "\${tasks.nbp.startTime}")
    override fun action() {
        importRates(LocalDate.now())
        logger.info("Importing rates from NBP completed")
    }

}
