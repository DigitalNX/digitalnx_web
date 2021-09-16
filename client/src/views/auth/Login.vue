<template>
  <div class="login-page text-center">
    <img class="img-fluid" src="@/assets/logo/logo.png" />
    <div class="login-form">
      <form class="form-signin" action="#" @submit.prevent="login">
        <div class="d-grid gap-1">
          <h4 class="mb-3 font-weight-normal">Please Login</h4>
          <input
            v-model="username"
            type="username"
            class="form-control"
            placeholder="Username"
            required
            autofocus
          />
          <input
            v-model="password"
            type="password"
            id="inputPassword"
            class="form-control"
            placeholder="Password"
            required
          />

          <button id="submit-button" class="btn btn-primary">
            Sign in
          </button>
        </div>

        <div v-if="invalidCredintials" class="error-message">
          <p>* Invalid credentials! Try again please.</p>
        </div>
        <div v-else-if="loginSuccess" class="success-message">
          <p>* Success! redirecting...</p>
        </div>
        <div v-else class="invisible-text">
          <p>&zwnj;</p>
        </div>
      </form>
    </div>
    <div class="footer-message">
      <p class="mt-5 mb-3 text-muted">&copy; DigitalNX Web Interface</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      username: "",
      password: "",
      invalidCredintials: false,
      loginSuccess: false,
    };
  },
  methods: {
    async login() {

      this.$store
        .dispatch("getToken", {
          username: this.username,
          password: this.password,
        }).then(response => {
        if (response['name']) {
            this.authenticated = true;
        } else {
            this.authenticated = false;
        }
    }).then(async () => {
           this.invalidCredintials = false;
           this.loginSuccess = true;
           await this.sleep(2000);
          this.$router.push({ name: "Home" });
         })
        .catch(() => {
          this.invalidCredintials = true;
         });
    },
    sleep(ms) {
      return new Promise((resolve) => setTimeout(resolve, ms));
    },
  },
};
</script>

<style scoped>
.login-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 110px;
  margin-bottom: 50px;
}

input {
  margin-bottom: 8px;
}

.form-signin {
  width: 300px;
  margin-bottom: 195px;
}

img {
  padding-top: 35px;
  padding-bottom: 60px;
}

#submit-button {
  margin-top: 10px;
  padding: 10px;
}

.form-signin h4 {
  padding-bottom: 1px;
}

.form-signin .form-control {
  height: auto;
  padding: 10px;
  font-size: 16px;
}

.form-signin .form-control:focus {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: 5px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

.success-message {
  color: green;
  padding-top: 25px;
}

.error-message {
  color: red;
  padding-top: 25px;
}

.invisible-text {
  padding-top: 25px;
}
</style>
