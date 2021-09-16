<template>
  <Dashboard>
    <div class="container">
      <div class="row align-items-start">
        <div class="col-7">
          <div class="page-title">
            <h4> Note </h4>
          </div>
          <div class="form-group">
            <span> Title: </span>
            <input type="text" class="form-control" v-model="note.title" /> 
          </div>
          <div class="form-group">
          <label> Content: </label>
            <textarea class="form-control" v-model="note.body" rows="8"> </textarea>
          </div>
          <div class="form-group">
            <div class="buttons">
              <button id="submit-button" v-on:click="update()" class="btn btn-primary">Update</button>

              <a @click="$router.go(-1)"> 
              <button id="back-button" type="button" class="btn btn-outline-secondary">Back</button>
              </a>
          </div>
            <div class="info-message" v-if="noteUpdated"> 
              * Note has been updated successfuly.
            </div>
          </div>
        </div>
      </div> 
    </div>
  </Dashboard>
</template>

<script>
import Dashboard from '../Dashboard.vue'

export default {
  name: "ViewNote",
  components: {
    Dashboard
  },
  props: {
      title: String,
      body: String,
      color: String,
      id: String,
  },
  data() {
    return {
      note: {
        title: "",
        body: "",
        color: "",
      },
      noteUpdated: false,
    }
  },
  created() {
      this.note.title = this.$props.title;
      this.note.body = this.$props.body;
      this.note.color = this.$props.color;
  },
  methods: {
      async update() {
            this.sendPOSTRequest("/note/"+ this.$props.id + "/update", {
                title: this.note.title,
                body: this.note.body,
            });

            this.noteUpdated = true;
        }
    },
}
</script>

<style scoped>
label {
  display: inline;
}

.page-title {
  margin-bottom: 10px;
}

.buttons {
  margin-top: 20px;
}

#submit-button {
  float: right;
}

.info-message {
  color: #1e92f4;
  margin-top: 25px;
}
</style>