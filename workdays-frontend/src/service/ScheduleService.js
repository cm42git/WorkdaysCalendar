import axios from "axios";

const http = axios.create({ baseURL: "http://localhost:9000" });

export default {
  getStartEnd(
    daysOn,
    daysOff,
    daysOnAlt,
    daysOffAlt,
    startLineOne,
    beginMonth,
    numMonths,
    lineNum
  ) {
    return http.get(`/scheduledates?daysOn=${daysOn}&daysOff=${daysOff}&daysOnAlt=${daysOnAlt}&daysOffAlt=${daysOffAlt}&startLineOne=${startLineOne}&beginMonth=${beginMonth}&numMonths=${numMonths}&lineNum=${lineNum}`);
  },
};
