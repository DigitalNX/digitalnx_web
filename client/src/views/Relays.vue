<template>
  <Dashboard>
    <div class="container-fluid">
      <div class="page-title">
        <h3> Relays </h3>
      </div>
        <div v-if="isAvailable" class="row align-items-start">
          <RelayControlWidget v-for="(relayGroup, index) in relays" v-bind:key="index" :relays="relayGroup.relays" :groupName="relayGroup.name" /> 
      </div> 
    </div>
  </Dashboard>
</template>

<script>
import Dashboard from './Dashboard.vue'
import RelayControlWidget from '../components/Widgets/RelayControlWidget.vue';

export default {
  name: 'Relays',
  components: {
    Dashboard,
    RelayControlWidget,
  },
  data() {
    return { 
      relays: [],
      segmented_relays: [],
      isAvailable: Boolean
    }
  },
  created() {
    this.sendGETRequest("/relays/").then(data => {
        this.relays = data['_embedded']['relayGroupList'];
        this.isAvailable = true;
    });
  }
}
</script>
