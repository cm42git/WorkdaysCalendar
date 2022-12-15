<template>
  <div class="text-center">
    <v-dialog width="400" v-model="show" persistent>
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
              v-model="schedDataLocal.startLineOne"
              label="First Line Start Date*"
              required
              type="date"
            ></v-text-field>
            <v-text-field
              v-model.number="schedDataLocal.daysOnAlt"
              label="Days Working (alternate tour)"
              type="number"
              :rules="[
                () =>
                  schedDataLocal.daysOnAlt >= 0 ||
                  'Must be greater than zero, or zero if N/A',
              ]"
            ></v-text-field>
            <v-text-field
              v-model.number="schedDataLocal.daysOffAlt"
              label="Days Off (alternate tour)"
              type="number"
              :rules="[
                () =>
                  schedDataLocal.daysOffAlt >= 0 ||
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
  name: "sched-input",
  props: { value: Boolean },
  data() {
    return {
      dialog: false,
      dateInput: Date,
      schedDataLocal: {
        daysOn: Number,
        daysOff: Number,
        daysOnAlt: Number,
        daysOffAlt: Number,
        startLineOne: Date,
        lineNum: Number,
      },
    };
  },
  created() {
    if (this.$store.state.schedule.daysOn > 0) {
      this.schedDataLocal = Object.assign({}, this.$store.state.schedule);
    } else {
      this.schedDataLocal.daysOnAlt = 0;
      this.schedDataLocal.daysOffAlt = 0;
      this.schedDataLocal.lineNum = 1;
    }
  },
  computed: {
    show: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit("input", value);
      },
    },
  },
  methods: {
    closeOnly() {
      this.schedDataLocal = Object.assign({}, this.$store.state.schedule);
      this.show = false;
    },
    saveData() {
      const temp = Object.assign({}, this.schedDataLocal);
      if (this.$store.state.schedule.daysOn > 0) {
        temp.lineNum = this.$store.state.schedule.lineNum;
      }
      this.$store.commit("SET_SCHEDULE", temp);
      this.$store.dispatch("pullEvents", { numMonths: 2 });
      this.show = false;
    },
  },
};
</script>
<style></style>
