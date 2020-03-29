package productivetime;

import productivetime.domain.ActivityControl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");

        ActivityControl control = new ActivityControl();
        Scanner reader = new Scanner(System.in);

        String input = "";

        while (true){
            System.out.println("Insert activity:\t(stop end program, list shows activities)");
            input = reader.nextLine();
            if (input.equals("stop")){
                break;
            } else if (input.equals("list")){
                control.listActivities();
            } else {
                control.addActivity(input);
            }
        }

    }
}
