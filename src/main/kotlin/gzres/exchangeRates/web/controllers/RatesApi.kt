package gzres.exchangeRates.web.controllers

import gzres.exchangeRates.rates.RateNotFoundException
import gzres.exchangeRates.rates.RatesRepository
import gzres.exchangeRates.rates.dao.Rate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/rates")
class RatesApi(
        @Autowired private val ratesRepo: RatesRepository
) {
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): Rate {

        val uid = UUID.fromString(id)

        return ratesRepo.findById(uid)
                .orElseThrow { RateNotFoundException(uid) }
    }

    @GetMapping
    fun getAll(): List<Rate> {
        return ratesRepo.findAll()
    }

    @GetMapping("/date/{date}")
    fun getByDate(@PathVariable date: String): List<Rate> {
        val localDate = LocalDate.parse(date)
        return ratesRepo.findByDate(localDate)
    }

    @DeleteMapping("/{id}")
    fun deleteRate(@PathVariable id: UUID) {
        ratesRepo.deleteById(id)
    }
}
