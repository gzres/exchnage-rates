package gzres.exchangeRates.rates

import java.util.*

internal class RateNotFoundException(id: UUID) : RuntimeException("Could not find rate $id")
