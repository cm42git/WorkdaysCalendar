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
              :rules="[()=>schedDataLocal.daysOn>0 || 'Must be greater than zero']"
            ></v-text-field>
            <v-text-field
              v-model.number="schedDataLocal.daysOff"
              label="Days Off*"
              required
              type="number"
              :rules="[()=>schedDataLocal.daysOff>0 || 'Must be greater than zero']"
            ></v-text-field>
            <v-text-field
              v-model="schedDataLocal.startLineOne"
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
              v-model.number="schedDataLocal.daysOnAlt"
              label="Days Working (alternate tour)"
              type="number"
              :rules="[()=>schedDataLocal.daysOnAlt>=0 || 'Must be greater than zero, or zero if N/A']"
              ></v-text-field>
              <v-text-field
              v-model.number="schedDataLocal.daysOffAlt"
              label="Days Off (alternate tour)"
              type="number"
              :rules="[()=>schedDataLocal.daysOffAlt>=0 || 'Must be greater than zero, or zero if N/A']"
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
    if(this.$store.state.schedule){
    this.schedDataLocal = this.$store.state.schedule;}
  },
  methods: {
    closeOnly() {
      this.schedDataLocal = this.$store.state.schedule;
      this.dialog = false;
    },
    saveData() {
      this.schedDataLocal.startLineOne=new Date(this.schedDataLocal.startLineOne+"T00:00:00Z")
      this.$store.commit("SET_SCHEDULE", this.schedDataLocal);
      this.dialog = false;
    },
  },
};
</script>
<style></style>
