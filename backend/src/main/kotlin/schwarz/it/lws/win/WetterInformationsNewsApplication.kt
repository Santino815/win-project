package schwarz.it.lws.win

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import schwarz.it.lws.win.WeatherApi.WeatherApiClient

@SpringBootApplication
class WetterInformationsNewsApplication {
    @Bean
    fun commandLineRunner(weatherApiClient: WeatherApiClient): CommandLineRunner {
        return CommandLineRunner {
            weatherApiClient.fetchWeatherData("Heilbronn", 3)
        }
    }

}


fun main(args: Array<String>) {
    runApplication<WetterInformationsNewsApplication>(*args)
}

