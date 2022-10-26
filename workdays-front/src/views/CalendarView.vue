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
      </v-toolbar-title></v-toolbar
    >
    <v-sheet height="600">
      <v-calendar ref="calendar" v-model="value" :events="events"></v-calendar>
      <!-- @change="updateRange" -->
    </v-sheet>
    <v-btn @click="setup">Click</v-btn>
  </div>
</template>
<script>
export default {
  name: "calendar-view",
  data() {
    return {
      value: "",
      events: [],
      schedData: {
        daysOn: 0,
        daysOff: 0,
        daysOnAlt: 0,
        daysOffAlt: 0,
        startLineOne: "2000-01-01",
      },
      displayStart: Date.now(),
    };
  },
  computed: {
    daysTotal() {
      return (
        this.schedData.daysOn +
        this.schedData.daysOff +
        this.schedData.daysOnAlt +
        this.schedData.daysOffAlt
      );
    },
    daysBack() {
      return this.schedData.daysOnAlt + this.schedData.daysOffAlt;
    },
  },
  methods: {
    click() {
      // const min = new Date(new Date(`${start.date}`).getMilliseconds() -(7*24*60*60*1000));
      // const max = new Date(new Date(`${end.date}`) + (7 * 24 * 60 * 60 * 1000));
      console.log(this.$refs.calendar);
      console.log(this.$refs.calendar);
      // console.log(max);
    },
    setToday() {
      this.value = "";
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    updateRange({ start, end }) {
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
    },
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
    setScheduleData(on, off, altOn, altOff, startOne) {
      this.schedData.daysOn = on;
      this.schedData.daysOff = off;
      this.schedData.daysOnAlt = altOn;
      this.schedData.daysOffAlt = altOff;
      this.schedData.startLineOne = startOne;
    },
    getWorkdayNum(line, date) {
      return (date - this.schedData.startLineOne + line - 1) % this.daysTotal;
    },
    getStartDate(line) {
      const n = new Date();
      const startOfMonth = new Date(n.getFullYear(), n.getMonth(), 1);
      const lineDayMillis =
        this.getWorkdayNum(line, startOfMonth) * (24 * 60 * 60 * 1000);

      // console.log(this.getWorkdayNum(line, startOfMonth));

      return new Date(startOfMonth - lineDayMillis);
    },
    setup() {
      this.setScheduleData(7, 7, 6, 5, new Date(2022,9,1));
      console.log(this.schedData);
      for (let index = 1; index <= this.daysTotal; index++) {
        
        console.log(index + ": " + this.getStartDate(index));
        
      }
    },
  },
};
</script>
<style>
/* #calendar {
  display: flex;
    flex-direction: column;
} */
</style>
