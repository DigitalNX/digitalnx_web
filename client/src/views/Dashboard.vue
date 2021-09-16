<template>
  <div>
    <TopNavbar />
    <div class="wrapper">
      <Sidebar :navItems="navItems" />
      <slot></slot>
    </div>
  </div>
</template>

<script>
import TopNavbar from '../components/TopNavbar.vue'
import Sidebar from '../components/Sidebar.vue'

export default {
  name: 'Dashboard',
  components: {
    TopNavbar,
    Sidebar
  },
  data() {
    return {
      navItems: []
    }
  },
  created() {
    this.navItems = [
        {
          name: 'Home',
          class: 'fas fa-home',
        },
        {
          name: 'Relays',
          class: 'fas fa-bolt',
        }, 
        {
          name: 'Sensors',
          class: 'fas fa-wifi',
        }, 
        {
          name: 'Notes',
          class: 'fas fa-file',
        }, 
        {
          name: 'About',
          class: 'fas fa-info-circle',
        }, 
        {
          name: 'Settings',
          class: 'fas fa-cog',
        },
      ]
  }
}
</script>