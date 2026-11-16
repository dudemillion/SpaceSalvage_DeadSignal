import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

/**
 * The expeditions of the game.
 * Where most events happen.
 */
public class Expedition {
    private double fuelCost;
    private String name;
    private int credCost;
    private int baseReward;

    /**
     * Creates a new expedition.
     * @param fuelCost The fuel cost of the expedition.
     * @param name The name of the expedition.
     * @param credCost The credit cost of the expedition.
     * @param baseReward The base reward of the expedition.
     */
    public Expedition(double fuelCost, String name, int credCost, int baseReward) {
        this.fuelCost = fuelCost;
        this.name = name;
        this.credCost = credCost;
        this.baseReward = baseReward;
    }

    /**
     * Gets the cost in fuel of the expedition.
     * @return The expedition's fuel cost.
     */
    public double getFuelCost() {
        return this.fuelCost;
    }

    /**
     * Sets the fuel cost of an expedition.
     * @param fuelCost The new fuel cost.
     */
    public void setFuelCost(double fuelCost) {
        if (fuelCost < 0) {
            System.out.println("Cannot set expedition fuel cost to negative.");
        } else {
        this.fuelCost = fuelCost;
        }
    }

    /**
     * Gets the name of the expedition.
     * @return The name of the expedition.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the expedition.
     * @param name The new name for the expedition.
     */
    public void setName(String name) {
        if (name.isBlank()) {
            System.out.println("Cannot set name to blank.");
        } else {
            this.name = name;
        }
    }

    /**
     * Gets the credit cost of the expedition.
     * @return The expedition's credit cost.
     */
    public int getCredCost() {
        return this.credCost;
    }

    /**
     * Sets the credit cost of the expedition
     * @param credCost The new credit cost.
     */
    public void setCredCost(int credCost) {
        if (credCost < 0) {
            System.out.println("Cannot set expedition credit cost below 0.");
        } else {
            this.credCost = credCost;
        }
    }

    /**
     * Gets the base reward of the expedition.
     * @return The expedition's base reward.
     */
    public int getBaseReward() {
        return this.baseReward;
    }

    /**
     * Sets the base reward of the expedition.
     * @param baseReward The new base reward.
     */
    public void setBaseReward(int baseReward) {
        if (baseReward < 0) {
            System.out.println("Cannot set base reward below 0.");
        } else {
            this.baseReward = baseReward;
        }
    }

    /**
     * Starts a hazard encounter, either an asteroid, radiation, pirate, or shield malfunction encounter.
     * @param ship The player's ship.
     * @param input The player's input.
     * @return Any bonus salvage earned.
     */
    public int resolveHazard(Ship ship, Scanner input) {
        int chance = (int)(Math.random() * 5);
        System.out.print("Ship: ");
        Helpers.typewrite("Searching for anomalies...", 100);
        if (chance >= 0 && chance < 1) {
            System.out.println("No anomalies found! Safe travels from here.");
            return 0;
        } else if (chance >= 1 && chance < 2) {
            return asteroidEncounter(ship, input);
        } else if (chance >= 2 && chance < 3) {
            return radiationEncounter(ship, input);
        } else if (chance >= 3 && chance < 4) {
            return pirateEncounter(ship, input);
        } else {
            return shieldMalfunction(ship, input);
        }
    }

    /**
     * The asteroid encounter, player can choose to divert navigation, brace the ship, or pray.
     * @param ship The player's ship.
     * @param input The player's input.
     * @return Any bonus salvage.
     */
    private static int asteroidEncounter(Ship ship, Scanner input) {
        Helpers.typewrite("Danger! An asteroid is on a collision course with navigation!", 70);
        System.out.println("Choose what to do...");
        int fuelLoss = ThreadLocalRandom.current().nextInt(10, 16);
        int damage = ThreadLocalRandom.current().nextInt(15, 26);
        System.out.println("[1] Divert navigation (-" + fuelLoss + " fuel)");
        System.out.println("[2] Brace ship (" + damage + " damage)");
        System.out.println("[3] Pray (5% chance of no damage)");
        while (true) {
            int choice = Helpers.tryAns(input);
            if (choice == 1) {
                if (ship.getFuel() < fuelLoss) {
                    Helpers.typewrite("Warning! Insufficient fuel! Course diversion impossible.", 70);
                    Helpers.typewrite("Alert! Asteroid collision! The ship has taken " + damage + " damage.", 70);
                    ship.takeDamage(damage);
                    return 0;
                } else {
                    ship.setFuel(ship.getFuel() - fuelLoss);
                    Helpers.typewrite("Successfully diverted course. Thank you.", 70);
                    return 0;
                }   
            } else if (choice == 2) {
                ship.takeDamage(damage);
                Helpers.typewrite("Alert! Asteroid collision! The ship has taken " + damage + " damage.", 70);
                return 0;
            } else if (choice == 3) {
                int pray = ThreadLocalRandom.current().nextInt(1, 101);
                if (pray <= 5) {
                    Helpers.typewrite("Update: Asteroid has miraculously diverged its course from the ship. No further action is required.", 60);
                    return 0;
                } else {
                    ship.takeDamage(damage*2);
                    Helpers.typewrite("Alert! Asteroid collision! The ship has taken " + damage*2 + " damage.", 70);
                    return 0;
                }
            } else {
                System.out.println("Input not recognized.");
            }
        }
    }

