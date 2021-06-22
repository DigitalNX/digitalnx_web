<template>
  <Dashboard>
    <div class="container-fluid">
        <div class="page-title">
          <h3>{{ title }}</h3>
          <h6>Version: {{ version }}</h6>
        </div>
        <div class="row">
          <div class="col-7">
            <div class="about">
              <p v-html="content"></p>
            </div>
          </div>
        </div>
      </div>
  </Dashboard>
</template>

<script>
import Dashboard from './Dashboard.vue'

export default {
  name: 'About',
  components: {
    Dashboard
  },
  props: {
    title: String,
    version: String,
    content: String,
  }
}
</script>

<style scoped>
.page-title h6 {
  display: inline;
  margin-left: 15px;
  color: grey;
}
p {
  font-size: 17px;
  margin-top: 10px;
  line-height: 1.55em;
}
.about {
  margin-left: 5px;
}
</style>