<template>
    <router-view/>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
/* General CSS Styles. 
   These styles have been intentionally added here (not their corresponding component/view),
   because they represent the general styling of the whole web app;
   */ 
@import url('https://fonts.googleapis.com/css2?family=Open+Sans&display=swap');
.wrapper {
    display: flex;
    color: #2b2b2b;
    font-family: 'Open Sans', sans-serif;
}

@media (min-device-width: 500px) and (max-width: 750px) {
    .wrapper {
        display: block;
        margin-left: 0px; 
    }
}

.page-title {
    padding-bottom: 20px;
}

#sidebar {
    margin-right: 1px;
}

.container-fluid {
    padding: 30px;
}

.page-title {
  margin-left: 8px;
  margin-top: 3px;
}

.page-title h3 {
  display: inline;
}

.row {
    padding: 6px;
}

li a {
    color: #4e4e4e;
}

.card {
  border-radius: 13px;
  box-shadow: 1px 0px 5px 0px #cfcfcf;
  display: flex;
  margin-bottom: 15px;
}
</style>