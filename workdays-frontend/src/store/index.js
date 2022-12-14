import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import ScheduleService from "@/service/ScheduleService";
export default new Vuex.Store({
  state: {
    schedule: {
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
    INITIALIZE_STORE(state) {
      if (localStorage.getItem("store")) {
        this.replaceState(
          Object.assign(state, JSON.parse(localStorage.getItem("store")))
        );
      }
    },
    DELETE_STORE() {
      localStorage.removeItem("store");
      this.replaceState({
        schedule: {
          daysOn: Number,
          daysOff: Number,
          daysOnAlt: Number,
          daysOffAlt: Number,
          startLineOne: Date,
          lineNum: Number,
        },
        events: [],
        displayMonth: Date,
      });
    },
    SET_SCHEDULE(state, sched) {
      state.schedule = sched;
    },
    SET_LINE(state, line) {
      state.schedule.lineNum = line;
    },
    SET_DISPLAY_MONTH(state, month) {
      state.displayMonth = month;
    },
    SET_EVENTS(state, events) {
      state.events = events;
    },
    APPEND_EVENTS(state, events) {
      events.forEach((e) => state.events.push(e));
    },
    DELETE_EVENTS(state, category) {
      if (category !== null) {
        state.events = state.events.filter((e) => e.category !== category);
      } else {
        state.events = [];
      }
    },
    DELETE_EVENT(state, event) {
      state.events = state.events.filter((e) => e !== event);
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
              category: "work",
            });
          });
          this.commit("DELETE_EVENTS", "work");
          this.commit("APPEND_EVENTS", events);
          console.log("Events updated.");
        });
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
            color: "green",
            timed: false,
            category: "holiday",
          });
        });
        this.commit("DELETE_EVENTS", "holiday");
        this.commit("APPEND_EVENTS", hol);
        console.log("Holidays set and appended.");
      });
    },
  },
  modules: {},
  strict: true,
});
