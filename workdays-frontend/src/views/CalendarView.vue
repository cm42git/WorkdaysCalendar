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
    </v-sheet>
    <!-- <v-btn @click="updateEvents">Click</v-btn> -->
    <!-- <h2>{{new Date(this.displayStart).toISOString()}}</h2>
    <h2 v-if="$refs.calendar">{{new Date(this.$refs.calendar.start).toISOString()}}</h2> -->
  </div>
</template>

<script>
Date.prototype.addDays = function (days) {
  var date = new Date(this.valueOf());
  date.setDate(date.getDate() + days);
  return date;
};
import ScheduleService from "@/service/ScheduleService";
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
    // this.setScheduleData(7, 6, 5, 4, new Date(2022, 9, 20), 1);
    // if (this.$store.state.schedule) {
    //   this.schedData = this.$store.state.schedule;
    //   this.updateEvents;
    // }
    if (!(this.$store.state.schedule.daysOn > 0)) {
      this.dialogVisable = true;
      console.log("No schedule data. Please update.");
      alert("No Schedule Data! Please update!")
    } else {
      this.schedData = this.$store.state.schedule;
      console.log("Loaded store schedule data.");
    }
  },
  computed: {
    /*  daysTotal() {
      return (
        this.schedData.daysOn +
        this.schedData.daysOff +
        this.schedData.daysOnAlt +
        this.schedData.daysOffAlt
      );
    },
    daysBack() {
      return this.schedData.daysOnAlt + this.schedData.daysOffAlt;
    }, */
  },
  methods: {
    click() {
      // const min = new Date(new Date(`${start.date}`).getMilliseconds() -(7*24*60*60*1000));
      // const max = new Date(new Date(`${end.date}`) + (7 * 24 * 60 * 60 * 1000));
      // console.log(this.$refs.calendar);
      // console.log(this.$refs.calendar);
      // console.log(max);
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
    /* updateRange({ start, end }) {
      const events = [];

      const min = new Date(new Date(`${start.date}`));
      const max = new Date(new Date(`${end.date}`));
      const days = (max.getTime() - min.getTime()) / 86400000;
      const eventCount = this.rnd(days, days + 20);

      for (let i = 0; i < eventCount; i++) {
        const allDay = this.rnd(0, 3) === 0;
        const firstTimestamp = this.rnd(min.getTime(), max.getTime());
        const first = new Date(firstTimestamp - (firstTimestamp % 900000));
        const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000;
        const second = new Date(first.getTime() + secondTimestamp);

        events.push({
          // name: this.names[this.rnd(0, this.names.length - 1)],
          start: first,
          end: second,
          // color: this.colors[this.rnd(0, this.colors.length - 1)],
          timed: false,
        });
      }

      this.events = events;
    }, */
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },

    /*
    public boolean isWorkingDay(int lineNumber, LocalDate dateToCheck) {
        boolean working = false;
        long daysTotal = daysOn + daysOff + daysOnAlt + daysOffAlt;
        long daysBack = daysOnAlt + daysOffAlt;
        long dateCheckNum = startLineOne.until(dateToCheck, ChronoUnit.DAYS);

        if (Math.floorMod((dateCheckNum + lineNumber - 1), daysTotal) < daysOn) {
            working = true;
        }
        if ((Math.floorMod((dateCheckNum + lineNumber - 1 + daysBack), daysTotal) < daysOnAlt)) {
            working = true;
        }

        return working;
    }
    */
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
    /*  getWorkdayNum(line, date) {
      return (date - this.schedData.startLineOne + line - 1) % this.daysTotal;
    },
    getStartDate(line, date) {
      const n = date;
      const startOfMonth = new Date(n.getFullYear(), n.getMonth(), 1);
      return startOfMonth.addDays(
        -this.getWorkdayNum(line, startOfMonth) - this.daysTotal
      );
    }, */
    updateCalendar({ start }) {
      const numMonths = 2;
      const begin = new Date(start.date).toISOString().slice(0, 10);
      // console.log("beginmonth passed: "+begin);
      this.displayStart = new Date(start.date);
      this.$store.state.displayMonth=new Date(start.date).toISOString().substring(0, 7) + "-01";
      // console.log("updatevents " + begin);
      // console.log(this.$store.state.schedule.daysOn);
      // console.log(this.$refs.calendar.start);
      // this.pullEvents(numMonths, begin);
      this.$store.dispatch("pullEvents", {numMonths: numMonths, begin: begin});
    },
    updateLine() {
      this.$store.commit("SET_LINE", this.schedData.lineNum);
      this.$store.state.displayMonth = new Date(this.displayStart).toISOString().substring(0, 7) + "-01";
      /* this.pullEvents(
        2,
        new Date(this.displayStart).toISOString().substring(0, 7) + "-01"
      ); */
      this.$store.dispatch("pullEvents",{numMonths: 2, begin: new Date(this.displayStart).toISOString().substring(0, 7) + "-01"})
    },
    pullEvents(numMonths, begin) {
      const events = [];
      if (this.$store.state.schedule.daysOn > 0) {
        console.log("Updating events from backend... ");
        ScheduleService.getStartEnd(
          this.$store.state.schedule.daysOn,
          this.$store.state.schedule.daysOff,
          this.$store.state.schedule.daysOnAlt,
          this.$store.state.schedule.daysOffAlt,
          this.$store.state.schedule.startLineOne,
          begin,
          numMonths,
          this.$store.state.schedule.lineNum
        ).then((response) => {
          response.data.forEach((e) => {
            events.push({
              name: "Work",
              start: e.start,
              end: e.end,
              timed: false,
            });
          });
        });
        this.events = events;
      } else {
        console.log("Unable to update events, store is empty?");
      }
    },
    /*  setup({ start }) {
      const numMonths = 2;
      const n = new Date(`${start.date}T00:00:00`);
      const events = [];

      ScheduleService.getStartEnd(
        this.schedData.daysOn,
        this.schedData.daysOff,
        this.schedData.daysOnAlt,
        this.schedData.daysOffAlt,
        this.schedData.startLineOne.toISOString().substr(0, 10),
        n.toISOString().substr(0, 10),
        numMonths,
        this.schedData.lineNum
      ).then((response) => {
        response.data.forEach((e) => {
          events.push({
            name: "Work",
            start: e.start,
            end: e.end,
            timed: false,
          });
        });
      });
      this.events = events;
    }, */
    /*  jsCalcs() {
      const daysBeforeFirst =
        (new Date(n.getFullYear(), n.getMonth(), 1) -
          this.getStartDate(line - 1, n)) /
        (24 * 60 * 60 * 1000);

      const events = [];
      const firstStart = this.getStartDate(line, n);

      for (let i = 0; i <= daysBeforeFirst + 75; i += this.daysTotal) {
        const oneStart = firstStart.addDays(i);
        const oneEnd = firstStart.addDays(i + this.schedData.daysOn - 1);

        events.push({
          name: "Work",
          start: oneStart,
          end: oneEnd,
          timed: false,
        });

        if (this.schedData.daysOnAlt > 0) {
          const twoStart = firstStart.addDays(
            i + this.schedData.daysOn + this.schedData.daysOff
          );
          const twoEnd = firstStart.addDays(
            i +
              this.schedData.daysOn +
              this.schedData.daysOff +
              this.schedData.daysOnAlt -
              1
          );
          events.push({
            name: "Work",
            start: twoStart,
            end: twoEnd,
            timed: false,
          });
        }
      }
      this.events = events; 
    }*/
  },
};
</script>
<style>
/* #calendar {
  display: flex;
    flex-direction: column;
} */
</style>
