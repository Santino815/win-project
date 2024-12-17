package schwarz.it.lws.win.WeatherController

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import schwarz.it.lws.win.WeatherService.WeatherService
import schwarz.it.lws.win.model.WeatherData

@RestController
@RequestMapping("/api/weather")
class WeatherController(private val weatherService: WeatherService) {

    @GetMapping("/current/{city}")
    fun getCurrentWeather(@PathVariable city: String): ResponseEntity<WeatherData> {
        return try {
            val weatherData = weatherService.getWeatherForCity(city).firstOrNull()
            if (weatherData != null) {
                ResponseEntity.ok(weatherData)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/forecast/{city}")
    fun getWeatherForecast(
        @PathVariable city: String,
        @RequestParam(defaultValue = "5") days: Int
    ): ResponseEntity<List<WeatherData>> {
        return try {
            require(days in 1..5) { "Anzahl der Tage muss zwischen 1 und 5 liegen" }
            ResponseEntity.ok(weatherService.getForecast(city, days.toLong()))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(null)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}
