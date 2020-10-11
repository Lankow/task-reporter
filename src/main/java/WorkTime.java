public class WorkTime {

    private Long hours;
    private Long minutes;
    private Long seconds;

    public WorkTime(Long hours, Long minutes, Long seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public WorkTime() {
        hours = 0L;
        minutes = 0L;
        seconds = 0L;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }
}
