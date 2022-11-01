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
              v-model="schedData.daysOn"
              label="Days Working*"
              required
            ></v-text-field>
            <v-text-field
              v-model="schedData.daysOff"
              label="Days Off*"
              required
            ></v-text-field>
            <v-text-field
              v-model="schedData.startLineOne"
              label="First Line Start Date*"
              placeholder="12/31/2000"
              required
            ></v-text-field>
            <v-text-field
              v-model="schedData.lineNum"
              label="Line Option number*"
              placeholder="1"
              required
            ></v-text-field>
            <v-text-field
              v-model="schedData.daysOnAlt"
              label="Days Working (alternate tour)"
            ></v-text-field>
            <v-text-field
              v-model="schedData.daysOffAlt"
              label="Days Off (alternate tour)"
            ></v-text-field>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue lighten-2" @click="dialog = false">Close</v-btn>
          <v-btn color="blue lighten-2" @click="dialog = false">Save</v-btn>
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
      schedData: {
        daysOn: 0,
        daysOff: 0,
        daysOnAlt: 0,
        daysOffAlt: 0,
        startLineOne: "2000-01-01",
        lineNum: 0,
      },
    };
  },
  created() {
    // console.log(this.$store.state.schedule);
    this.schedData = this.$store.state.schedule;
  },
  methods: {
    saveData() {},
  },
};
</script>
<style></style>
