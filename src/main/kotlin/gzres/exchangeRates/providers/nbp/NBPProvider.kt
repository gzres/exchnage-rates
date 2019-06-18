package gzres.exchangeRates.providers.nbp

import com.fasterxml.jackson.databind.ObjectMapper
import gzres.exchangeRates.providers.RatesProvider
import gzres.exchangeRates.providers.nbp.dao.NBPExchangeRates
import gzres.exchangeRates.providers.nbp.dao.NBPRate
import gzres.exchangeRates.rates.dao.ExchangeType
import gzres.exchangeRates.rates.dao.ProviderEnum
import gzres.exchangeRates.rates.dao.Rate
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@Service
class NBPProvider : RatesProvider() {
    override fun getExchangeRates(date: LocalDate): List<Rate> {

        val list = mutableListOf<Rate>()

        EXCHANGE_TABLES.forEach {
            list.addAll(getExchangeRatesFromTable(date, it))
        }

        return list
    }

    private fun getExchangeRatesFromTable(date: LocalDate, tableName: String): List<Rate> {

        val url = URL(
                API_URL
                        + ENDPOINT_RATES_DATE_BASED
                        .replace("{table}", tableName)
                        .replace("{date}", date.toString())
                        + "?format=json"
        )

        return when (val serviceStatus = getServiceStatus(url)) {
            HttpStatus.OK -> {
                parseExchangeRate(url)
            }
            HttpStatus.NOT_FOUND -> {
                //TODO: Log information about empty data
                ArrayList()
            }
            else -> {
                //TODO: Log "Not handled status: $serviceStatus"
                ArrayList()
            }
        }
    }

    private fun parseExchangeRate(url: URL, date: LocalDate = LocalDate.now()): ArrayList<Rate> {
        val result = ArrayList<Rate>()

        val response = ObjectMapper().readValue(
                url,
                Array<NBPExchangeRates>::class.java
        )

        response!!.forEach { nbpExchangeRates ->
            nbpExchangeRates.rates.forEach {
                try {
                    result.add(convertNBPRateToRate(it, date))
                } catch (exception: Exception) {
                    //TODO Log information about changed schema
                }
            }
        }

        return result
    }

    private fun convertNBPRateToRate(nbpRate: NBPRate, date: LocalDate): Rate {
        return Rate(
                date,
                Currency.getInstance(nbpRate.code),
                LOCAL_CURRENCY,
                nbpRate.mid,
                ExchangeType.AVG,
                PROVIDER_NAME
        )
    }

    companion object {
        private val PROVIDER_NAME = ProviderEnum.NBP

        private val LOCAL_CURRENCY = Currency.getInstance("PLN")

        private const val API_URL = "https://api.nbp.pl/api"
        private const val ENDPOINT_RATES_DATE_BASED = "/exchangerates/tables/{table}/{date}"

        private val EXCHANGE_TABLES = arrayListOf("A", "B")
    }
}
