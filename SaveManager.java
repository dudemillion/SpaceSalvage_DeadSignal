import java.io.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveManager {
    private String saveName;
    private Ship ship;
    private ArrayList<ExpeditionResult> expeditions;
    private int loadedSpent;
    private int loadedEarned;
    private int loadedSuccessfulExpeds;

    public SaveManager(String saveName, Ship ship, ArrayList<ExpeditionResult> expeditions) {
        this.saveName = saveName;
        this.ship = ship;
        this.expeditions = expeditions;
        this.loadedSpent = 0;
        this.loadedEarned = 0;
        this.loadedSuccessfulExpeds = 0;
    }

    public String getSave() {
        return saveName;
    }

    public void setSave(String saveName) {
        if (saveName != null && !saveName.trim().isEmpty()) {
            this.saveName = saveName;
        } else {
            System.out.println("Name cannot be empty.");
        }
    }

    public Ship getShip() {
        return ship;
    }

    public ArrayList<ExpeditionResult> getExpeds() {
        return expeditions;
    }

    public void setExpeds(ArrayList<ExpeditionResult> expeditions) {
        this.expeditions = expeditions;
    }

    public int getTotalSpent() {
        return loadedSpent;
    }

    public void setTotalSpent(int totalSpent) {
        if (totalSpent < 0) {
            System.out.println("Total spent cannot be negative.");
        } else {
            loadedSpent = totalSpent;
        }
    }

    public int getTotalEarned() {
        return loadedEarned;
    }

    public void setTotalEarns(int totalEarned) {
        if (totalEarned < 0) {
            System.out.println("Total earned cannot be negative.");
        } else {
            loadedEarned = totalEarned;
        }
    }

    public int getTotalSuccessfulExpeds() {
        return loadedSuccessfulExpeds;
    }

    public void setTotalSuccessfulExpeds(int successfulExpeditions) {
        if (successfulExpeditions < 0) {
            System.out.println("Total expeditions cannot be negative.");
        } else {
            loadedSuccessfulExpeds = successfulExpeditions;
        }
    }

    // Partially AI-generated. Model: ChatGPT. This code essentially allows me to make a list of keys equal to values, like a JSON. Then I write it to a file.
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

    public void loadGame(Scanner input) {
        while (true) {
            System.out.println("What is the name of your file? (CASE-SENSITIVE)");
            input.nextLine();
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
}
