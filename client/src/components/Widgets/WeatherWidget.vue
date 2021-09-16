<template>
    <MediumBoxWidget>
        <div class="weather-card">
            <div class="weather-card-body">
                <div class="weather-temperature">
                    <h4 class="display-3">{{ weather_data["list"][0].temperature }}<span class="symbol">°</span>C</h4>
                </div>
                <i class="weather-logo fas fa-cloud"></i>
                <div class="weather-date-location">
                    <p>
                        <span class="weather-date">{{ weather_data.date }}</span>
                        <span>&nbsp;-&nbsp;</span>
                        <span class="weather-location">{{ weather_data.location }}</span>
                    </p>
                </div>
            </div>
            <div class="weather-card-forecast">
                <div class="d-flex weakly-weather">
                    <div v-for="temp_info in weather_data['list']" v-bind:key="temp_info.day" class="weakly-weather-item">
                        <p class="mb-0">{{ temp_info.day }}</p><i class="mdi mdi-weather-cloudy"></i>
                        <p class="mb-0">{{ temp_info.temperature }}&#176;C</p>

                    </div>
                </div>
            </div> 
        </div> 
    </MediumBoxWidget>
</template>

<script>
import MediumBoxWidget from '../BaseWidgets/MediumBoxWidget.vue';

export default {
    name: 'WeatherWidget',
    components: {
        MediumBoxWidget
    },
    props: {
        weather_data: {
            date: String,
            location: String,
            list: Array
        }
        // TODO: Polution too?
    }
}
</script>

<style scoped>
.weather-card {
    background-image: linear-gradient(rgba(255,255,255,0.1), rgba(255,255,255,0.1)), url('~@/assets/pics/landscape_illustration.svg');
    background-size: cover;
    border-radius: 8px;
}

.weather-card-body {
    padding-left: 35px;
    color: rgb(58, 58, 58);
}

.weather-temperature {
    display: inline-block;
}

.weather-temperature h4 {
    float: left;
}

.weather-logo {
    float: right;
    color: #4576b9;
    margin-top: 70px; 
    margin-right: 80px; 
    font-size: 90px;
}

.weather-card-body {
    padding-bottom: 10.5em;
}

.weather-card-forecast {
    padding-bottom: 8px;
}

.weather-temperature {
    padding-top: 40px; 
}

.weather-temperature p {
    padding-left: 5px;
}

.weather-date-location {
    padding-left: 5px;
    font-weight: bold;
}

.weakly-weather-item {
    flex: 14%; /* 100/7 */
    color: white;
    padding-top: 9px;
    padding-bottom: 11px;
    text-align: center;
}

.weakly-weather-item i {
    font-size: 1.2rem;
}
</style>
