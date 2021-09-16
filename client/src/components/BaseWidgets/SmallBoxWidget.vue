<template>
    <div class="col-lg-3">
        <div class="card card-body flex-fill">
            <div class="content">
                <slot/>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "SmallBoxWidget"
}
</script>

<style>
.card .col-sm-6 {
    height: 230px;
}
</style>