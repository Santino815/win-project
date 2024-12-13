package schwarz.it.lws.win

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import schwarz.it.lws.win.WeatherRepository.WeatherRepository
import schwarz.it.lws.win.model.WeatherData
import java.time.LocalDateTime

@SpringBootApplication
class WetterInformationsNewsApplication {

    	@Bean
        fun commandLineRunner( weatherRepository: WeatherRepository) : CommandLineRunner {
            return CommandLineRunner {
                val wd1 = WeatherData(0, "Heilbronn", LocalDateTime.now(), 5.1, 3.5, 70, "regnerisch", "1003d", LocalDateTime.now())
                val wd2 = WeatherData(0, "Berlin", LocalDateTime.now(), 5.1, 3.5, 70, "regnerisch", "1003d", LocalDateTime.now())
                val wd3 = WeatherData(0, "Mainz", LocalDateTime.now(), 5.1, 3.5, 70, "regnerisch", "1003d", LocalDateTime.now())

                weatherRepository.saveAll( listOf(wd1,wd2,wd3))
            }
        }
    }

    fun main(args: Array<String>) {
        runApplication<WetterInformationsNewsApplication>(*args)
    }

