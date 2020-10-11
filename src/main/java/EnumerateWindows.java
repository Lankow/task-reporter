import com.sun.jna.*;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class EnumerateWindows {
    private static final int MAX_TITLE_LENGTH = 1024;

    public static void main(String[] args) throws IOException {
        ProcessesOrganizer processesOrganizer = new ProcessesOrganizer();
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome to Task Reporter - Pick an option from below:");
//        char choice = '0';
//        while(choice  != ('3')){
//            System.out.println("1.Show observed processes");
//            System.out.println("2.Start Reporting");
//            System.out.println("3.Quit");
//            choice = scanner.nextLine().charAt(0);
//            switch (choice){
//                case '1':
//                break;
//                case '2':
//
//            }
//        }
        try {

            System.out.println("Reporting Started - In order to stop - PRESS ENTER");
            while (System.in.available() == 0) {
                char[] buffer = new char[MAX_TITLE_LENGTH * 2];
                WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
                User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
                String processName = Native.toString(buffer);
                processesOrganizer.saveProcess(processName);
            }
            processesOrganizer.createBackUp();
            processesOrganizer.saveToFile("savedProcesses.txt");
            System.out.println("Your Report is Ready! See you soon.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}