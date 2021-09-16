<template>
    <div class="col-md-6">
        <div class="card" >
            <div class="card-body">
                <div class="content ">
                    <slot/>    
                </div>
            </div>
        </div>
    </div> 
</template>

<script>
export default {
    name: "MediumBoxWidget",
}
</script>