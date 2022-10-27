import java.time.LocalDate;
import model.SchedulePattern;

//TODO User inputs
// on, off, onAlt, offAlt, line 1 Start
// get month to start displaying
// get # months to display

//TODO Display calendar with on & off
// Display a calendar
// how to show on/off?
// holidays?

public class OnOrOffDay {
    public static void main(String[] args) {
        // Schedule schedFiveDay=new Schedule(5,2,LocalDate.of(2002,2,1));
        LocalDate dateToCheck = LocalDate.of(2022, 7, 4);

        // boolean working= schedFiveDay.onOrOff(dateToCheck,3);
        // System.out.println("Working on " + dateToCheck + ": " + working);

        // LocalDate startLineOne=LocalDate.of(2022,7,4);
        // SchedulePattern sixOn587 = new SchedulePattern(6, 5, 8, 7, startLineOne);
        // System.out.println(sixOn587);
        // System.out.println("Working on " + dateToCheck + ": " +
        // sixOn587.isWorkingDay(6, dateToCheck));

        SchedulePattern fiveOnTwoOff = new SchedulePattern(5, 2, 0, 0, LocalDate.of(2022, 7, 25));
        System.out.println(fiveOnTwoOff);
        System.out.println("Working on " + dateToCheck + ": " + fiveOnTwoOff.isWorkingDay(1, dateToCheck));

        // System.out.println("\u2660 \u2661 \u2662 \u2663 \u2664 \u2665 \u2666
        // \u2667");
        System.out.println(fiveOnTwoOff.workDays(1, LocalDate.of(2022, 10, 1), 365));
        System.out.println("===============");
        System.out.println(fiveOnTwoOff.listStartEnd(dateToCheck, 8, 1));
    }

    // public static boolean onOrOff(int on, int off, int line, LocalDate
    // startLineOne, LocalDate onDay){
    // boolean output;
    // long patternOnOff=-1;
    //
    // patternOnOff= (ChronoUnit.DAYS.between(startLineOne,onDay) + line - 1) %
    // (on+off);
    // output=patternOnOff<on;
    //
    // return output;
    // }
}
