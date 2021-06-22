<template>
<div class="daily-schedule">
    <h6>Specify the starting time and ending time of the relay's activation:</h6>
    <div class="time-field starting-time">
        <label for="starting-time">Starting time: </label>
        <div class="time-input">
            <input type="number" id="s-h" name="startingHour" maxlength="2" size="3" min="0" max="24" v-model="startingTime[0]">
            :
            <input type="number" id="s-m" name="startingMinute" maxlength="2" size="3" min="0" max="24" v-model="startingTime[1]">
            :
            <input type="number" id="s-s" name="startingSecond" maxlength="2" size="3" min="0" max="24" v-model="startingTime[2]">
        </div>
    </div>
    <div class="time-field ending-time">
        <label for="ending-time">Ending time: </label>
        <div class="time-input">
            <input type="number" id="e-h" name="endingHour" maxlength="2" size="3" min="0" max="24" value="13">
            :
            <input type="number" id="e-m" name="endingMinute" maxlength="2" size="3" min="0" max="24" value="10">
            :
            <input type="number" id="e-s" name="endingSecond" maxlength="2" size="3" min="0" max="24" value="00">
        </div>
        <button @click="set_schedule" type="button" class="btn btn-primary">Set</button>
    </div>
    <div v-if="success" class="alert alert-success" role="alert">
        <strong>Success!</strong> Schedule has been successfully set. 
    </div>
    <div v-else><p>&zwnj;</p></div>

</div>     
</template>
<script>
export default {
    name: 'DailySchedule',
    props: {
        relay_id: Number
    },
    data() {
        return {
            startingTime: ['13', '00', '00'],
            endingTime: ['13', '10', '00'],
            success: false
        }
    },
    methods: {
        convert_time_to_seconds(time) {
            return parseInt(time[0]) * 60 * 60 + 
                   parseInt(time[1]) * 60 + 
                   parseInt(time[2])
        },
        set_schedule() {
            let startingTimeS = this.convert_time_to_seconds(this.startingTime)
            let endingTimeS = this.convert_time_to_seconds(this.endingTime)
            var execution_duration, period
            if(endingTimeS > startingTimeS) {
                execution_duration = endingTimeS - startingTimeS
                period = (24*3600 - endingTimeS) + startingTimeS
            } else {
                execution_duration = (24*3600 - startingTimeS) + endingTimeS
                period = startingTimeS - endingTimeS
            }
            var todaysEpochTime = new Date()
            todaysEpochTime.setHours(0,0,0,0)
            this.sendPOSTRequest('/schedules/relay/' + this.relay_id + '/add', {
                startingTime: todaysEpochTime/1000,
                period: period,
                execution_duration: execution_duration,
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
    margin-bottom: 10px;
    margin-left: 15px;
    margin-right: 15px;
}
.time-field .time-input {
    flex-grow: 1;
    margin-top: 4px;
}
.time-field button {
    position: relative;
    margin-right: 25px;
    margin-bottom: 5px;
}
.starting-time label {
    margin-top: 8px;
    margin-right: 5px;
    flex-grow: 0;
    flex-shrink: 0;
    flex-basis: 100px;
}
.ending-time label {
    margin-top: 8px;
    margin-right: 5px;
    flex-grow: 0;
    flex-shrink: 0;
    flex-basis: 100px;
}

</style>