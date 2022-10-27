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
      <v-calendar
        ref="calendar"
        v-model="value"
        :events="events"
        @change="setup"
      ></v-calendar>
    </v-sheet>
    <!-- <v-btn @click="setup">Click</v-btn> -->
  </div>
</template>

<script>
Date.prototype.addDays = function (days) {
  var date = new Date(this.valueOf());
  date.setDate(date.getDate() + days);
  return date;
};

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
    getStartDate(line, date) {
      const n = date;
      const startOfMonth = new Date(n.getFullYear(), n.getMonth(), 1);
      return startOfMonth.addDays(
        -this.getWorkdayNum(line, startOfMonth) - this.daysTotal
      );
    },
    setup({ start }) {
      this.setScheduleData(5, 2, 0, 0, new Date(2022, 9, 3));
      const line = 1;
      const n = new Date(`${start.date}T00:00:00`);

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
