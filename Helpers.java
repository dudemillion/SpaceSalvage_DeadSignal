/*
I have only used resources indicated that are allowable.
The work submitted is my, and only my work.
Signature: Nikhil Bhalla
Date: 11/15/2026
*/

import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * Consists of methods I consistently use throughout all the classes I make in this project.
 */
public class Helpers {
    
    /**
     * Dummy default constructor. To appease JavaDocs.
     */
    private Helpers() {

    }
    // Partially AI-generated. Model: Gemini. Used to discover and use the Thread and its methods.
    // I wanted to make the Java program wait to write each character.
    // I wrote everything other than the Thread sleep and interruption logic.
    /**
     * Types the given String letter by letter at a given speed in ms.
     * @param words The String to type.
     * @param speed The speed in ms.
     */
    public static void typewrite(String words, int speed) {
        System.out.println("");
        for (int i = 0; i<words.length(); i++) {
            System.out.print(words.charAt(i));
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                System.out.println("Interrupted sleep.");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("");
    }

    /**
     * try-catch input validation for inputs that require a number.
     * @param input The player's input.
     * @return The validated answer the player gave.
     */
    public static int tryAns(Scanner input) {
        while (true) {
            try {
                int ans = input.nextInt();
                return ans;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
    }
}
