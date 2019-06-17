package gzres.exchangeRates.providers

import org.springframework.http.HttpStatus
import java.net.HttpURLConnection
import java.net.URL

abstract class RatesProvider : Provider {

    protected fun getServiceStatus(url: URL): HttpStatus {
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        return HttpStatus.valueOf(connection.responseCode)
    }
}
