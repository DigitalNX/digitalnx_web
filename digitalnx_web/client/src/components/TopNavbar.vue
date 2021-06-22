<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-white">
        <div class="logo">
          <img class="img-fluid" src="@/assets/logo/logo_square.png" width="60" height="60" />
          <h5>DigitalNX IoT Project</h5>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav ms-auto">
                <li class="nav-item dropdown">
                    <div class="nav-dropdown">
                    <button class="btn btn-link dropdown-toggle drop-down-button" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {{ capitalize(username) }} &zwnj;
                      </button>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                          <router-link to="/logout">
                          <li><a class="dropdown-item drop-down-button" href="">Logout</a></li>
                          </router-link>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script>
export default {
  name: "TopNavbar",
  data() {
      return {
          username: this.$store.state.username
      }
  },
  methods: {
    capitalize(str) {
      if (typeof str === 'string') { 
        return str.charAt(0).toUpperCase() + str.slice(1)
      }
    },
    sign_out() {
      this.$store.dispatch('clearToken')
    }
  }
};
</script>

<style scoped>
img {
  display: inline;
  margin-left: 14px;
  margin-right: 10px;
}

.logo {
  margin-top: 9px;
  margin-left: 2px;
}

h5 {
  display: inline;
  padding-left: 5px;
}

.drop-down-button {
  margin-right: 28px;
  text-decoration: none;
  border: none;
  color: grey;
}
</style>