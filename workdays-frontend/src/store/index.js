import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

// const schedule = localStorage.getItem("schedData");
import ScheduleService from "@/service/ScheduleService";
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
    events: [],
    displayMonth: Date
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
    },
    SET_DISPLAY_MONTH(state, month) {
      state.displayMonth = month;
    }
  },
  actions: {
    pullEvents({ state }, data) {
      const events = [];
      if (state.schedule.daysOn > 0) {
        console.log("Updating events from backend... ");
        ScheduleService.getStartEnd(
          state.schedule.daysOn,
          state.schedule.daysOff,
          state.schedule.daysOnAlt,
          state.schedule.daysOffAlt,
          state.schedule.startLineOne,
          state.displayMonth,
          data.numMonths,
          state.schedule.lineNum
        ).then((response) => {
          response.data.forEach((e) => {
            events.push({
              name: "Work",
              start: e.start,
              end: e.end,
              timed: false,
            });
          });
          state.events = events;
        });
      }
    },
  },
  modules: {},
});
