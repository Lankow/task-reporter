import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProcessMeasurer {

    WorkProcess workProcess;
    Long startTime;
    Long endTime;

    public ProcessMeasurer() {
        workProcess = null;
        startTime = System.currentTimeMillis();
    }

    public WorkProcess getWorkProcess() {
        return workProcess;
    }

    public void setWorkProcess(WorkProcess workProcess) {
        this.workProcess = workProcess;
    }

    public void startMeasuring(WorkProcess workProcess){
//        System.out.println(workProcess.getName() + ": Start: " + LocalDateTime.now());
        this.workProcess = workProcess;
        startTime = System.currentTimeMillis();
    }

    public void checkActive(WorkProcess activeProcess){
        if(workProcess == null) startMeasuring(activeProcess);;
        updateProcess(workProcess);
        if(!activeProcess.getName().equals(workProcess.getName())) {
//            System.out.println(workProcess.getName() + ": End: " + LocalDateTime.now());
            startMeasuring(activeProcess);
        }
    }

    private void updateProcess(WorkProcess workProcess){
        endTime = System.currentTimeMillis();
        Long duration = (endTime - startTime)/1000;
        if(duration>=1){
            workProcess.setWorkTime(workProcess.getWorkTime() + 1);
            startTime = System.currentTimeMillis();
        }
    }

    public void log(String processName) {

    }
}
