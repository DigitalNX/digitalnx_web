<template>
    <div class="remove-scheduler"> 
        <p> Remove the schedule? </p>
        <button
        type="button"
        v-on:click="remove()"
        class="btn btn-outline-danger btn-sm ">
        Remove
        </button>

        <template v-if="success">
            <div class="alert alert-success" role="alert">
            <strong>Success!</strong> <p v-if="response_message"> {{ response_message }} </p>
            </div>
        </template>
    </div>
</template>

<script>

export default {
    name: 'RemoveSchedule',
    props: {
        relay_id: Number
    },
    created() {
        this.response_message = ''
        this.success = false
    },
    methods: {
        data() {
            return {
                success: Boolean,
                response_message: String
            }
        },
        async remove() {
            this.sendGETRequest('/schedules/relay/' + this.relay_id + '/remove').then( (data) => {

                if(data['result'] == 200) {
                    this.response_message = data['message']
                    this.success = true
                    this.$emit('delete-schedule')
                } else {
                    this.response_message = data
                }
                }
            )
            console.log("b")
        }
    }
}
</script>

<style scoped>
.remove-scheduler * {
    margin-left: 10px;
    display: inline;
}
.remove-scheduler button {
    float: right;
    margin-right: 15px;
}
</style>