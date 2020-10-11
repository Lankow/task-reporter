import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProcessesOrganizer {

    ArrayList<String> observedProcesses;
    ArrayList<WorkProcess> savedProcesses;
    ArrayList<String> logger;
    ProcessMeasurer processMeasurer;
    String activeProcess;

    public ProcessesOrganizer() throws IOException {
        observedProcesses = loadObservedFromFile("observedProcesses.txt");
        savedProcesses = loadSavedFromFile("savedProcesses.txt");
        logger = new ArrayList<>();
        processMeasurer = new ProcessMeasurer();
        activeProcess="";
    }

    private ArrayList<String> loadObservedFromFile(String filePath) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(filePath);
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) list.add(st);
            return list;
        }catch (FileNotFoundException e){
            File createdFile = new File(filePath);
            return new ArrayList<String>();
        }
    }
    private ArrayList<WorkProcess>loadSavedFromFile(String filePath) throws IOException {
        ArrayList<WorkProcess> list = new ArrayList<WorkProcess>();
        File file = new File(filePath);
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                String[] processDetails = st.split("##");
                String name = processDetails[0];
                Long workTime = Long.parseLong(processDetails[1]);
                WorkProcess workProcess = new WorkProcess(name,workTime);
                list.add(workProcess);
            };
            return list;
        }catch (FileNotFoundException e){
            File createdFile = new File(filePath);
            return new ArrayList<WorkProcess>();
        }
    }
    public void saveToFile(String filePath) throws IOException {
        FileWriter fstream = new FileWriter(filePath);
        BufferedWriter out = new BufferedWriter(fstream);
        for (WorkProcess process: savedProcesses) {
                String processDetails = "";
                processDetails += process.getName()+"##";
                processDetails += process.getWorkTime()+"##";
                out.write(processDetails + "\n");
        }
        saveLoggerToFile();
        out.close();
    }
    public void saveLoggerToFile() throws IOException {
        String filePath = "log\\log " + formatDate(LocalDateTime.now()).replaceAll(":",".") + ".txt";
        FileWriter fstream = new FileWriter(filePath);
        BufferedWriter out = new BufferedWriter(fstream);
        for (String log: logger) {
            out.write(log + "\n");
        }
        out.close();
    }
    public boolean isProcessToObserve(String processName){
        for (String process: observedProcesses) {
            if (processName.contains(process)) return true;
        }
        return false;
    }

    private boolean isSavedProcess(String processName){
        for (WorkProcess process: savedProcesses) {
            if (process.getName().equals(processName)) return true;
        }
        return false;
    }

    public void saveProcess(String processName){
        if(!isSavedProcess(processName) && isProcessToObserve(processName)){
            savedProcesses.add(new WorkProcess(processName));
            System.out.println("Detected and saved new process:");
            System.out.println(processName);
        }
        if (isSavedProcess(processName)){
            processMeasurer.checkActive(findProcessByName(processName));
        }
        logProcess(processName);
    }

    public WorkProcess findProcessByName(String processName){
        for (WorkProcess workProcess: savedProcesses) {
            if (processName.equals(workProcess.getName())) return workProcess;
        }
        return null;
    }

    public void createBackUp() throws IOException {
        String filePath = formatDate(LocalDateTime.now()).replaceAll(":",".") + ".txt";
        saveToFile("backup\\backup "+ filePath);
    }

    public void logProcess(String processName) {
        if(isProcessToObserve(processName) && activeProcess == ""){
            String start = "Log: " + processName + ": Start: " + formatDate(LocalDateTime.now());
            logger.add(start);
            activeProcess = processName;
        }
        else if(isProcessToObserve(processName) && !processName.equals(activeProcess)){
            String end = "Log: " + activeProcess + ": End: " + formatDate(LocalDateTime.now());
            String start = "Log: " + processName + ": Start: " + formatDate(LocalDateTime.now());
            logger.add(end);
            logger.add(start);
            activeProcess = processName;
            }
        else if(!isProcessToObserve(processName) && isProcessToObserve(activeProcess)){
            String end = "Log: " + activeProcess + ": End: " + formatDate(LocalDateTime.now());
            logger.add(end);
            activeProcess = "";
        }
        }

        private String formatDate(LocalDateTime date){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = date.format(formatter);
            return  formatDateTime;
        }
    }


