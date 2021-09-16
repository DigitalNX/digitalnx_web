import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/store'
import axios from 'axios'

createApp(App).use(router).use(store).mixin({
  methods: {
   async sendGETRequest(route) {
        let res = axios.get(this.$store.state.resource_server + route, { headers: { Authorization: "Bearer " + this.$store.state.token }})
        let data = (await res).data
        return data
    },
    async sendPOSTRequest(route, obj) {
        let res = axios.post(this.$store.state.resource_server + route, obj, { headers: { Authorization: "Bearer " + this.$store.state.token }})
        let data = (await res).data
        return data
    }
  },
}).component("modal", {
  template: '#modal-template',
}).mount('#app')

import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
import '@fortawesome/fontawesome-free/css/all.css'
import '@fortawesome/fontawesome-free/js/all.js'