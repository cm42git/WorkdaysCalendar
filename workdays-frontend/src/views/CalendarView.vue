"use strict"

<template>
  <div id="calendar">
    <v-toolbar flat dense>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
      <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
        Today
      </v-btn>
      <v-btn
        outlined
        class="mr-4"
        color="grey darken-2"
        @click="setMonthToggle"
      >
        Go To
      </v-btn>
      <v-btn fab text small color="grey darken-2" @click="prev">
        <v-icon small> mdi-chevron-left </v-icon>
      </v-btn>
      <v-toolbar-title v-if="$refs.calendar" color="grey darken-2">
        {{ $refs.calendar.title }}
      </v-toolbar-title>
      <v-toolbar-title v-else>
        {{
          new Date(this.displayStart).toLocaleDateString("default", {
            month: "long",
            year: "numeric",
          })
        }}
      </v-toolbar-title>
      <v-btn fab text small color="grey darken-2" @click="next">
        <v-icon small> mdi-chevron-right </v-icon>
      </v-btn>

      <v-spacer></v-spacer>
      <SchedInput class="mr-4"></SchedInput>

      <v-card outlined class="px-2" width="100">
        <v-text-field
          v-model="schedData.lineNum"
          hide-details
          single-line
          type="number"
          prefix="Line"
          @input="updateLine"
        ></v-text-field>
      </v-card>
      <AddEvent class="ml-4"></AddEvent>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-sheet height="600">
      <v-calendar
        ref="calendar"
        v-model="value"
        :events="this.$store.state.events"
        @change="updateCalendar"
        @click:event="showEvent"
      ></v-calendar>
      <v-menu
        v-model="selectedOpen"
        :close-on-content-click="false"
        :activator="selectedElement"
        max-width="350px"
        offset-y
      >
        <v-card color="grey lighten-4" min-width="150px" flat>
          <v-toolbar :color="selectedEvent.color" dark>
            <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <span v-html="selectedEvent.details"></span>
          </v-card-text>
          <v-card-actions>
            <v-btn text color="secondary" @click="selectedOpen = false">
              Close
            </v-btn>
            <v-btn text color="secondary" @click="deleteEvent"> Delete </v-btn>
          </v-card-actions>
        </v-card>
      </v-menu>
      <p></p>

      <v-dialog max-width="290" v-model="showMonthPick">
        <v-card>
          <v-date-picker type="month" v-model="monthPick" dense>
          </v-date-picker>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue lighten-2" @click="setMonth">OK</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-sheet>
  </div>
</template>

<script>
Date.prototype.addDays = function (days) {
  var date = new Date(this.valueOf());
  date.setDate(date.getDate() + days);
  return date;
};
import SchedInput from "./SchedInput.vue";
import AddEvent from "./AddEvent.vue";
export default {
  name: "calendar-view",
  components: {
    SchedInput,
    AddEvent,
  },
  data() {
    return {
      value: "",
      events: [],
      schedData: {
        daysOn: Number,
        daysOff: Number,
        daysOnAlt: Number,
        daysOffAlt: Number,
        startLineOne: Date,
        lineNum: Number,
      },
      displayStart: Date.now(),
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
      showMonthPick: false,
      monthPick: "",
    };
  },
  created() {
    this.displayStart = Date.now();
    this.$store.commit(
      "SET_DISPLAY_MONTH",
      new Date(this.displayStart).toISOString().substring(0, 7) + "-01"
    );

    if (!(this.$store.state.schedule.daysOn > 0)) {
      this.schedData.lineNum = 1;
      console.log("No schedule data. Please update.");
      alert("No Schedule Data! Please update!");
    } else {
      this.schedData = { ...this.$store.state.schedule };
      console.log("Loaded store schedule data.");
    }
  },
  methods: {
    setMonthToggle() {
      this.showMonthPick = !this.showMonthPick;
    },
    setMonth() {
      this.value = this.monthPick + "-01";
      this.showMonthPick = !this.showMonthPick;
    },
    setToday() {
      this.value = "";
      this.displayStart = Date.now();
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    setScheduleData(on, off, altOn, altOff, startOne, line) {
      this.schedData.daysOn = on;
      this.schedData.daysOff = off;
      this.schedData.daysOnAlt = altOn;
      this.schedData.daysOffAlt = altOff;
      this.schedData.startLineOne = startOne;
      this.schedData.lineNum = line;
      this.$store.commit("SET_SCHEDULE", this.schedData);
    },
    setLineNum(line) {
      this.schedData.lineNum = line;
      this.$store.commit("SET_SCHEDULE", this.schedData);
      this.updateEvents();
    },

    updateCalendar({ start }) {
      const numMonths = 2;
      const begin = new Date(start.date).toISOString().slice(0, 10);
      this.displayStart = new Date(start.date);
      this.$store.commit(
        "SET_DISPLAY_MONTH",
        new Date(start.date).toISOString().substring(0, 7) + "-01"
      );

      this.$store.dispatch("pullHolidays");
      this.$store.dispatch("pullEvents", {
        numMonths: numMonths,
        begin: begin,
      });
    },
    updateLine() {
      this.$store.commit("SET_LINE", this.schedData.lineNum);
      this.$store.commit(
        "SET_DISPLAY_MONTH",
        new Date(this.displayStart).toISOString().substring(0, 7) + "-01"
      );

      this.$store.dispatch("pullEvents", {
        numMonths: 2,
        begin:
          new Date(this.displayStart).toISOString().substring(0, 7) + "-01",
      });
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        requestAnimationFrame(() =>
          requestAnimationFrame(() => (this.selectedOpen = true))
        );
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        requestAnimationFrame(() => requestAnimationFrame(() => open()));
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    deleteEvent() {
      this.$store.commit("DELETE_EVENT", this.selectedEvent);
      this.selectedOpen = false;
    },
  },
};
</script>
<style></style>
