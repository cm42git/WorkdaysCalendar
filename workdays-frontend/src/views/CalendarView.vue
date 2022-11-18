"use strict"

<template>
  <div id="calendar">
    <v-toolbar flat>
      <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
        Today
      </v-btn>
      <v-btn fab text small color="grey darken-2" @click="prev">
        <v-icon small> mdi-chevron-left </v-icon>
      </v-btn>
      <v-btn fab text small color="grey darken-2" @click="next">
        <v-icon small> mdi-chevron-right </v-icon>
      </v-btn>
      <v-toolbar-title v-if="$refs.calendar">
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
      <v-spacer></v-spacer>
      <SchedInput></SchedInput>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="schedData.lineNum"
        class="mt-0 pt-0"
        hide-details
        single-line
        type="number"
        prefix="Line"
        @input="updateLine"
      ></v-text-field>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-sheet height="600">
      <v-calendar
        ref="calendar"
        v-model="value"
        :events="this.$store.state.events"
        @change="updateCalendar"
      ></v-calendar>
      <Calendar />
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
export default {
  name: "calendar-view",
  components: {
    SchedInput,
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
        lineNum: 1,
      },
      displayStart: Date.now(),
    };
  },
  created() {
    this.displayStart = Date.now();
    this.$store.commit(
      "SET_DISPLAY_MONTH",
      new Date(this.displayStart).toISOString().substring(0, 7) + "-01"
    );

    if (!(this.$store.state.schedule.daysOn > 0)) {
      this.dialogVisable = true;
      console.log("No schedule data. Please update.");
      alert("No Schedule Data! Please update!");
    } else {
      this.schedData = this.$store.state.schedule;
      console.log("Loaded store schedule data.");
    }
  },
  methods: {
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
  },
};
</script>
<style></style>
