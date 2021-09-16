<template>
  <Dashboard>
    <div class="container-fluid">
      <div class="page-title">
        <h3> Sensors </h3>
      </div>
        <div v-if="isAvailable" class="row align-items-start">
          <SensorWidget v-for="(sensorGroup, index) in sensors" v-bind:key="index" :sensors="sensorGroup.sensors" :groupName="sensorGroup.name" /> 
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
          this.sensors = data['_embedded']['sensorGroupList'];
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
