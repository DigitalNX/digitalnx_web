<template>
  <SmallBoxWidget>
    <button
      type="button"
      v-on:click="remove()"
      class="btn btn-outline-danger btn-sm ">
      Remove
    </button>
     <router-link :to="{
          name: 'ViewNote',
          params: {
              title: title,
              body: body,
              id: id }}"> 
      <button type="button" class="btn btn-outline-secondary btn-sm">View</button>
    </router-link>
    <h5 v-if="title" class="card-title">
      {{ title }}
    </h5>

    <div v-if="body.length < 195">
      <p class="card-text">{{ body }}</p>
    </div>
    <div v-else>
      <p class="card-text">
        {{ content.substring(0, 195) + "..." }}
        <router-link :to="{
              name: 'ViewNote',
              params: {
                  title: title,
                  body: body,
                  id: id
                }
              }"> 
              view more
        </router-link>
      </p>
    </div>
  </SmallBoxWidget>
</template>

<script>
import SmallBoxWidget from "../BaseWidgets/SmallBoxWidget.vue";

export default {
  name: "NoteWidget",
  props: {
        title: String,
        body: String,
        color: String,
        id: Number,
  },
  emits: ["notes-list-update"],
  components: {
    SmallBoxWidget,
  },
  methods: {
    async remove() {
      await this.sendDELETERequest('/note/' + this.$props.id)
      this.$emit('notes-list-update');
    },
  },
};
</script>

<style scoped>
.card-text {
  padding-bottom: 17px;
}

h5 {
  margin-top: 5px;
}

button {
  display: inline;
  float: right;
  margin-bottom: 20px;
}

button {
  margin-left: 5px;
}

h5 {
  font-size: 18px;
  font-weight: bolder;
}

p {
  font-size: 16px;
}
</style>
