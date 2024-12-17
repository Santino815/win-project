<script>
import { onMount } from 'svelte';
import { weatherData } from './weatherStore.js';

let city = 'Berlin';
let loading = false;
let error = null;

async function fetchWeather() {
    loading = true;
    error = null;
    try {
        const response = await fetch(`http://localhost:8080/api/weather/forecast/${city}`);
        if (!response.ok) throw new Error('Failed to fetch weather data');
        const data = await response.json();
        weatherData.set(data);
    } catch (e) {
        error = e.message;
    } finally {
        loading = false;
    }
}

onMount(fetchWeather);
</script>

<main>
    <input bind:value={city} on:change={fetchWeather}>
    {#if loading}
        <p>Loading...</p>
    {:else if error}
        <p>Error: {error}</p>
    {:else}
        {#each $weatherData as day}
            <div>
                <h3>{day.forecastDate}</h3>
                <p>Temperature: {day.temperature}°C</p>
                <p>Humidity: {day.humidity}%</p>
                <p>{day.description}</p>
            </div>
        {/each}
    {/if}
</main>
