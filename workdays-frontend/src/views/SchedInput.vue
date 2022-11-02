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
              v-model.number="schedDataLocal.daysOn"
              label="Days Working*"
              required
              type="number"
              :rules="[
                () => schedDataLocal.daysOn > 0 || 'Must be greater than zero',
              ]"
            ></v-text-field>
            <v-text-field
              v-model.number="schedDataLocal.daysOff"
              label="Days Off*"
              required
              type="number"
              :rules="[
                () => schedDataLocal.daysOff > 0 || 'Must be greater than zero',
              ]"
            ></v-text-field>
            <v-text-field
              v-model="dateInput"
              label="First Line Start Date*"
              placeholder="12/31/2000"
              required
              type="date"
            ></v-text-field>
            <!-- <v-text-field
              v-model="schedDataLocal.lineNum"
              label="Line Option number*"
              placeholder="1"
              required
              type="number"
              ></v-text-field> -->
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
      daysOnAltInput: 0,
      daysOffAltInput: 0,
      schedDataLocal: {
        daysOn: Number,
        daysOff: Number,
        daysOnAlt: 0,
        daysOffAlt: 0,
        startLineOne: Date,
        lineNum: 1,
      },
    };
  },
  created() {
    if (this.$store.state.schedule) {
      this.schedDataLocal = this.$store.state.schedule;
      this.dateInput = this.schedDataLocal.startLineOne;
    }
  },
  methods: {
    closeOnly() {
      this.schedDataLocal = this.$store.state.schedule;
      this.dialog = false;
    },
    saveData() {
      // console.log(this.dateInput);
      // const year = this.dateInput.getFullYear();
      // const month = this.dateInput.getMonth() - 1;
      // const day = this.dateInput.getDate();
      // this.schedDataLocal.startLineOne = new Date(year, month, day);
      this.schedDataLocal.startLineOne = this.dateInput;
      this.schedDataLocal.daysOnAlt = this.daysOnAltInput;
      this.schedDataLocal.daysOffAlt = this.daysOffAltInput;
      // console.log(this.schedDataLocal.startLineOne);
      this.schedDataLocal.lineNum = 1;
      // console.log(this.schedDataLocal);
      this.$store.commit("SET_SCHEDULE", this.schedDataLocal);
      this.dialog = false;
      // console.log(this.$store.state.schedule);
    },
  },
};
</script>
<style></style>
