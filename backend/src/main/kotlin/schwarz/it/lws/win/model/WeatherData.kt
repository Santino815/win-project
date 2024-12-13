package schwarz.it.lws.win.model

import jakarta.persistence.*
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
    val forecastDate: LocalDateTime,

    @Column(nullable = false)
    val temperature: Double,

    @Column(nullable = false)
    val minTemperature: Double,

    @Column(nullable = false)
    val humidity: Int,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val iconCode: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime
)
