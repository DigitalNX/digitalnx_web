<template>
    <SmallBoxWidget>
        <div id="sensor-widget-title">
            <h5>{{ group_name }}</h5>
        </div>
        <div v-if="number_of_sensors <= 3">
          <div v-bind:key="sensor.id" v-for="sensor in sensors" class="sensor-panel">
            <p class="sensor-name"> {{ sensor['device_name'] + ":" }} </p>
            <div class="sensor-value">
                  <template v-if="sensor['sensor_type'] == 'BOOLEAN'">
                      <label class="switch">
                          <label>
                          <input type="checkbox" :disabled="true" :checked="sensor['value'] == true">
                          <div class="slider slider-inner-button"></div>
                          </label>
                      </label>
                  </template>
                  <template v-else-if="sensor['sensor_type'] == 'FLOATING_POINT'">
                      <label><b> {{ sensor['value'] }} </b></label>
                  </template>
            </div>
          </div>
        </div>
     </SmallBoxWidget>
</template>

<script>
import SmallBoxWidget from '../BaseWidgets/SmallBoxWidget.vue';

export default {
    name: 'SensorWidget',
    props: {
        sensors: {
            device_name: String, 
            group_id: Number, 
            id: Number, 
            ip_address: String,
            sensor_type: String,
            value: String
        },
        group_name: String
    },
    components: {
        SmallBoxWidget
    },
    data() {
        return {
            number_of_sensors: 0,
        }
    },
    created() {
        this.number_of_sensors = Object.keys(this.sensors).length;
    }
}

</script>

<style scoped>
.sensor-panel {
  display: flex;
  margin-top: 15px;
  margin-left: 18px;
  margin-right: 18px;
  margin-bottom: 10px;
}

.sensor-panel p {
  margin-bottom: 5px;
  flex-grow: 0;
  flex-shrink: 0;
  flex-basis: 108px;
}

.sensor-panel label {
  padding-bottom: 4px;
}

.sensor-value {
  flex-grow: 1;
  margin-left: 15px;
}

#sensor-widget-title h5 {
  padding-top: 5px;
  padding-bottom: 5px;
  padding-left: 2px;
  font-weight: bold;
}

.switch {
  position: relative;
  width: 52px;
  height: 26px;
  padding-top: 2px;
}

.switch input {
  opacity: 0;
}

.slider {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  cursor: pointer;
  background-color: #d6d6d6;
  transition: 0.35s;
}

.slider:before {
  position: absolute;
  left: 4px;
  bottom: 4px;
  height: 18px;
  width: 18px;
  content: "";
  background-color: white;
  transition: 0.35s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:checked + .slider-inner-button:before{
  transform: translateX(26px);
}

label {
    margin: 0;
}
</style>
