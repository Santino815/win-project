package schwarz.it.lws.win.WeatherController

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import schwarz.it.lws.win.WeatherService.WeatherService
import schwarz.it.lws.win.model.WeatherData

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = ["http://localhost:5173"])
class WeatherController(private val weatherService: WeatherService) {

    @GetMapping("/current/{city}")
    fun getCurrentWeather(@PathVariable city: String): ResponseEntity<WeatherData> {
        val weatherData = weatherService.getWeatherForCity(city).firstOrNull()
        return if (weatherData != null) {
            ResponseEntity.ok(weatherData)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/forecast/{city}")
    fun getWeatherForecast(
        @PathVariable city: String,
        @RequestParam(defaultValue = "5") days: Int
    ): ResponseEntity<List<WeatherData>> {
        require(days in 1..5) { "Anzahl der Tage muss zwischen 1 und 5 liegen" }
        return ResponseEntity.ok(weatherService.getForecast(city, days.toLong()))
    }
}
