package gzres.exchangeRates.providers.nbp.dao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class NBPRate(
        @JsonProperty("currency") val currency: String,
        @JsonProperty("code") val code: String,
        @JsonProperty("mid") val mid: Double
)
