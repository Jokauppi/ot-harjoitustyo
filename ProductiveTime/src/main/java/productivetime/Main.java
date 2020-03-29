package productivetime;

import productivetime.domain.ActivityControl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ActivityControl control = new ActivityControl();
        Scanner reader = new Scanner(System.in);

        String input = "";
        System.out.println("(stop ends program, list shows activities)");

        while (true){
            System.out.println("Insert activity:");
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
