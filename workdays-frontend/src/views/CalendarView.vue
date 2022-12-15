<template>
  <div id="calendar">
    <v-toolbar flat dense>
      <v-app-bar-nav-icon v-if="mobile"
        @click.stop="navDrawer = !navDrawer"
      ></v-app-bar-nav-icon>
      <v-btn
        v-if="!mobile"
        outlined
        class="mr-4"
        color="grey darken-2"
        @click="setToday"
      >
        Today
      </v-btn>
      <v-btn
        v-if="!mobile"
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

      <v-btn
        v-if="!mobile"
        class="mr-4"
        color="blue lighten-2"
        dark
        @click="showSchedInput = !showSchedInput"
        >Schedule Input</v-btn
      >

      <v-card v-if="!mobile" outlined class="px-2 mr-4" width="100">
        <v-text-field
          v-model="schedData.lineNum"
          hide-details
          single-line
          type="number"
          prefix="Line"
          @input="updateLine"
        ></v-text-field>
      </v-card>

      <v-btn
        v-if="!mobile"
        outlined
        class="mr-4"
        color="grey darken-2"
        @click="showAddEvent = !showAddEvent"
        ><v-icon>mdi-plus</v-icon></v-btn
      >
      <v-spacer></v-spacer>
    </v-toolbar>

    <SchedInput v-model="showSchedInput"></SchedInput>
    <AddEvent v-model="showAddEvent"></AddEvent>

    <v-navigation-drawer v-model="navDrawer" absolute temporary height="240">
      <v-list nav dense class="text-left">
        <v-list-item-group color="primary">
          <v-list-item @click="setToday">
            <v-list-item-icon
              ><v-icon>mdi-calendar-month</v-icon></v-list-item-icon
            >
            <v-list-item-content>
              <v-list-item-title>Go To Today</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item @click="setMonthToggle">
            <v-list-item-icon
              ><v-icon>mdi-calendar-search</v-icon></v-list-item-icon
            >
            <v-list-item-content>
              <v-list-item-title>Select Month</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item @click="schedInputShow">
            <v-list-item-icon
              ><v-icon>mdi-calendar-question</v-icon></v-list-item-icon
            >
            <v-list-item-content>
              <v-list-item-title>Schedule Data</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item @click="setLineToggle">
            <v-list-item-icon
              ><v-icon>mdi-calendar-star</v-icon></v-list-item-icon
            >
            <v-list-item-content>
              <v-list-item-title>Change Line</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item @click="addEventShow">
            <v-list-item-icon
              ><v-icon>mdi-calendar-plus</v-icon></v-list-item-icon
            >
            <v-list-item-content>
              <v-list-item-title>Add Event</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-sheet height="600">
      <v-calendar
        ref="calendar"
        v-model="value"
        color="primary"
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

      <v-dialog max-width="150" v-model="showLineDialog">
        <v-card>
          <v-text-field
            v-model="schedData.lineNum"
            class="px-3"
            type="number"
            prefix="Line"
            label="Set Line Number:"
            @input="updateLine"
          ></v-text-field>
          <v-card-actions>
            <v-btn color="blue lighten-2" @click="showLineDialog = false">
              OK
            </v-btn>
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
      navDrawer: false,
      showSchedInput: false,
      showAddEvent: false,
      showLineDialog: false,
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
  computed: {
    mobile() {
      return this.$vuetify.breakpoint.smAndDown;
        },
  },
  methods: {
    schedInputShow() {
      this.navDrawer = false;
      this.showSchedInput = !this.showSchedInput;
    },
    addEventShow() {
      this.navDrawer = false;
      this.showAddEvent = !this.showAddEvent;
    },
    setMonthToggle() {
      this.showMonthPick = !this.showMonthPick;
      this.navDrawer = false;
    },
    setLineToggle() {
      this.showLineDialog = !this.showLineDialog;
      this.navDrawer = false;
    },
    setMonth() {
      this.value = this.monthPick + "-01";
      this.showMonthPick = !this.showMonthPick;
    },
    setToday() {
      this.value = "";
      this.displayStart = Date.now();
      this.navDrawer = false;
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
