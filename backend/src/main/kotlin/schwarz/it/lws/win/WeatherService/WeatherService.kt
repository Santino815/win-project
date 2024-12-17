package schwarz.it.lws.win.WeatherService

import org.springframework.stereotype.Service
import org.springframework.cache.annotation.Cacheable
import schwarz.it.lws.win.WeatherRepository.WeatherRepository
import schwarz.it.lws.win.WeatherApi.WeatherApiClient
import schwarz.it.lws.win.model.WeatherData
import java.time.LocalDateTime
import org.slf4j.LoggerFactory

@Service
class WeatherService(
    private val weatherRepository: WeatherRepository,
    private val weatherApiClient: WeatherApiClient
) {
    private val logger = LoggerFactory.getLogger(WeatherService::class.java)

    @Cacheable("weatherCache")
    fun getWeatherForCity(city: String): List<WeatherData> {
        logger.info("Fetching weather data for city: $city")
        val startDate = LocalDateTime.now()
        val endDate = LocalDateTime.now().plusDays(1)
        var weatherData = weatherRepository.findByCityAndForecastDateBetween(city, startDate, endDate)

        if (weatherData.isEmpty()) {
            weatherData = weatherApiClient.fetchWeatherData(city, 1)
            weatherRepository.saveAll(weatherData)
        }

        return weatherData
    }

    @Cacheable("forecastCache")
    fun getForecast(city: String, days: Long): List<WeatherData> {
        logger.info("Fetching forecast for city: $city, days: $days")
        val startDate = LocalDateTime.now()
        val endDate = LocalDateTime.now().plusDays(days)
        var weatherData = weatherRepository.findByCityAndForecastDateBetween(city, startDate, endDate)

        if (weatherData.size < days) {
            weatherData = weatherApiClient.fetchWeatherData(city, days.toInt())
            weatherRepository.saveAll(weatherData)
        }

        return weatherData
    }
}
