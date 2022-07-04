package budget;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // write your code here
        Scanner sc = new Scanner(System.in);
        MenuLevel1 menuLevel1 = new MenuLevel1();
        int currentActionNumber = -1;
        while(currentActionNumber != 0) {
            menuLevel1.chooseAction(sc);
            currentActionNumber = menuLevel1.getCurrentActionNumber();
            menuLevel1.doAction(currentActionNumber, sc);
        }
        sc.close();
    }
}
