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
    APPEND_HOLIDAYS(state, holidays) {
      holidays.forEach((h) => state.events.push(h));
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
              color: "blue",
              timed: false,
            });
          });
          this.commit("SET_EVENTS", events);
        });
        console.log("Events updated.");
        this.dispatch("pullHolidays");
      }
    },
    pullHolidays({ state }) {
      const hol = [];
      console.log("Getting holidays for " + state.displayMonth + "...");
      ScheduleService.getHolidays(state.displayMonth, 1, 1).then((response) => {
        response.data.forEach((h) => {
          hol.push({
            name: h.holidayName,
            start: h.date,
            end: h.date,
            color: "green",
            timed: false,
          });
        });
        // this.commit("SET_HOLIDAYS", hol);
        this.commit("APPEND_HOLIDAYS", hol);
        console.log("Holidays set and appended.");
      });

    },
  },
  modules: {},
  strict: true,
});