    /**
     * The radiation encounter. The player can divert course, brace the ship, or pray.
     * @param ship The player's ship.
     * @param input The player's input.
     * @return Any bonus salvage.
     */
    private static int radiationEncounter(Ship ship, Scanner input) {
        Helpers.typewrite("Danger! Radiation storm detected ahead!", 70);
        System.out.println("Choose what to do...");
        int fuelLoss = ThreadLocalRandom.current().nextInt(10, 16);
        int damage = ThreadLocalRandom.current().nextInt(15, 26);
        System.out.println("[1] Divert course (-" + fuelLoss + " fuel)");
        System.out.println("[2] Brace ship (" + damage + " damage)");
        System.out.println("[3] Pray (5% chance of no damage)");
        while (true) {
            int choice = Helpers.tryAns(input);
            if (choice == 1) {
                if (ship.getFuel() < fuelLoss) {
                    Helpers.typewrite("Warning! Insufficient fuel! Course diversion impossible.", 70);
                    Helpers.typewrite("Alert! Radiation damage detected! The ship has taken " + damage + " damage.", 70);
                    ship.takeDamage(damage);
                    return 0;
                }
                ship.setFuel(ship.getFuel() - fuelLoss);
                Helpers.typewrite("Successfully avoided radiation storm. Thank you.", 70);
                return 0;
            } else if (choice == 2) {
                ship.takeDamage(damage);
                Helpers.typewrite("Alert! Radiation damage detected! The ship has taken " + damage + " damage.", 70);
                return 0;
            } else if (choice == 3) {
                int pray = ThreadLocalRandom.current().nextInt(1, 101);
                if (pray <= 5) {
                    Helpers.typewrite("Update: The radiation storm has dissipated unexpectedly. No further action is required.", 80);
                    return 0;
                } else {
                    ship.takeDamage(damage*2);
                    Helpers.typewrite("Alert! Radiation damage detected! The ship has taken " + damage*2 + " damage.", 70);
                    return 0;
                }
            } else {
                System.out.println("Input not recognized.");
            }
        }
    }

