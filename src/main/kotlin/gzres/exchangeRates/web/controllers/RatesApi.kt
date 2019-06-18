package gzres.exchangeRates.web.controllers

import gzres.exchangeRates.rates.RatesRepository
import gzres.exchangeRates.rates.dao.ExchangeType
import gzres.exchangeRates.rates.dao.ProviderEnum
import gzres.exchangeRates.rates.dao.Rate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*


@RestController
class RatesApi(
        @Autowired private val ratesRepo: RatesRepository
) {
    @GetMapping("/rates")
    fun getAll(): List<Rate> {
        return ratesRepo.findAll()
    }

    @PostMapping("/rates/date")
    fun getByDate(@RequestParam date: LocalDate): List<Rate> {
        return ratesRepo.findByDate(date)
    }

    @GetMapping("/rates/clean")
    fun clean(): List<Rate> {
        ratesRepo.deleteAll()
        return ratesRepo.findAll()
    }
}
