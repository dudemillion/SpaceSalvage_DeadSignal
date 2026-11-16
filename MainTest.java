import java.util.Scanner;
/**
 * Starts the game.
 */
public class MainTest {
    /**
     * Dummy default constructor to appease JavaDocs.
     */
    private MainTest() {

    }
    /**
     * Starts the Space Salvage: Dead Signal.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new GameManager().startGame();
    }
}
