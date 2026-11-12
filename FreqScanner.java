import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
public class FreqScanner {
    private int[] targetFrequency;
    private int[] playerFrequency;
    private int[] revealedPositions;

    public FreqScanner() {
        this.targetFrequency = new int[5];
        this.playerFrequency = new int[5];
        this.revealedPositions = new int[5];
    }

    public int[] getTargetFreq() {
        return targetFrequency;
    }

    public int[] getPlayerFreq() {
        return playerFrequency;
    }

    public int[] getRevealedPositions() {
        return revealedPositions;
    }

    public void setTargetFreq(int[] freqs) {
        if (freqs.length != 5) {
            System.out.println("Target frequency must be of 5 integers.");
        } else {
            this.targetFrequency = freqs;
        }
    }

    public void setPlayerFreq(int[] freqs) {
        if (freqs.length != 5) {
            System.out.println("Player frequency must be of 5 integers.");
        } else {
            this.playerFrequency = freqs;
        }
    }

    public void setRevealedPositions(int[] positions) {
        if (positions.length != 5) {
            System.out.println("Revealed positions must be of 5 integers. Example: 0, 0, 1, 0, 0. Meaning the 3rd position is only revealed.");
        } else {
            this.revealedPositions = positions;
        }
    }

    public void generateSignal() {
        for (int i = 0; i < this.targetFrequency.length; i++) {
            this.targetFrequency[i] = ThreadLocalRandom.current().nextInt(1, 6);
        }
        Helpers.typewrite("Notice: A frequency has been picked up over a channel...", 70);
        Helpers.typewrite("Frequency alignment required. Please enter the frequency values.", 0);
        System.out.println("Tip: Enter 5 random numbers from 1-5. Revealed positions are shown below, so you're guaranteed one match at least if you're reading this.");
        System.out.println("Revealed values: " + revealFrequencies());
    }

    public static String revealFrequencies(Ship ship) {
        int revealed = 1;
        for (int i = 0; i<ship.getUpgrades().length; i++) {
            if (ship.getUpgrades()[i] == "Scanner") {
                revealed++;
            }
        }
        ArrayList<Integer> revealedPos = new ArrayList<>();
        for (int i = 0; i<revealed; i++) {
            int randomPos = ThreadLocalRandom.current().nextInt(0, 2);
            revealedPos.add(randomPos);
        }
    }
}
