import { writable } from "svelte/store";

export const weatherData = writable({
  city: "",
  forecast: [],
});

export const fetchWeatherData = async (city) => {
  const simulatedData = {
    city,
    forecast: [
      {
        date: "11. Dezember 2024",
        temperature: { current: 20, min: 10, max: 30 },
        humidity: 60,
        weather: "snow",
      },
      {
        date: "12. Dezember 2024",
        temperature: { current: 10, min: 20, max: 30 },
        humidity: 70,
        weather: "sunny",
      },
      {
        date: "13. Dezember 2024",
        temperature: { current: 20, min: 10, max: 30 },
        humidity: 40,
        weather: "cloudy",
      },
      {
        date: "14. Dezember 2024",
        temperature: { current: 10, min: 0, max: 40 },
        humidity: 20,
        weather: "rainy",
      },
    ],
  };
  weatherData.set(simulatedData);
};
