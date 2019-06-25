package gzres.exchangeRates.providers.ecb

import gzres.exchangeRates.providers.RatesProvider
import gzres.exchangeRates.rates.dao.ExchangeType
import gzres.exchangeRates.rates.dao.ProviderEnum
import gzres.exchangeRates.rates.dao.Rate
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.net.URL
import java.time.LocalDate
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

@Service
class ECBProvider : RatesProvider() {

    private val logger: Logger = LogManager.getLogger()

    override fun getExchangeRates(date: LocalDate): List<Rate> {
        val url = URL(API_URL + ENDPOINT_RATES_LAST_90DAYS)

        return when (val serviceStatus = getServiceStatus(url)) {
            HttpStatus.OK -> {
                parseExchangeRate(url)
            }
            HttpStatus.NOT_FOUND -> {
                logger.warn("Not found data for $PROVIDER_NAME rates for date $date")
                ArrayList()
            }
            else -> {
                logger.error("Not handled status: $serviceStatus during processing $PROVIDER_NAME rates")
                ArrayList()
            }
        }
    }

    //WARNING:  Takes a lot of time and resources
    fun getWholeHistory() : List<Rate> {
        return parseExchangeRate(URL(API_URL + ENDPOINT_RATES_WHOLE_HISTORY))
    }

    private fun parseExchangeRate(url: URL): ArrayList<Rate> {
        val result = ArrayList<Rate>()

        try {

            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream())!!

            val documentNodes = document.documentElement.childNodes!!

            for (i in 0 until documentNodes.length) {
                if (documentNodes.item(i).nodeName === "Cube") {
                    val cubeContainers = documentNodes.item(i).childNodes

                    (0 until cubeContainers.length).forEach { k ->
                        val cubesList = cubeContainers.item(k)

                        if (cubesList is Element) {
                            val date = LocalDate.parse(cubesList.attributes.getNamedItem("time").nodeValue)!!
                            (0 until cubesList.getChildNodes().length).forEach { j ->
                                val cube = cubesList.getChildNodes().item(j)
                                if (cube is Element) {
                                    result.add(convertToRate(cube, date))
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logger.error("Error during parsing $PROVIDER_NAME rates", e)
        }

        return result
    }

    private fun convertToRate(cube: Node, date: LocalDate): Rate {
        return Rate(
                date,
                Currency.getInstance(cube.attributes.getNamedItem("currency").nodeValue),
                LOCAL_CURRENCY,
                cube.attributes.getNamedItem("rate").nodeValue.toDouble(),
                ExchangeType.AVG,
                PROVIDER_NAME
        )
    }

    companion object {
        private val PROVIDER_NAME = ProviderEnum.ECB

        private val LOCAL_CURRENCY = Currency.getInstance("EUR")

        private const val API_URL = "https://www.ecb.europa.eu"
        private const val ENDPOINT_RATES_LAST_90DAYS = "/stats/eurofxref/eurofxref-hist-90d.xml"
        private const val ENDPOINT_RATES_WHOLE_HISTORY = "/stats/eurofxref/eurofxref-hist.xml"
    }
}
