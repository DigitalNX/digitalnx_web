<template>
    <SmallBoxWidget>
        <div id="relay-widget-title">
            <h5>{{ group_name }}</h5>
        </div>
        <div v-if="number_of_relays <= 3" >
            <div class="relay-control">
                <div v-bind:key="relay.id" v-for="relay in relays.slice(0, number_of_relays)" class="relay">
                    <div class="canvas-wrapper group">
                    <label class="switch">
                    <label>
                      <input v-model="relay.status" v-on:change="relayControl(relay.id, !relay.status)" id="{{ relay.id }}"
                                  type="checkbox" :checked="relay.status == true" :disabled="relay.controlled_by_scheduler">
                      <div v-if="relay.controlled_by_scheduler" class="slider slider-inner-button" data-toggle="tooltip" title="Controlled by scheduler" data-delay='{"show":"0", "hide":"0"}'></div>
                      <div v-else class="slider slider-inner-button"></div>
                    </label>
                    </label>
                    <template v-if="relay.controlled_by_scheduler">
                      <a href="#" @click="setSelectedRelayId(relay.id)" data-bs-toggle="modal" data-bs-target="#RelaySchedulerModal"><b class="relay-text" style="color: green"> {{ relay.device_name }}</b></a>
                    </template>
                    <template v-else>
                      <a href="#" @click="setSelectedRelayId(relay.id)" data-bs-toggle="modal" data-bs-target="#RelaySchedulerModal"><span class="relay-text"> {{ relay.device_name }}</span></a>
                    </template>

                    </div>
                </div>
            </div>
        </div>
        <div v-else-if="number_of_relays > 3 && number_of_relays <= 6">
          <div class="relay-control row ">
            <div class="col-md-6 p-3" >
                <div v-bind:key="relay.id" v-for="relay in relays.slice(0,3)" class="relay">
                    <div class="canvas-wrapper group">
                    <label class="switch">
                    <label>
                      <input v-model="relay.status" v-on:change="relayControl(relay.id, !relay.status)" id="{{ relay.id }}"
                                  type="checkbox" :checked="relay.status == true" :disabled="relay.controlled_by_scheduler">
                      <div v-if="relay.controlled_by_scheduler" class="slider slider-inner-button" data-toggle="tooltip" title="Controlled by scheduler" data-delay='{"show":"0", "hide":"0"}'></div>
                      <div v-else class="slider slider-inner-button"></div>
                    </label>
                    </label>
                    <template v-if="relay.controlled_by_scheduler">
                      <a href="#" @click="setSelectedRelayId(relay.id)" data-bs-toggle="modal" data-bs-target="#RelaySchedulerModal"><b class="relay-text" style="color: green"> {{ relay.device_name }}</b></a>

                    </template>
                    <template v-else>
                      <a href="#" @click="setSelectedRelayId(relay.id)" data-bs-toggle="modal" data-bs-target="#RelaySchedulerModal"><span class="relay-text"> {{ relay.device_name }}</span></a>
                    </template>
                    </div>
                </div>
            </div>

            <div class="col-md-6 p-3">
                <div v-bind:key="relay.id" v-for="relay in relays.slice(3, number_of_relays)" class="relay">
                    <div class="canvas-wrapper group">
                    <label class="switch">
                    <label>
                      <input v-model="relay.status" v-on:change="relayControl(relay.id, !relay.status)" id="{{ relay.id }}"
                                  type="checkbox" :checked="relay.status == true" :disabled="relay.controlled_by_scheduler">          
                      <div v-if="relay.controlled_by_scheduler" class="slider slider-inner-button" data-toggle="tooltip" title="Controlled by scheduler" data-delay='{"show":"0", "hide":"0"}'></div>
                      <div v-else class="slider slider-inner-button"></div>
                    </label>
                    </label>
                    <template v-if="relay.controlled_by_scheduler">
                      <a href="#" @click="setSelectedRelayId(relay.id)" data-bs-toggle="modal" data-bs-target="#RelaySchedulerModal"><b class="relay-text" style="color: green"> {{ relay.device_name }}</b></a>

                    </template>
                    <template v-else>
                      <a href="#" @click="setSelectedRelayId(relay.id)" data-bs-toggle="modal" data-bs-target="#RelaySchedulerModal"><span class="relay-text"> {{ relay.device_name }}</span></a>

                    </template>
                    </div>
                </div>
            </div>
        </div>

        </div>
    <div
      class="modal fade"
      id="RelaySchedulerModal"
      tabindex="-1"
      aria-labelledby="RelaySchedulerLable"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="RelaySchedulerModalLable"> Relay control with schecule </h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" ></button>
          </div>
          <div class="modal-body">
            <template v-if="controlled_by_scheduler_list.includes(id_of_selected_relay)">
              <RemoveSchedule :relay_id="id_of_selected_relay" v-on:delete-schedule="delete_schedule" />
            </template>
            <template v-else>

              <div class="btn-group btn-group-toggle">
                <input type="radio" class="btn-check" id="daily-scheduler" value="daily" name="scheduler" v-model="picked"> 
                <label class="btn btn-outline-primary shadow-none" for="daily-scheduler">Daily</label>

                <input type="radio" class="btn-check" id="manual-scheduler" value="manual" name="scheduler" v-model="picked"> 
                <label class="btn btn-outline-primary shadow-none" for="manual-scheduler">Manual</label>
              </div>
              <DailySchedule v-if="picked == 'daily'" :relay_id=id_of_selected_relay v-on:add-schedule="add_schedule" />
              <ManualSchedule v-if="picked == 'manual'" :relay_id=id_of_selected_relay v-on:add-schedule="add_schedule" />
            </template>

          </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
  </SmallBoxWidget>
