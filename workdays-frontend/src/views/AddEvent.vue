<template>
  <div class="text-right">
    <v-dialog width="300" v-model="addDialog">
      <template v-slot:activator="{ on, attrs }">
        <v-btn>
          <v-icon v-bind="attrs" v-on="on">mdi-plus</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-container>
          <v-text-field
            v-model="event[0].name"
            label="Event Name"
            required
          ></v-text-field>
          <v-text-field
            v-model="event[0].start"
            label="Start Date"
            required
            type="date"
          ></v-text-field
          ><v-text-field
            v-model="event[0].end"
            label="End Date"
            required
            type="date"
          ></v-text-field>
        </v-container>
      </v-card>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue lighten-2" @click="closeOnly">Cancel</v-btn>
        <v-btn color="blue lighten-2" @click="saveData">Save</v-btn>
      </v-card-actions>
    </v-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      addDialog: false,
      event: [
        {
          name: "",
          start: "",
          end: "",
          color: "orange",
          timed: false,
          category: "user",
        },
      ],
    };
  },
  methods: {
    closeOnly() {
      this.addDialog = false;
    },
    saveData() {
      this.$store.commit("APPEND_EVENTS", this.event);
      this.addDialog = false;
    },
  },
};
</script>
<style></style>
