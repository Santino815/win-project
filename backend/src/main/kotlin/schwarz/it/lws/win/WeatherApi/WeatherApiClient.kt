package schwarz.it.lws.win.WeatherApi

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import schwarz.it.lws.win.ExceptionHandler.WeatherApiException
import schwarz.it.lws.win.model.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class WeatherApiClient(private val restTemplate: RestTemplate) {
    @Value("\${openweathermap.api.key}")
    private lateinit var apiKey: String

    private val logger = LoggerFactory.getLogger(WeatherApiClient::class.java)

    fun fetchWeatherData(city: String, days: Int): List<WeatherData> {
        try {
            val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=$apiKey&units=metric"
            val response: WeatherApiResponse = restTemplate.getForObject(url, WeatherApiResponse::class.java)
                ?: throw WeatherApiException("Null response from API", null)

            return response.list
                .groupBy { it.dt_txt.substring(0, 10) }
                .map { (date, forecasts) ->
                    val minTemp = forecasts.minOf { it.main.temp_min }
                    val maxTemp = forecasts.maxOf { it.main.temp_max }
                    val avgTemp = forecasts.map { it.main.temp }.average()
                    val avgHumidity = forecasts.map { it.main.humidity }.average().toInt()
                    val mostFrequentDescription = forecasts.groupBy { it.weather.firstOrNull()?.description }
                        .maxByOrNull { it.value.size }?.key ?: ""
                    val mostFrequentIcon = forecasts.groupBy { it.weather.firstOrNull()?.icon }
                        .maxByOrNull { it.value.size }?.key ?: ""

                    WeatherData(
                        id = 0,
                        city = response.city.name,
                        forecastDate = LocalDate.parse(date),
                        temperature = avgTemp,
                        minTemperature = minTemp,
                        maxTemperature = maxTemp,
                        humidity = avgHumidity,
                        description = mostFrequentDescription,
                        iconCode = mostFrequentIcon,
                        createdAt = LocalDateTime.now()
                    )
                }
                .take(days)
        } catch (e: Exception) {
            logger.error("Error fetching weather data: ${e.message}")
            throw WeatherApiException("Failed to fetch weather data", e)
        }
    }
}


data class WeatherApiResponse(
    val list: List<DailyForecast>,
    val city: DailyForecastCity
)

data class DailyForecast(
    val dt_txt: String,
    val main: DailyForecastMain,
    val weather: List<DailyForecastWeatherEntry>,
)

data class DailyForecastMain(
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double,
    val humidity: Int,
    val pressure: Int,
)

data class DailyForecastWeatherEntry(
    val description: String,
    val icon: String,
)

data class DailyForecastCity(
    val name: String,
)