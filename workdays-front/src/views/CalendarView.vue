<template>
  <div id="calendar">
    <v-btn icon class="ma-2" @click="$refs.calendar.prev()">
      <v-icon>mdi-chevron-left</v-icon>
    </v-btn>
    <h1>Workdays Calendar</h1>
    <v-btn icon class="ma-2" @click="$refs.calendar.next()">
      <v-icon>mdi-chevron-right</v-icon>
    </v-btn>
    <v-sheet height="600">
      <v-calendar
        ref="calendar"
        type="month"
        v-model="value"
        :events="events"
      ></v-calendar>
    </v-sheet>
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
  },
};
</script>
<style>
/* #calendar {
  display: flex;
    flex-direction: column;
} */
</style>
