package schwarz.it.lws.win.WeatherApi

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import schwarz.it.lws.win.model.WeatherData
import java.time.LocalDateTime

@Component
class WeatherApiClient(private val restTemplate: RestTemplate) {
    fun fetchWeatherData(city: String, days: Int): List<WeatherData> {
        val apiKey = "90e5e34736b6f2076508eced09a1124b"
        val response = restTemplate.getForObject(
            "https://api.openweathermap.org/data/2.5/forecast/daily?q=$city&cnt=$days&appid=$apiKey&units=metric",
            WeatherApiResponse::class.java
        )
        return response?.list?.mapIndexed { index, forecast ->
            WeatherData(
                id = 0,
                city = city,
                forecastDate = LocalDateTime.now().plusDays(index.toLong()),
                temperature = forecast.temp.day,
                minTemperature = forecast.temp.min,
                humidity = forecast.humidity,
                description = forecast.weather.firstOrNull()?.description ?: "",
                iconCode = forecast.weather.firstOrNull()?.icon ?: "",
                createdAt = LocalDateTime.now()
            )
        } ?: emptyList()
    }
}

data class WeatherApiResponse(
    val list: List<DailyForecast>
)

data class DailyForecast(
    val temp: Temperature,
    val humidity: Int,
    val weather: List<Weather>
)

data class Temperature(
    val day: Double,
    val min: Double
)

data class Weather(
    val description: String,
    val icon: String
)
