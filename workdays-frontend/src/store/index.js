import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

// const schedule = localStorage.getItem("schedData");

export default new Vuex.Store({
  state: {
    // schedule: schedule || {},
    schedule: {
      daysOn: Number,
      daysOff: Number,
      daysOnAlt: Number,
      daysOffAlt: Number,
      startLineOne: Date,
      lineNum: Number,
    },
  },
  getters: {},
  mutations: {
    SET_SCHEDULE(state, sched) {
      state.schedule = sched;
      // localStorage.setItem("schedData", JSON.stringify(sched));
    },
    SET_LINE(state, line) {
      state.schedule.lineNum = line;
      // localStorage.setItem("schedData", JSON.stringify(sched));
    }
  },
  actions: {},
  modules: {},
});
