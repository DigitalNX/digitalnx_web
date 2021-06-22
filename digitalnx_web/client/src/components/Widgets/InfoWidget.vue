<template>
    <MediumBoxWidget>
        <div class="info-card">
            <div class="info-card-title">
                <h2>{{ info['title'] }}</h2>
            </div>
            <div class="info-card-body">
                    <template v-if="info['items'].length >= 4">
                        <div v-for="(item, index) in info['items']" :key="index" class="info-card-item">
                            <h5>{{ item['title'] }}</h5>
                            <template v-for="content_item in item['content']" :key="content_item.index">
                                <template v-if="item['type'] == 'SENSOR'">
                                    <p> {{ content_item['device_name'] + ":"}} </p>

                                    <template v-if="content_item['sensor_type'] == 'BOOLEAN'">
                                        <label class="switch">
                                            <label>
                                            <input type="checkbox" :disabled="true"  :checked="content_item['value'] == true">
                                            <div class="slider slider-inner-button"></div>
                                            </label>
                                        </label>
                                    </template>
                                    <template v-else-if="content_item['sensor_type'] == 'FLOATING_POINT'">
                                        <b> {{ content_item['value'] }} </b>
                                    </template>

                                    <p> </p>
                                </template>
                                <template v-else>
                                    <p> {{ content_item }} </p>
                                </template>
                            </template>
                        </div>
                    </template>
                    <template v-else>
                        <div v-for="(_, index) in 4" :key="index" class="info-card-item">
                            <template v-if="index < info['items'].length">
                                <h5>{{ info['items'][index]['title'] }}</h5>
                                <p v-for="text_line in info['items'][index]['content']" :key="text_line.index">
                                    {{ text_line }}
                                </p>
                            </template>
                            <template v-else>
                                <h5>&zwnj;</h5>
                                <p>&zwnj;</p>
                            </template>
                        </div>
                    </template>
                </div>
        </div>
    </MediumBoxWidget>
</template>

<script>
import MediumBoxWidget from '../BaseWidgets/MediumBoxWidget.vue';

export default {
    name: 'InfoWidget',
    components: {
        MediumBoxWidget
    },
    props: {
        info: Object
    },
}
</script>

<style scoped>
.info-card {
    padding-left: 0.3rem;
    line-height: 1rem;
}
.info-card-title {
    padding-top: 1rem;
    padding-bottom: 1.25rem;
    letter-spacing: 0.2px;
}
.info-card-item {
    padding-bottom: 1.8rem;
}
.info-card-item h5 {
    font-weight: bold;
    padding-bottom: 0.2rem;
}
.info-card-item p {
    font-size: 15px;
    font-weight: lighter;
    display: inline;
    padding-right: 0.9rem;
}

.switch {
  position: relative;
  width: 52px;
  height: 26px;
  padding-top: 2px;
}

.switch input {
  opacity: 0;
}

.slider {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  cursor: pointer;
  background-color: #d6d6d6;
  transition: 0.35s;
}

.slider:before {
  position: absolute;
  left: 4px;
  bottom: 4px;
  height: 18px;
  width: 18px;
  content: "";
  background-color: white;
  transition: 0.35s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:checked + .slider-inner-button:before{
  transform: translateX(26px);
}

label {
    margin: 0;
}

</style>