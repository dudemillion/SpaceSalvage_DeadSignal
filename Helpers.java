public class Helpers {
    
    // Partially AI-generated. Model: Gemini. Used to discover and use the Thread and its methods.
    // I wanted to make the Java program wait to write each character.
    // I wrote everything other than the Thread sleep and interruption logic.
    public static void typewrite(String words, int speed) {
        for (int i = 0; i<words.length(); i++) {
            System.out.print(words.charAt(i));
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                System.out.println("Interrupted sleep.");
                Thread.currentThread().interrupt();
            }
        }
    }
}
