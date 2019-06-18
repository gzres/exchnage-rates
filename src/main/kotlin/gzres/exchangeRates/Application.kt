package gzres.exchangeRates

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class Application

fun main(args: Array<String>) {
    SpringApplicationBuilder(Application::class.java).run(*args)
}
