<template>
  <Dashboard>
    <div class="container">
      <div class="row align-items-start">

        <div class="col-7">
          <div class="page-title">
            <h4> New Note </h4>
          </div>
          <div class="form-group">
            <span> Title: </span>
            <input type="text" class="form-control" v-model="note.title" /> 
          </div>
          <div class="form-group">
          <label> Content: </label>
            <textarea class="form-control" v-model="note.body"> </textarea>
          </div>
          <div class="form-group">
            <div class="buttons">
              <button id="submit-button" v-on:click="submit()" class="btn btn-primary">Submit</button>

              <a @click="$router.go(-1)"> 
              <button id="back-button" type="button" class="btn btn-outline-secondary">Back</button>
              </a>
          </div>
            <div v-if="noteIsEmpty" class="error-message"> 
              * Note title or body cannot be empty!
            </div>
            <div v-else-if="noteSubmitted" class="success-message"> 
              * Note has been created successfuly.
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
  name: "NewNote",
  components: {
    Dashboard
  },
  data() {
    return {
      note: {
        title: "",
        body: "",
        color: "",
      },
      noteSubmitted: false,
      noteIsEmpty: false
    };
  },
  methods: {
    async submit() {
      if(this.note.title == "" || this.note.body == "") {
        this.noteIsEmpty = true;
      } else {
        this.noteIsEmpty = false;
        this.sendPOSTRequest("/note/create", {
          title: this.note.title,
          body: this.note.body,
        });
        this.noteSubmitted = true;
      }
    },
  },
};
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

.success-message {
  color: green;
  margin-top: 25px;
}

.error-message {
  color: red;
  margin-top: 25px;
}
</style>