<template>
  <div class="text-center">
    <v-dialog width="400" v-model="dialog">
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="blue lighten-2" dark v-bind="attrs" v-on="on">
          Schedule Data
        </v-btn>
      </template>
      <v-card>
        <v-card-title>Recurring Work Tours Information</v-card-title>
        <v-card-text>
          <v-container>
            <v-text-field
              v-model.number="daysOnInput"
              label="Days Working*"
              required
              type="number"
              :rules="[
                () => daysOnInput > 0 || 'Must be greater than zero',
              ]"
            ></v-text-field>
            <v-text-field
              v-model.number="daysOffInput"
              label="Days Off*"
              required
              type="number"
              :rules="[
                () => daysOffInput > 0 || 'Must be greater than zero',
              ]"
            ></v-text-field>
            <v-text-field
              v-model="dateInput"
              label="First Line Start Date*"
              required
              type="date"
            ></v-text-field>
            <v-text-field
              v-model.number="daysOnAltInput"
              label="Days Working (alternate tour)"
              type="number"
              :rules="[
                () =>
                  daysOnAltInput >= 0 ||
                  'Must be greater than zero, or zero if N/A',
              ]"
            ></v-text-field>
            <v-text-field
              v-model.number="daysOffAltInput"
              label="Days Off (alternate tour)"
              type="number"
              :rules="[
                () =>
                  daysOffAltInput >= 0 ||
                  'Must be greater than zero, or zero if N/A',
              ]"
            ></v-text-field>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue lighten-2" @click="closeOnly">Close</v-btn>
          <v-btn color="blue lighten-2" @click="saveData">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
export default {
  props: {},
  data() {
    return {
      dialog: false,
      dateInput: Date,
      daysOnInput: 0,
      daysOffInput: 0,
      daysOnAltInput: 0,
      daysOffAltInput: 0,
      schedDataLocal: {
        daysOn: Number,
        daysOff: Number,
        daysOnAlt: Number,
        daysOffAlt: Number,
        startLineOne: Date,
        lineNum: 1,
      },
    };
  },
  created() {
    // if (this.$store.state.schedule) {
      // this.schedDataLocal = this.$store.state.schedule;
      this.dateInput = this.schedDataLocal.startLineOne;
    // }
  },
  methods: {
    closeOnly() {
      // this.schedDataLocal = this.$store.state.schedule;
      this.dialog = false;
    },
    saveData() {
      this.schedDataLocal.daysOn = this.daysOnInput;
      this.schedDataLocal.daysOff = this.daysOffInput;
      this.schedDataLocal.startLineOne = this.dateInput;
      this.schedDataLocal.daysOnAlt = this.daysOnAltInput;
      this.schedDataLocal.daysOffAlt = this.daysOffAltInput;
      this.schedDataLocal.lineNum = 1;
      this.$store.commit("SET_SCHEDULE", this.schedDataLocal);
      this.$store.dispatch("pullEvents", { numMonths: 2 });
      this.dialog = false;
      this.schedDataLocal = {};
    },
  },
};
</script>
<style></style>
