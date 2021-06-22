<template>
  <div class="container-fluid">
    <div class="row align-items-start">
    <template v-for="item in layout" :key="item.index">
      <WeatherWidget v-if="item.name == 'weather' && isAvailable.get('weather_info')"
      v-bind:weather_data="weather_data" />

      <InfoWidget v-if="item.name == 'info'" :info="info[0]" />

      <RelayControlWidget v-if="item.name == 'relay' && isAvailable.get('relays')" 
              :relays="relays.find(group => group.group_id === item.id).relays"
              :group_name="relays.find(group => group.group_id === item.id).group_name" />

      <SensorWidget v-if="item.name == 'sensor' && isAvailable.get('sensors')"
              :sensors="sensors.find(group => group.group_id === item.id).sensors"
              :group_name="sensors.find(group => group.group_id === item.id).group_name" />

      <NoteWidget v-if="item.name == 'note' && isAvailable.get('notes')"
              @notes-list-update="fetchLayout"
              :title="notes.find(x => x.id === item.id).title"
              :content="notes.find(x => x.id === item.id).content"
              :note_id="item.id" />

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

      this.sendGETRequest("/relays/").then(data => {
        if(this.objectIsNotNull(data)) {
          this.relays = data;
          this.isAvailable.set('relays', true);
        }
      });

      this.sendGETRequest("/sensors/").then(data => {
        if(this.objectIsNotNull(data)) {
          this.sensors = data;
          this.isAvailable.set('sensors', true);
        }
      });
      
      this.sendGETRequest("/weather_report").then(data => {
        if(this.objectIsNotNull(data)) {
          this.weather_data = data;
          this.isAvailable.set('weather_info', true);
        }
      });

      this.sendGETRequest("/notes/").then(data => {
        if(this.objectIsNotNull(data)) {
          this.notes = data;
          this.isAvailable.set('notes', true);
        }
      });

      this.sendGETRequest("/layout/").then(data => {
        if(this.objectIsNotNull(data)) {
          this.layout = data;
          this.isAvailable.set('layout', true);
        }
      });

      this.sendGETRequest("/info_data").then(data => {
        if(this.objectIsNotNull(data)) {
          this.info = data;
          this.isAvailable.set('info', true);
        }
      });
    },
    grabSensorData() {
      this.sendGETRequest("/sensors/").then(data => {
        if(this.objectIsNotNull(data)) {
          this.sensors = data;
        }
      })
      this.sendGETRequest("/info_data").then(data => {
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
