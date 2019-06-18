package gzres.exchangeRates.providers.nbp.dao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class NBPExchangeRates(
        @JsonProperty("table") val table: String,
        @JsonProperty("no") val no: String,
        @JsonProperty("effectiveDate") val effectiveDate: String,
        @JsonProperty("rates") val rates: List<NBPRate>
)