    /**
     * The pirate encounter. The player can pay the credit due, refuse, or run.
     * @param ship The player's ship.
     * @param input The player's input.
     * @return Any bonus salvage.
     */
    private static int pirateEncounter(Ship ship, Scanner input) {
        int creditCost = ThreadLocalRandom.current().nextInt(15, 21);
        int damage = ThreadLocalRandom.current().nextInt(20, 31);
        Helpers.typewrite("Warning. Unknown vessels approaching ship.", 70);
        Helpers.typewrite("A message over an unknown radio frequency has been received...", 70);
        Helpers.typewrite("*kzzzrt* Hello. Do not fear us, for all we want are your credits. Transfer us " + creditCost + " credits and we will continue on our course. Insolence will not be tolerated.", 60);
        System.out.println("Choose what to do...");
        System.out.println("[1] Pay credits (-" + creditCost + " credits)");
        System.out.println("[2] Refuse (" + damage + " damage)");
        System.out.println("[3] RUN (50% chance to get away with no damage)");
        while (true) {
            int choice = Helpers.tryAns(input);
            if (choice == 1) {
                Helpers.typewrite("Initiating credit transfer to c00l_p1rate...", 70);
                if (ship.getCredits() < creditCost) {
                    Helpers.typewrite("What!? You don't have enough?", 60);
                    Helpers.typewrite("You know what? I'm feeling generous for the POOR today. I'll leave you be.", 60);
                    Helpers.typewrite("...Signal lost...", 70);
                    return 0;
                }
                Helpers.typewrite("Success. " + creditCost + " credits transferred.", 70);
                ship.setCredits(ship.getCredits()-creditCost);
                
                Helpers.typewrite("*khzzrt* Thank you.", 60);
                return 0;
            } else if (choice == 2) {
                Helpers.typewrite("Ha! Then we will show no mercy.", 60);
                Helpers.typewrite("Warning! Incoming enemy vessel fire! Ship navigation rerouted for escape.", 50);
                Helpers.typewrite("*kzhrt* You can't run!", 60);
                Helpers.typewrite("Wait! Where did you go!? *kzhrrrrrt*.....", 60);
                Helpers.typewrite("Signal lost.", 70);
                Helpers.typewrite("The ship has taken " + damage + " damage.", 70);
                ship.takeDamage(damage);
                return 0;
            } else if (choice == 3) {
                Helpers.typewrite("Hyperdrive engaged. Launching...", 70);
                Helpers.typewrite("Hey! You better not be using your hyperdrive!", 60);
                Helpers.typewrite("Warning! Incoming enemy vessel fire!", 50);
                boolean escape = ThreadLocalRandom.current().nextBoolean();
                if (escape) {
                    Helpers.typewrite("*kzzzhrrrtt...* Warp successful.", 70);
                    return 0;
                } else {
                    Helpers.typewrite("Hyperdrive damaged! Warp cancelled.", 50);
                    Helpers.typewrite("Fool!", 60);
                    Helpers.typewrite("Warning! Incoming missiles! Ship navigation rerouted for escape.", 50);
                    Helpers.typewrite("*kzhrt* You can't run!", 60);
                    Helpers.typewrite("Wait! Where did you go!? *kzhrrrrrt*.....", 60);
                    Helpers.typewrite("Signal lost.", 70);
                    Helpers.typewrite("The ship has taken " + damage*2 + " damage.", 70);
                    ship.takeDamage(damage*2);
                    return 0;
                }
            } else {
                System.out.println("Input not recognized.");
            }
        }
    }

    /**
     * The shield malfunction encounter. Players can either fix it from the inside or outside.
     * @param ship The player's ship.
     * @param input The player's input.
     * @return Any bonus salvage.
     */
    private static int shieldMalfunction(Ship ship, Scanner input) {
        if (ship.getShield() == 0) {
            System.out.println("No anomalies found! Safe travels from here.");
            return 0;
        } else {
            Helpers.typewrite("Warning. External shields malfunctioning.", 70);
            System.out.println("Choose what to do...");
            System.out.println("[1] Attempt to fix shields from inside");
            System.out.println("[2] Tether up and check shields from outside");
            while (true) {
                int choice = Helpers.tryAns(input);
                if (choice == 1) {
                    System.out.println("Surely I can fix up the generators from in here. Probably a software issue.");
                    Helpers.typewrite("...", 300);
                    System.out.println("Well, there is an error in the code here. Let's see if we can fix this...");
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        Helpers.typewrite("Shields functional.", 80);
                        System.out.println("Nice!");
                    } else {
                        Helpers.typewrite("...Shields malfunctioning.", 80);
                        System.out.println("Shoot.");
                        int shieldDamage = Math.max(0, ship.getShield() - 10);
                        ship.setShield(shieldDamage);
                        return 0;
                    }
                    return 0;
                } else if (choice == 2) {
                    System.out.println("Attach that tether... Okay.");
                    Helpers.typewrite("Opening ship latch...", 70);
                    System.out.println("Let's see if I can find this generator.");
                    Helpers.typewrite("...", 300);
                    System.out.println("There it is!");
                    System.out.println("Seems like some space debris is stuck in a gear here.");
                    if (ThreadLocalRandom.current().nextInt(1, 11) < 7) {
                        System.out.println("Got it! Let's get back to the ship.");
                        Helpers.typewrite("...", 300);
                        Helpers.typewrite("Closing hatch...", 70);
                        Helpers.typewrite("Shield functions nominal.", 70);
                    } else {
                        System.out.println("Ouch! It shocked me! Stupid...");
                        System.out.println("OH! Shoot. I let my wrench go. It's... oh it's all the way out there. Okay.");
                        System.out.println("Lets just get back to the ship. I can't do it without my tool.");
                        Helpers.typewrite("...", 150);
                        Helpers.typewrite("Closing hatch...", 70);
                        Helpers.typewrite("Shields malfunctioning.", 70);
                        int shieldDamage = Math.max(0, ship.getShield() - 10);
                        ship.setShield(shieldDamage);
                        return 0;
                    }
                    return 0;
                } else {
                    System.out.println("Input not recognized.");
                }
            }
        }
    }
} 
