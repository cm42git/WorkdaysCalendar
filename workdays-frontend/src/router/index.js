import Vue from "vue";
import VueRouter from "vue-router";
import CalendarViewVue from "@/views/CalendarView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: CalendarViewVue,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
