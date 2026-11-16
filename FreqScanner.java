/*
I have only used resources indicated that are allowable.
The work submitted is my, and only my work.
Signature: Nikhil Bhalla
Date: 11/15/2026
*/

import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import java.util.Arrays;
/**
 * The frequency scanner minigame. Match 5 frequencies 1-5 and gain a reward multiplier based on it.
 */
public class FreqScanner {
    private int[] targetFrequency;
    private int[] playerFrequency;
    private int[] revealedPositions;
    private int matches;

    /**
     * Creates a new frequency scanner.
     */
    public FreqScanner() {
        this.targetFrequency = new int[5];
        this.playerFrequency = new int[5];
        this.revealedPositions = new int[5];
        this.matches = 0;
    }

    /**
     * Gets the target frequency of the minigame.
     * @return The target frequency.
     */
    public int[] getTargetFreq() {
        return targetFrequency;
    }

    /**
     * Gets the frequency guessed by the player.
     * @return The frequency guessed.
     */
    public int[] getPlayerFreq() {
        return playerFrequency;
    }

    /**
     * Gets the revealed positions of the frequency given to the player.
     * @return The revealed positions.
     */
    public int[] getRevealedPositions() {
        return revealedPositions;
    }

    /**
     * Gets how many matches the player made.
     * @return The amount of matches.
     */
    public int getMatches() {
        return matches;
    }

    /**
     * Sets the target frequency to a new one.
     * @param freqs The new target frequency.
     */
    public void setTargetFreq(int[] freqs) {
        if (freqs.length != 5) {
            System.out.println("Target frequency must be of 5 integers.");
        } else {
            this.targetFrequency = freqs;
        }
    }

    /**
     * Sets the player frequency to a new one.
     * @param freqs The new player frequency.
     */
    public void setPlayerFreq(int[] freqs) {
        if (freqs.length != 5) {
            System.out.println("Player frequency must be of 5 integers.");
        } else {
            this.playerFrequency = freqs;
        }
    }

    /**
     * Sets the revealed positions of the frequency.
     * @param positions The new positions.
     */
    public void setRevealedPositions(int[] positions) {
        if (positions.length != 5) {
            System.out.println("Revealed positions must be of 5 integers. Example: 0, 0, 1, 0, 0. Meaning the 3rd position is only revealed.");
        } else {
            this.revealedPositions = positions;
        }
    }

    /**
     * Sets the matches the player made.
     * @param matches The amount of matches.
     */
    public void setMatches(int matches) {
        if (matches > 5 || matches < 0) {
            System.out.println("Invalid match count. Must be from 0 to 5.");
        } else {
            this.matches = matches;
        }
    }

    /**
     * The actual minigame. Generates a random target frequency, gives the player random positions revealed based on upgrades and allows them to guess.
     * @param ship The player's ship.
     * @param scanner The player's input.
     * @return How many matches the player made.
     */
    public int generateSignal(Ship ship, Scanner scanner) {
        this.matches = 0;
        for (int i = 0; i < this.targetFrequency.length; i++) {
            this.targetFrequency[i] = ThreadLocalRandom.current().nextInt(1, 6);
        }
        Helpers.typewrite("Notice: A frequency has been picked up over a channel...", 70);
        Helpers.typewrite("Frequency alignment required. Please enter the frequency values.", 0);
        System.out.println("Tip: Enter 5 random numbers from 1-5. Revealed positions are shown below, so you're guaranteed one match at least if you're reading this.");
        System.out.println("Revealed values: " + revealFrequencies(ship, this.revealedPositions, this.targetFrequency));
        int successes = 0;
        while (successes < playerFrequency.length) {
            int value = Helpers.tryAns(scanner);
            if(!(value > 5 || value < 1)) {
                playerFrequency[successes] = value;
                successes++;
            } else {
                System.out.println("Invalid frequency value. Enter them from 1-5.");
            }
        }
        double mult = 1.0;
        for (int i = 0; i < targetFrequency.length; i++) {
            Helpers.typewrite("Checking frequency " + (i+1) + "...", 30);
            if (targetFrequency[i] == playerFrequency[i]) {
                Helpers.typewrite("Success!", 50);
                this.matches++;
            } else {
                Helpers.typewrite("The frequency errs.", 50);
            }
        }
        switch (this.matches) {
            case 0: Helpers.typewrite("No matches made. Frequency lost.", 70); break;
            case 1: Helpers.typewrite("One match made. The frequency falters, but holds lightly.", 70); mult = 1.2; break;
            case 2: Helpers.typewrite("Two matches made. The frequency holds just enough.", 70); mult = 1.5; break;
            case 3: Helpers.typewrite("Three matches made. The frequency is holding quite strong.", 70); mult = 2.0; break;
            case 4: Helpers.typewrite("Four matches made. The frequency is holding very strong!", 70); mult = 2.5; break;
            case 5: Helpers.typewrite("Five matches made. The frequency holds perfectly!", 70); mult = 3.0; break;
            default: System.out.println("Error."); break;
        }

        if (mult > 1.0) {
            Helpers.typewrite("You extract more valuable salvage by following the frequency, and store it in the ship.", 60);
            Helpers.typewrite("The company has approved a multiplication to your payout of " + mult + ".", 70);
        } else {
            Helpers.typewrite("You struggle to find good salvage without a frequency to follow.", 60);
            Helpers.typewrite("The company is disappointed in your performance.", 70);
        }
        return matches;
    }

    /**
     * Reveals frequencies to the player based on upgrades.
     * @param ship The player's ship.
     * @param revealedFrequencies The revealed frequencies
     * @param frequencies The target frequency
     * @return The revealed frequency values of the target frequency.
     */
    public static String revealFrequencies(Ship ship, int[] revealedFrequencies, int[] frequencies) {
        Arrays.fill(revealedFrequencies, 0);
        int revealed = 1;
        String message = "";
        for (int i = 0; i<ship.getUpgrades().length; i++) {
            if ("Advanced Scanner".equals(ship.getUpgrades()[i])) {
                if (revealed >= 5) {
                    System.out.println("Error. Revealed frequencies exceeded 5.");
                    break;
                } else {
                    revealed++;
                }
            }
        }
        int trueRevealed = 0;
        while (trueRevealed < revealed) {
            int randomPos = ThreadLocalRandom.current().nextInt(0, 5);
            if (!(revealedFrequencies[randomPos] == 1)) {
                revealedFrequencies[randomPos] = 1;
                trueRevealed++;
            }
        }
        
        for (int i = 0; i < revealedFrequencies.length; i++) {
            if (revealedFrequencies[i] == 1) {
                message = message + "Position " + (i+1) + ": " + frequencies[i] + " ";
            }
        }
        return message;
    }
}
