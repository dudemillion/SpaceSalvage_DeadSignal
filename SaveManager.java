import java.io.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Runs the functions of saving and loading the game.
 */
public class SaveManager {
    private String saveName;
    private Ship ship;
    private ArrayList<ExpeditionResult> expeditions;
    private int loadedSpent;
    private int loadedEarned;
    private int loadedSuccessfulExpeds;

    /**
     * Creates a new save manager. Also stores the player's total spent credits, earned credits, and successful expeditions.
     * @param saveName The name of the save.
     * @param ship The player's ship.
     * @param expeditions The expeditions the player went on.
     */
    public SaveManager(String saveName, Ship ship, ArrayList<ExpeditionResult> expeditions) {
        this.saveName = saveName;
        this.ship = ship;
        this.expeditions = expeditions;
        this.loadedSpent = 0;
        this.loadedEarned = 0;
        this.loadedSuccessfulExpeds = 0;
    }

    /**
     * Gets the save's name.
     * @return The name of the save.
     */
    public String getSave() {
        return saveName;
    }

    /**
     * Sets the name of the save.
     * @param saveName The new name.
     */
    public void setSave(String saveName) {
        if (saveName != null && !saveName.trim().isEmpty()) {
            this.saveName = saveName;
        } else {
            System.out.println("Name cannot be empty.");
        }
    }

    /**
     * Gets the player's ship.
     * @return The player's ship.
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Gets the expeditions the player went on.
     * @return All the expeditions the player went on.
     */
    public ArrayList<ExpeditionResult> getExpeds() {
        return expeditions;
    }

    /**
     * Sets the expeditions the player went on.
     * @param expeditions The new list of expeditions. 
     */
    public void setExpeds(ArrayList<ExpeditionResult> expeditions) {
        this.expeditions = expeditions;
    }

    /**
     * Gets the total credits spent.
     * @return The total credits spent.
     */
    public int getTotalSpent() {
        return loadedSpent;
    }

    /**
     * Sets the total credits spent.
     * @param totalSpent The new total.
     */
    public void setTotalSpent(int totalSpent) {
        if (totalSpent < 0) {
            System.out.println("Total spent cannot be negative.");
        } else {
            loadedSpent = totalSpent;
        }
    }

    /**
     * Gets the total credits earned.
     * @return The total credits earned.
     */
    public int getTotalEarned() {
        return loadedEarned;
    }

    /**
     * Sets the total credits earned.
     * @param totalEarned The new total.
     */
    public void setTotalEarned(int totalEarned) {
        if (totalEarned < 0) {
            System.out.println("Total earned cannot be negative.");
        } else {
            loadedEarned = totalEarned;
        }
    }

    /**
     * Gets the total successful expeditions the player went on.
     * @return The total successful expeditions.
     */
    public int getTotalSuccessfulExpeds() {
        return loadedSuccessfulExpeds;
    }

    /**
     * Sets the total successful expeditions the player went on.
     * @param successfulExpeditions The new total.
     */
    public void setTotalSuccessfulExpeds(int successfulExpeditions) {
        if (successfulExpeditions < 0) {
            System.out.println("Total expeditions cannot be negative.");
        } else {
            loadedSuccessfulExpeds = successfulExpeditions;
        }
    }

