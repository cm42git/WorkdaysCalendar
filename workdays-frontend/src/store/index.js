import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const schedule = localStorage.getItem("schedData");

export default new Vuex.Store({
  state: {
    schedule: schedule || {},
  },
  getters: {},
  mutations: {
    SET_SCHEDULE(state, sched) {
      state.schedule = sched;
      localStorage.setItem("schedData", JSON.stringify(sched));
    },
  },
  actions: {},
  modules: {},
});
