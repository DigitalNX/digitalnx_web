<template>
  <div class="container-fluid">
    <div class="row align-items-start">
    <template v-for="item in layout" :key="item.index">
      <WeatherWidget v-if="item.widgetType == 'WEATHER' && isAvailable.get('WEATHER')"
      v-bind:weather_data="weather_data" />

      <InfoWidget v-if="item.widgetType == 'INFO' && isAvailable.get('INFO')" :info="info"/>

      <RelayControlWidget v-if="item.widgetType == 'RELAY_GROUP' && isAvailable.get('RELAY_GROUP')" 
              :relays="relays.find(group => group.id === item.id).relays"
              :groupName="relays.find(group => group.id === item.id).relayGroupName" />

      <SensorWidget v-if="item.widgetType == 'SENSOR_GROUP' && isAvailable.get('SENSOR_GROUP')"
              :sensors="sensors.find(group => group.id === item.id).sensors"
              :groupName="sensors.find(group => group.id === item.id).sensorGroupName" />

      <NoteWidget v-if="item.widgetType == 'NOTE' && isAvailable.get('NOTE')"
              @notes-list-update="fetchLayout"
              :title="notes.find(x => x.id === item.id).title"
              :body="notes.find(x => x.id === item.id).body"
              :id="item.id" />

    </template>
    </div>
  </div>
</template>

<script>
import WeatherWidget from './Widgets/WeatherWidget.vue';
import RelayControlWidget from './Widgets/RelayControlWidget.vue';
import SensorWidget from './Widgets/SensorWidget.vue';
import NoteWidget from './Widgets/NoteWidget.vue';
import InfoWidget from './Widgets/InfoWidget.vue'

export default {
  name: 'DashboardLayout',
  components: {
    WeatherWidget,
    RelayControlWidget,
    SensorWidget,
    NoteWidget,
    InfoWidget
  },
  data() {
    return {
        layout: [],
        relays: [],
        sensors: [],
        segmented_relays: [],
        weather_data: [],
        notes: [],
        info: [],
        isAvailable: new Map(),
    }
  },
  methods: {
    objectIsNotNull(obj) {
      return !(Object.keys(obj).length === 0 && obj.constructor === Object);
    },
    fetchLayout() {
      this.sendGETRequest("/weatherForecast").then(data => {
        if(this.objectIsNotNull(data)) {
          this.weather_data = data;
          this.isAvailable.set('WEATHER', true)
        }
      });

      this.sendGETRequest("/relays").then(data => {
        if(this.objectIsNotNull(data)) {
          this.relays = data['_embedded']['relayGroupList'];
          this.isAvailable.set('RELAY_GROUP ', true)
        }
      });

      this.sendGETRequest("/sensors").then(data => {
        if(this.objectIsNotNull(data)) {
          this.sensors = data['_embedded']['sensorGroupList'];
          this.isAvailable.set('SENSOR_GROUP', true);
        }
      });
      
      this.sendGETRequest("/notes").then(data => {
        if(this.objectIsNotNull(data)) {
          this.notes = data['_embedded']['noteList'];
          this.isAvailable.set('NOTE', true);
        }
      });

      this.sendGETRequest("/layout").then(data => {
        if(this.objectIsNotNull(data)) {
          this.layout = data['_embedded']['jSONObjectList'];
          this.isAvailable.set('LAYOUT', true);
        }
      });

      this.sendGETRequest("/infoData").then(data => {
        if(this.objectIsNotNull(data)) {
          this.info = data;
          this.isAvailable.set('INFO', true);
        }
      });
    },
    grabSensorData() {
      this.sendGETRequest("/sensors").then(data => {
        if(this.objectIsNotNull(data)) {
          this.sensors = data['_embedded']['sensorGroupList'];
        }
      })
      this.sendGETRequest("/infoData").then(data => {
        if(this.objectIsNotNull(data)) {
          this.info = data;
        }
      })
    }
  },
  mounted() {
    this.fetchLayout();
  },
  created() {
    this.timer = setInterval(this.grabSensorData, 2000);
  }
}
</script>
