import { createStore } from 'vuex'
import axios from 'axios';

export default createStore({
  state: {
    token: localStorage.getItem('token') || null,
    username: localStorage.getItem('username') || null,
    api_server: 'http://127.0.0.1:5000'
  },
  mutations: {
    getToken(state, token) {
      state.token = token;
    },
    clearToken(state) {
      state.token = null;
    },
    getUsername(state, username) {
      state.username = username;
    }
  },
  actions: {
    clearToken(context) {
        localStorage.removeItem('token')
        context.commit('clearToken')
    },
    getToken(context, credentials) {
      return new Promise((resolve, reject) => {
        axios.post(context.state.api_server + '/auth/login', {
          username: credentials.username,
          password: credentials.password
        }).then(res => {
            const token = res.data.token
            localStorage.setItem('username', credentials.username)
            localStorage.setItem('token', token)
            context.commit('getToken', token)
            context.commit('getUsername', credentials.username)
            resolve(res)
        }).catch(err => {
            reject(err)
        })
      })
    }
  }
})