import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Expedition {
    private double fuelCost;
    private String name;
    private int credCost;
    private int baseReward;

    public Expedition(double fuelCost, String name, int credCost, int baseReward) {
        this.fuelCost = fuelCost;
        this.name = name;
        this.credCost = credCost;
        this.baseReward = baseReward;
    }

    public double getFuelCost() {
        return this.fuelCost;
    }

    public void setFuelCost(double fuelCost) {
        this.fuelCost = fuelCost;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredCost() {
        return this.credCost;
    }

    public void setCredCost(int credCost) {
        this.credCost = credCost;
    }

    public int getBaseReward() {
        return this.baseReward;
    }

    public void setBaseReward(int baseReward) {
        this.baseReward = baseReward;
    }

    public boolean resolveHazard(Ship ship) {
        int chance = (int)(Math.random() * 5);
        System.out.println("Ship: ");
        Helpers.typewrite("'Searching for anomalies...'", 300);
        if (chance >= 0 && chance < 1) {
            System.out.println("No anomalies found! Safe travels from here.");
            return true;
        } else if (chance >= 1 && chance < 2) {
            Helpers.typewrite("Danger! An asteroid is on a collision course with navigation!", 70);
            System.out.println("Choose what to do...");
            int fuelLoss = ThreadLocalRandom.current().nextInt(10, 16);
            int damage = ThreadLocalRandom.current().nextInt(15, 21);
            System.out.println("[1] Divert navigation (-" + fuelLoss + " fuel)");
            System.out.println("[2] Do nothing (" + damage + " damage)");
            System.out.println("[3] Pray (5% chance of no damage)");
        }
    } 
}
