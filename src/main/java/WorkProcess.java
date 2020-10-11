public class WorkProcess {

    private String name;
    private Long workTime;

    public WorkProcess(String name, Long workTime) {
        this.name = name;
        this.workTime = workTime;
    }

    public WorkProcess(String name) {
        this.name = name;
        this.workTime = 0L;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Long workTime) {
        this.workTime = workTime;
    }
}
