import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

// const schedData = localStorage.getItem("schedData");
import ScheduleService from "@/service/ScheduleService";
export default new Vuex.Store({
  state: {
    // schedule: schedule || {},
    schedule: /* schedData || */ {
      daysOn: Number,
      daysOff: Number,
      daysOnAlt: Number,
      daysOffAlt: Number,
      startLineOne: Date,
      lineNum: Number,
    },
    events: [],
    displayMonth: Date,
  },
  getters: {},
  mutations: {
    SET_SCHEDULE(state, sched) {
      state.schedule = sched;
      // localStorage.setItem("schedData", JSON.stringify(state.schedule));
    },
    SET_LINE(state, line) {
      state.schedule.lineNum = line;
      // localStorage.setItem("schedData", JSON.stringify(state.schedule));
    },
    SET_DISPLAY_MONTH(state, month) {
      state.displayMonth = month;
    },
    SET_EVENTS(state, events) {
      state.events = events;
    },
    SET_HOLIDAYS(state, holidays) {
      state.holidays = holidays;
    },
    APPEND_HOLIDAYS(state) {
      state.events.push(state.holidays);
    },
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
          this.commit("SET_EVENTS", events);
        });
        console.log("Events updated.");
      }
    },
    pullHolidays(month) {
      const hol = [];
      console.log("Getting holidays for " + month + "...");
      ScheduleService.getHolidays(month, 1, 1).then((response) => {
        response.data.forEach((h) => {
          hol.push({
            name: h.holidayName,
            start: h.date,
            end: h.date,
            timed: false,
            color: "green",
          });
        });
      });
      this.commit("SET_HOLIDAYS", hol);
      this.commit("APPEND_HOLIDAYS");
    },
  },
  modules: {},
  strict: true,
});
