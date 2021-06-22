<template>
    <div id="sidebar">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#sidebar-items" aria-controls="#sidebar-items" aria-expanded="true"> 
            <span class="navbar-toggler-icon fas fa-bars"></span>
        </button>
        <nav id="sidebar-items" class="collapse">
            <ul class="list-unstyled">
                <li v-for="item in navItems" :key="item.index">
                    <router-link :to="{name: item.name }"><i :class="item.class"></i>{{ item.name }} </router-link>
                </li>
            </ul>
        </nav>
    </div>
</template>

<script>
export default {
    name: "Sidebar",
    props: {
        navItems: Array
    },
}
</script>

<style scoped>
#sidebar-items {
    width: 250px;
    padding-top: 15px;
    transition: 0.3s;
}

button {
    margin-left: 5px;
}

a {
    font-weight: 1.4rem;
    padding: 12px 23px 12px 23px;
    display: block;
    text-decoration: none;
}

a .svg-inline--fa {
    width: 24px;
    margin-right: 5px;
}


a:hover, a.active {
    color: #1578ca;
    background: #eff1f3;
}

@media (min-device-width: 500px) {
    .navbar-toggler {
        display: none !important;
    }
    .collapse {
        display: block;
    }
}

@media (min-device-width: 500px) and (max-width: 750px) {
    #sidebar {
        margin-left: -250px;
    }
}
</style>