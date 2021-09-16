<template>
<div class="manual-schedule">
    <h6>Specify the interval for relay's activation:</h6>
    <div class="time-field">
        <label for="time-interval">Interval (in minutes): </label>
        <input id="time-interval" type="number" min=5 max=2000 v-model="time_interval">
    </div>
    <div class="time-field">
        <label for="time-duration">Duration (in minutes): </label>
        <input id="time-duration" type="number" min=5 max=2000 v-model="time_duration">
        <button @click="set_schedule" type="button" class="btn btn-primary">Set</button>
    </div>
    <div v-if="success" class="alert alert-success" role="alert">
        <strong>Success!</strong> <p> {{ response_message }} </p>
    </div>
</div>
</template>
<script>
// TODO : Add an optional text field for description
export default {
    name: 'ManualSchedule',
    props: {
        relay_id: Number
    },
    data() {
        return {
            time_interval: 60,
            time_duration: 5,
            success: false,
            response_message: ''
        }
    },
    methods: {
        async set_schedule() {
            this.sendPOSTRequest('/schedules/relay/' + this.relay_id + '/add', {
                startingTime: '',
                period: this.time_interval * 60,
                execution_duration: this.time_duration * 60,
                description: '',
                enabled: true
            }).then((data) => {
                if(data['result'] == 200) {
                    this.response_message = data['message']
                    this.success = true
                    this.$emit('add-schedule')
                } else {
                    this.response_message = data
                }
            })
        }
    }
}
</script>

<style scoped>
h6 {
    margin-top: 16px;
    margin-bottom: 10px;
}
.time-field {
    display: flex;
}
.time-field input {
    margin-left: 4px;
    margin-right: 15px;
}
.time-field button {
    position: relative;
    margin-right: 25px;
}
.time-field label {
    margin-top: 8px;
    margin-right: 5px;
    flex-grow: 0;
    flex-shrink: 0;
    flex-basis: 160px;
}
.time-field label {
    margin-top: 8px;
    margin-right: 5px;
    flex-grow: 0;
    flex-shrink: 0;
    flex-basis: 160px;
}
</style>