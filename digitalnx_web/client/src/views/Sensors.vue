<template>
  <Dashboard>
    <div class="container-fluid">
      <div class="page-title">
        <h3> Sensors </h3>
      </div>
        <div v-if="isAvailable" class="row align-items-start">
          <SensorWidget v-for="(sensor_group, index) in sensors" v-bind:key="index" :sensors="sensor_group.sensors" :group_name="sensor_group.group_name" /> 
      </div> 
    </div>
  </Dashboard>
</template>

<script>
import Dashboard from './Dashboard.vue'
import SensorWidget from '../components/Widgets/SensorWidget.vue';

export default {
  name: 'Sensors',
  components: {
    Dashboard,
    SensorWidget,
  },
  data() {
    return { 
      sensors: [],
      isAvailable: Boolean
    }
  },
  methods: {
    grabSensorData() {
      this.sendGETRequest("/sensors/").then(data => {
          this.sensors = data;
          this.isAvailable = true;
      });
    }
  },
  created() {
    this.grabSensorData();
    this.timer = setInterval(this.grabSensorData, 4500)
  },
}
</script>
