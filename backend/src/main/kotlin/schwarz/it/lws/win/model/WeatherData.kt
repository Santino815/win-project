package schwarz.it.lws.win.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "weather_data")
data class WeatherData(
    @Id
    @GeneratedValue
    val id: Long,

    @Column(nullable = false)
    val city: String,

    @Column(nullable = false)
    val forecastDate: LocalDate,

    @Column(nullable = false)
    val temperature: Double,

    @Column(nullable = false)
    val minTemperature: Double,

    val maxTemperature: Double,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val iconCode: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime,

    @Column(nullable = false)
    val humidity: Int
)
