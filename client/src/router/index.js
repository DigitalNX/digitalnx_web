import { createRouter, createWebHistory } from 'vue-router'
import store from '../store/store'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
    props: {
      title: "DigitalNX Web Interface",
      version: "0.1",
      content: String.raw`DigitalNX is an open-source IoT project containing device code, back-end/front-end and all the necessary code
                          to run a basic IoT setup with corresponding guides. The design goal of this project was to build a platform with simple design while being easily expandable for new devices and widgets.
                          <br> For more info and documentation visit: <a href="http://www.github.com/DigitalNX" style="font-family:'Courier New';"> http://www.github.com/DigitalNX </a>`
    },
    meta: { requiresAuth: true }
  },
  {
    path: '/relays',
    name: 'Relays',
    component: () => import('../views/Relays.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/sensors',
    name: 'Sensors',
    component: () => import('../views/Sensors.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/notes',
    name: 'Notes',
    component: () => import('../views/Notes.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/settings',
    name: 'Settings',
    component: () => import('../views/Settings.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/new_note',
    name: 'NewNote',
    component: () => import('../views/notes/NewNote.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/view_note',
    name: 'ViewNote',
    component: () => import('../views/notes/ViewNote.vue'),
    props: true,
    meta: { requiresAuth: true }
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/Login.vue'),
    meta: { requiresVisitors: true }
  },
  {
    path: '/logout',
    name: 'Logout',
    component: () => import('../views/auth/Logout.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (store.state.token == null) {
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    } else {
      next()
    }
  } else {
    next() 
  }
})

export default router
