package schwarz.it.lws.win.WeatherService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import schwarz.it.lws.win.WeatherRepository.WeatherRepository
import schwarz.it.lws.win.WeatherApi.WeatherApiClient
import schwarz.it.lws.win.model.WeatherData
import java.time.LocalDateTime

@Service
class WeatherService(
    private val weatherRepository: WeatherRepository,
    private val weatherApiClient: WeatherApiClient
) {
    @Autowired
    private lateinit var restTemplate: RestTemplate
    fun getWeatherForCity(city: String): List<WeatherData> {
        val startDate = LocalDateTime.now()
        val endDate = LocalDateTime.now().plusDays(1)
        var weatherData = weatherRepository.findByCityAndForecastDateBetween(city, startDate, endDate)

        if (weatherData.isEmpty()) {
            weatherData = weatherApiClient.fetchWeatherData(city, 1)
            weatherRepository.saveAll(weatherData)
        }

        return weatherData
    }

    fun getForecast(city: String, days: Long): List<WeatherData> {
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
