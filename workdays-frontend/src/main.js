import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router";
import store from "./store";

Vue.config.productionTip = false;

new Vue({
  vuetify,
  router,
  store,
  beforeCreate() {
    this.$store.commit('INITIALIZE_STORE')
  },
  created: function () {
    // Subscribe to store updates
    store.subscribe((mutation, state) => {
      // Store the state object as a JSON string
      localStorage.setItem("store", JSON.stringify(state));
    });
  },
  render: (h) => h(App),
}).$mount("#app");
