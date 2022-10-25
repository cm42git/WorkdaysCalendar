import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private int onDays;
    private int offDays;
    private List<Integer> options;
    private LocalDate dateOption1Start;

    // Default generator creates all possible options
    public Schedule(int inputOnDays, int inputOffDays, LocalDate inputOption1Start){
        this.onDays=inputOnDays;
        this.offDays=inputOffDays;
        this.dateOption1Start=inputOption1Start;
        options=new ArrayList<>();
        for(int i=1;i<=inputOnDays+inputOffDays;i++){
            options.add(i);
        }
    }

    // Generator for if user wants to limit schedule options
    public Schedule(int inputOnDays, int inputOffDays, List<Integer> inputOptions, LocalDate inputOption1Start){
        this.onDays=inputOnDays;
        this.offDays=inputOffDays;
        this.options=inputOptions;
        this.dateOption1Start=inputOption1Start;
    }

    public int getOnDays() {
        return onDays;
    }

    public int getOffDays() {
        return offDays;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public void setOptions(List<Integer> options) {
        this.options = options;
    }

    public LocalDate getDateOption1Start() {
        return dateOption1Start;
    }

    public void setDateOption1Start(LocalDate dateOption1Start) {
        this.dateOption1Start = dateOption1Start;
    }

    public boolean onOrOff(LocalDate onDay, int option) {
        boolean output;
        long patternOnOff = -1;

        patternOnOff = (ChronoUnit.DAYS.between(getDateOption1Start(), onDay) + option - 1) % (getOnDays() + getOffDays());
        output = patternOnOff < getOnDays();

        return output;
    }
}
