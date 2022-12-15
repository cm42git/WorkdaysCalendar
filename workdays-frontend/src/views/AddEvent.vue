<template>
  <div class="text-right">
    <v-dialog width="300" v-model="show">
      <v-card>
        <v-card-text>
          <v-container>
            Add User Event
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
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue lighten-2" @click="closeOnly">Cancel</v-btn>
          <v-btn color="blue lighten-2" @click="saveData">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
export default {
  props:{value: Boolean},
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
  computed: {
show: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit('input',value)
      }
    }
  },
  methods: {
    closeOnly() {
      this.show = false;
    },
    saveData() {
      this.$store.commit("APPEND_EVENTS", this.event);
      this.show = false;
    },
  },
};
</script>
<style></style>
