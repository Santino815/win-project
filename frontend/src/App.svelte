<script>
  import { weatherData, fetchWeatherData } from "./weatherStore.js";
  import { onMount } from "svelte";

  import Sunny from "./assets/fill/all/horizon.svg";
  import Cloudy from "./assets/fill/all/overcast.svg";
  import Partlycloudy from "./assets/fill/all/overcast-day.svg";
  import Rainy from "./assets/fill/all/rain.svg";
  import Snow from "./assets/fill/all/snow.svg";

  import Temp from "./assets/fill/all/thermometer.svg";
  import TempMin from "./assets/fill/all/thermometer-colder.svg";
  import TempMax from "./assets/fill/all/thermometer-warmer.svg";
  import Humidity from "./assets/fill/all/humidity.svg";

  let city = "";
  let selectedCity = "Stadtname";

  // Holen der Wetterdaten, wenn die Komponente geladen wird
  onMount(() => {
    fetchWeatherData(selectedCity);
  });

  function addCity() {
    console.log("Button clicked, city:", city);
    if (city) {
      selectedCity = city;
      fetchWeatherData(selectedCity);
    }
  }

  const weatherSymbols = {
    sunny: Sunny,
    cloudy: Cloudy,
    partlycloudy: Partlycloudy,
    rainy: Rainy,
    snow: Snow,
  };
</script>

<div class="d-flex flex-column align-items-center my-3 rounded">
  <div class="w-75">
    <div class="card-body">
      <h2 class="card-title text-center my-5">Wetter Informationen</h2>
      <div class="input-group mb-3">
        <input
          type="text"
          class="form-control"
          placeholder="Deine Stadt..."
          bind:value={city}
          on:keydown={(e) => e.key === "Enter" && addCity()}
        />
        <button
          class="btn btn-outline-primary"
          type="button"
          on:click={addCity}
        >
          Suchen
        </button>
      </div>
      <!-- 
              data-bs-ride="carousel"
-->
      <div
        id="carouselExampleControls"
        class="carousel slide"
        data-bs-interval="10000"
      >
        <div class="carousel-inner">
          {#each $weatherData.forecast as { date, temperature, humidity, weather }, index}
            <div class="carousel-item {index === 0 ? 'active' : ''}">
              <div class="card text-center">
                <div class="card-header">
                  <h4 class="card-title">Montag</h4>
                  <p class="card-subtitle text-muted">{date}</p>
                </div>
                <img
                  src={weatherSymbols[weather]}
                  class="card-img-top mx-auto w-50"
                  alt="Sunset"
                />
                <div class="card-body">
                  <h4 class="card-title">{selectedCity}</h4>
                  <p class="card-subtitle text-muted">
                    Hier finden Sie Informationen zum Wetter in Ihrer Stadt.
                  </p>
                  <div class="d-flex flex-column align-items-center">
                    <div class="d-flex align-items-center mb-2">
                      <img src={Temp} class="icon me-2" alt="Temperatur" />
                      <p class="mb-0">20°C</p>
                    </div>
                    <div class="d-flex align-items-center mb-2">
                      <img
                        src={TempMin}
                        class="icon me-2"
                        alt="Min Temperatur"
                      />
                      <p class="mb-0">15°C</p>
                    </div>
                    <div class="d-flex align-items-center mb-2">
                      <img
                        src={TempMax}
                        class="icon me-2"
                        alt="Max Temperatur"
                      />
                      <p class="mb-0">25°C</p>
                    </div>
                    <div class="d-flex align-items-center">
                      <img
                        src={Humidity}
                        class="icon me-2"
                        alt="Luftfeuchtigkeit"
                      />
                      <p class="mb-0">60%</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          {/each}
        </div>
        <button
          class="carousel-control-prev"
          type="button"
          data-bs-target="#carouselExampleControls"
          data-bs-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button
          class="carousel-control-next"
          type="button"
          data-bs-target="#carouselExampleControls"
          data-bs-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
    </div>
  </div>
</div>