</template>

<script>
import SmallBoxWidget from '../BaseWidgets/SmallBoxWidget.vue';
import DailySchedule from "../Modals/SchedulerComponents/DailySchedule";
import ManualSchedule from "../Modals/SchedulerComponents/ManualSchedule";
import RemoveSchedule from "../Modals/SchedulerComponents/RemoveSchedule";

export default {
    name: 'RelayControlWidget',
    props: {
        relays: {
            device_name: String, 
            group_id: Number, 
            id: Number, 
            ip_address: String,
            status: Boolean,
        },
        group_name: String,
    },
    components: {
        SmallBoxWidget,
        DailySchedule,
        ManualSchedule,
        RemoveSchedule
    },
    data() {
        return {
          number_of_relays: 0,
          picked: 'daily',
          id_of_selected_relay: 0,
          controlled_by_scheduler_list: []
        }
    },
    created() {
        this.number_of_relays = Object.keys(this.relays).length;
        this.relays.forEach((element) => {
          if(element.controlled_by_scheduler) {
            this.controlled_by_scheduler_list.push(element.id)
          }
        })
    },
    methods: {
      relayControl(relay_id, activate) {
          if(activate == true){
            this.sendGETRequest('/relay/' + relay_id + '/on')
          } else {
            this.sendGETRequest('/relay/' + relay_id + '/off')
          }
        },
      setSelectedRelayId(relay_id) {
        this.id_of_selected_relay = relay_id
        console.log(this.id_of_selected_relay)
      },
      save_changes() {
        if(this.picked == "daily") {
          console.log()
          // code for daily
        } else if (this.picked == "manual") {
          console.log()
        }
      },
      add_schedule() {
        this.relays.forEach((element) => {
          if(element.id == this.id_of_selected_relay) {
            element.controlled_by_scheduler = true
          }
        })
        this.controlled_by_scheduler_list.push(this.id_of_selected_relay)
        this.$forceUpdate()
      },
      delete_schedule() {
        this.relays.forEach((element) => {
          if(element.id == this.id_of_selected_relay) {
            element.controlled_by_scheduler = false
          }
        })
        const index = this.controlled_by_scheduler_list.indexOf(this.id_of_selected_relay);
        if (index > -1) {
          this.controlled_by_scheduler_list.splice(index, 1);
        }
        this.$forceUpdate()
      }
    }
}

</script>

<style scoped> .relay-control {
  margin-bottom: 2px;
  margin-top: 2px;
}

a {
  outline: none;
  }

a:link {
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

.relay {
  margin-bottom: 3px;
}

.relay-text {
  padding-left: 17px;
  color: black;
}

#relay-widget-title h5 {
  padding-top: 8px;
  font-weight: bold;
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
  border-radius: 30px;
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
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:checked + .slider-inner-button:before{
  transform: translateX(26px);
}
.btn-group {
  display: flex;
}
.btn input {
  opacity: 0;
  position: fixed;
  margin-right: 10px;
}
label {
    outline: 0;
    box-shadow: none;
}
.btn-primary:hover {
    color: #0275d8;
    background-color: #f1f3ff;
}
label {
    outline: 0;
    box-shadow: none;
}
</style>