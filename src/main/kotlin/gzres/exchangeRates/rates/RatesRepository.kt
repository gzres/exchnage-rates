package gzres.exchangeRates.rates

import gzres.exchangeRates.rates.dao.ProviderEnum
import gzres.exchangeRates.rates.dao.Rate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RatesRepository : MongoRepository<Rate, UUID> {
    fun findByDate(date: Date): List<Rate>
    fun findByProviderAndDate(providerEnum: ProviderEnum, date: Date): List<Rate>
}
