import java.util.Scanner;
import java.util.Arrays;
public class Helpers {
    
    // Partially AI-generated. Model: Gemini. Used to discover and use the Thread and its methods.
    // I wanted to make the Java program wait to write each character.
    // I wrote everything other than the Thread sleep and interruption logic.
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

    public static String checkAns(String[] cases) {
        Scanner input = new Scanner(System.in);
        while (true) {
            String ans = input.next();
            if (Arrays.asList(cases).contains(ans)) {
                return ans;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }
}
