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
        <v-container>
          <v-text-field
            v-model="schedData.daysOn"
            label="Days Working"
            required
          ></v-text-field>
          <v-text-field
            v-model="schedData.daysOff"
            label="Days Off"
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
          <v-text-field
            v-model="schedData.startLineOne"
            label="First Line Start Date"
            placeholder="12/31/2000"
            required
          ></v-text-field>
        </v-container>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue lighten-2" @click="dialog = false">Submit</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dialog: false,
      schedData: {
        daysOn: 0,
        daysOff: 0,
        daysOnAlt: 0,
        daysOffAlt: 0,
        startLineOne: "2000-01-01",
      },
    };
  },
};
</script>
<style></style>