    // Partially AI-generated. Model: ChatGPT. This code essentially allows me to make a list of keys equal to values, like a JSON. Then I write it to a file.
    /**
     * Saves the game. Uses Properties to easily write save data to a file.
     * @param totalSpent The total credits spent
     * @param totalEarned The total credits earned
     * @param successfulExpeditions The total amount of successful expeditions.
     * @return Whether the save was successful.
     */
    public boolean saveGame(int totalSpent, int totalEarned, int successfulExpeditions) {
        Properties data = new Properties();
        data.setProperty("ship.hull", String.valueOf(ship.getHull()));
        data.setProperty("ship.maxHull", String.valueOf(ship.getMaxHull()));
        data.setProperty("ship.shield", String.valueOf(ship.getShield()));
        data.setProperty("ship.maxShield", String.valueOf(ship.getMaxShield()));
        data.setProperty("ship.fuel", String.valueOf(ship.getFuel()));
        data.setProperty("ship.maxFuel", String.valueOf(ship.getMaxFuel()));
        data.setProperty("shop.credits", String.valueOf(ship.getCredits()));
        data.setProperty("upgrade.count", String.valueOf(ship.getUpgrades().length));
        for (int i = 0; i<ship.getUpgrades().length; i++) {
            data.setProperty("upgrade." + i, String.valueOf(ship.getUpgrades()[i]));
        }
        data.setProperty("stats.totalSpent", String.valueOf(totalSpent));
        data.setProperty("stats.totalEarned", String.valueOf(totalEarned));
        data.setProperty("stats.totalExpeditions", String.valueOf(expeditions.size()));
        data.setProperty("stats.successfulExpeditions", String.valueOf(successfulExpeditions));
        data.setProperty("history.count", String.valueOf(expeditions.size()));
        for (int i = 0; i<expeditions.size(); i++) {
            data.setProperty("history." + i + ".name", expeditions.get(i).getExpeditionName());
            data.setProperty("history." + i + ".matches", String.valueOf(expeditions.get(i).getMatches()));
            data.setProperty("history." + i + ".reward", String.valueOf(expeditions.get(i).getReward()));
            data.setProperty("history." + i + ".damage", String.valueOf(expeditions.get(i).getDamageTaken()));
            data.setProperty("history." + i + ".success", String.valueOf(expeditions.get(i).wasSuccessful()));
        }
        File saveDirectory = new File("saves");
        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }
        File saveFile = new File("saves", saveName + ".properties");
        try (FileOutputStream out = new FileOutputStream(saveFile)) {
            data.store(out, "Space Salvage Save File");
            return true;
        } catch (IOException e) {
            System.out.println("Failed to save game, error is " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads the game. Ensures the file exists before attempting load.
     * @param input The player's input.
     */
    public void loadGame(Scanner input) {
        input.nextLine();
        while (true) {
            System.out.println("What is the name of your file? (CASE-SENSITIVE)");
            String name = input.nextLine().trim();
            Properties data = new Properties();
            try (InputStream in = new FileInputStream("saves/" + name + ".properties")) {
                data.load(in);
                ship.setMaxHull(Integer.parseInt(data.getProperty("ship.maxHull")));
                ship.setHull(Integer.parseInt(data.getProperty("ship.hull")));
                ship.setMaxShield(Integer.parseInt(data.getProperty("ship.maxShield")));
                ship.setShield(Integer.parseInt(data.getProperty("ship.shield")));
                ship.setMaxFuel(Double.parseDouble(data.getProperty("ship.maxFuel")));
                ship.setFuel(Double.parseDouble(data.getProperty("ship.fuel")));
                ship.setCredits(Integer.parseInt(data.getProperty("shop.credits")));
                for (int i = 0; i < Integer.parseInt(data.getProperty("history.count")); i++) {
                    expeditions.add(new ExpeditionResult(data.getProperty("history." + i + ".name"), Integer.parseInt(data.getProperty("history." + i + ".matches")), Integer.parseInt(data.getProperty("history." + i + ".reward")), Integer.parseInt(data.getProperty("history." + i + ".damage")), Boolean.parseBoolean(data.getProperty("history." + i + ".success"))));
                }
                loadedSpent = Integer.parseInt(data.getProperty("stats.totalSpent"));
                loadedEarned = Integer.parseInt(data.getProperty("stats.totalEarned"));
                loadedSuccessfulExpeds = Integer.parseInt(data.getProperty("stats.successfulExpeditions"));
                int upgrades = Integer.parseInt(data.getProperty("upgrade.count"));
                String[] allupgrades = new String[upgrades];
                for (int i = 0; i < upgrades; i++) {
                    allupgrades[i] = data.getProperty("upgrade." + i);
                }
                ship.setUpgrades(allupgrades);
                this.saveName = name;
                System.out.println("Successfully loaded save.");
                break;
            } catch (IOException e) {
                System.out.println("Error while getting file: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Number format exception: " + e.getMessage());
            }
        }
    }

    // Partially AI-generated. Model: ChatGPT. This method uses the PrintWriter to write lines to the saves/(insertname)_stats.txt file.
    /**
     * Exports all statistics.
     * @param successfulExpeditions The total successful expeditions.
     * @param totalEarned The total amount of credits earned.
     * @param totalSpent The total amount of credits spent.
     * @param matches The total amount of matches made.
     * @param damageTaken The total amount of damage taken.
     */
    public void exportStatistics(int successfulExpeditions, int totalEarned, int totalSpent, int matches, int damageTaken) {
        File stats = new File("saves", saveName + "_stats.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(stats))) {
            writer.println("===== SPACE SALVAGE: DEAD SIGNAL =====");
            writer.println("Save: " + saveName);
            writer.println();
            writer.println("Total expeditions: " + expeditions.size());
            writer.println("Successful expeditions: " + successfulExpeditions);
            writer.println();
            writer.println("Total credits earned: " + totalEarned);
            writer.println("Total credits spent: " + totalSpent);
            writer.println("Net credit profit/loss: " + (totalEarned - totalSpent));
            writer.println();
            writer.println("Total Frequency Matches: " + matches);
            writer.println("Total damage taken: " + damageTaken);
            double winRate = expeditions.isEmpty() ? 0.0 : (successfulExpeditions * 100.0 / expeditions.size());
            writer.printf("Success rate: %.1f%%%n", winRate);
            System.out.println("Successfully generated statistics.");
        } catch (IOException e) {
            System.out.println("Failed to export statistics: " + e.getMessage());
        }
    }
}
