import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import com.google.ortools.Loader;

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
        Schedule schedFiveDay=new Schedule(5,2,LocalDate.of(2002,2,1));
        LocalDate dateToCheck=LocalDate.of(2022,7,27);


        boolean working= schedFiveDay.onOrOff(dateToCheck,3);
        System.out.println("Working on "+ dateToCheck+": "+working);

//        System.out.println("\u2660 \u2661 \u2662 \u2663 \u2664 \u2665 \u2666 \u2667");

    }

//    public static boolean onOrOff(int on, int off, int line, LocalDate startLineOne, LocalDate onDay){
//        boolean output;
//        long patternOnOff=-1;
//
//        patternOnOff= (ChronoUnit.DAYS.between(startLineOne,onDay) + line - 1) % (on+off);
//        output=patternOnOff<on;
//
//        return output;
//    }
}
