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

    public void resolveHazard(Ship ship, Scanner input) {
        int chance = (int)(Math.random() * 5);
        System.out.print("Ship: ");
        Helpers.typewrite("Searching for anomalies...", 100);
        if (chance >= 0 && chance < 1) {
            System.out.println("No anomalies found! Safe travels from here.");
        } else if (chance >= 1 && chance < 2) {
            asteroidEncounter(ship, input);
        } else if (chance >= 2 && chance < 3) {
            radiationEncounter(ship, input);
        } else if (chance >= 3 && chance < 4) {
            pirateEncounter(ship, input);
        } else {
            shieldMalfunction(ship, input);
        }
    }
    private static void asteroidEncounter(Ship ship, Scanner input) {
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
                    break;
                } else {
                    ship.setFuel(ship.getFuel() - fuelLoss);
                    Helpers.typewrite("Successfully diverted course. Thank you.", 70);
                    break;
                }   
            } else if (choice == 2) {
                ship.takeDamage(damage);
                Helpers.typewrite("Alert! Asteroid collision! The ship has taken " + damage + " damage.", 70);
                break;
            } else if (choice == 3) {
                int pray = ThreadLocalRandom.current().nextInt(1, 101);
                if (pray <= 5) {
                    Helpers.typewrite("Update: Asteroid has miraculously diverged its course from the ship. No further action is required.", 60);
                    break;
                } else {
                    ship.takeDamage(damage*2);
                    Helpers.typewrite("Alert! Asteroid collision! The ship has taken " + damage*2 + " damage.", 70);
                    break;
                }
            } else {
                System.out.println("Input not recognized.");
            }
        }
    }
    private static void radiationEncounter(Ship ship, Scanner input) {
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
                    break;
                }
                ship.setFuel(ship.getFuel() - fuelLoss);
                Helpers.typewrite("Successfully avoided radiation storm. Thank you.", 70);
                break;
            } else if (choice == 2) {
                ship.takeDamage(damage);
                Helpers.typewrite("Alert! Radiation damage detected! The ship has taken " + damage + " damage.", 70);
                break;
            } else if (choice == 3) {
                int pray = ThreadLocalRandom.current().nextInt(1, 101);
                if (pray <= 5) {
                    Helpers.typewrite("Update: The radiation storm has dissipated unexpectedly. No further action is required.", 80);
                    break;
                } else {
                    ship.takeDamage(damage*2);
                    Helpers.typewrite("Alert! Radiation damage detected! The ship has taken " + damage*2 + " damage.", 70);
                    break;
                }
            } else {
                System.out.println("Input not recognized.");
            }
        }
    }

    private static void pirateEncounter(Ship ship, Scanner input) {
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
                    break;
                }
                Helpers.typewrite("Success. " + creditCost + " credits transferred.", 70);
                ship.setCredits(ship.getCredits()-creditCost);
                Helpers.typewrite("*khzzrt* Thank you.", 60);
                break;
            } else if (choice == 2) {
                Helpers.typewrite("Ha! Then we will show no mercy.", 60);
                Helpers.typewrite("Warning! Incoming enemy vessel fire! Ship navigation rerouted for escape.", 50);
                Helpers.typewrite("*kzhrt* You can't run!", 60);
                Helpers.typewrite("Wait! Where did you go!? *kzhrrrrrt*.....", 60);
                Helpers.typewrite("Signal lost.", 70);
                Helpers.typewrite("The ship has taken " + damage + " damage.", 70);
                ship.takeDamage(damage);
                break;
            } else if (choice == 3) {
                Helpers.typewrite("Hyperdrive engaged. Launching...", 70);
                Helpers.typewrite("Hey! You better not be using your hyperdrive!", 60);
                Helpers.typewrite("Warning! Incoming enemy vessel fire!", 50);
                int escape = ThreadLocalRandom.current().nextInt(1, 3);
                if (escape == 1) {
                    Helpers.typewrite("*kzzzhrrrtt...* Warp successful.", 70);
                    break;
                } else {
                    Helpers.typewrite("Hyperdrive damaged! Warp cancelled.", 50);
                    Helpers.typewrite("Fool!", 60);
                    Helpers.typewrite("Warning! Incoming missiles! Ship navigation rerouted for escape.", 50);
                    Helpers.typewrite("*kzhrt* You can't run!", 60);
                    Helpers.typewrite("Wait! Where did you go!? *kzhrrrrrt*.....", 60);
                    Helpers.typewrite("Signal lost.", 70);
                    Helpers.typewrite("The ship has taken " + damage*2 + " damage.", 70);
                    ship.takeDamage(damage*2);
                    break;
                }
            } else {
                System.out.println("Input not recognized.");
            }
        }
    }

    private static void shieldMalfunction(Ship ship, Scanner input) {
        if (ship.getShield() == 0) {
            System.out.println("No anomalies found! Safe travels from here.");
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
                    if (ThreadLocalRandom.current().nextInt(1, 3) == 1) {
                        Helpers.typewrite("Shields functional.", 80);
                        System.out.println("Nice!");
                    } else {
                        Helpers.typewrite("...Shields malfunctioning.", 80);
                        System.out.println("Shoot.");
                        ship.setShield(Math.max(0, ship.getShield() - 10));
                    }
                    break;
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
                        ship.setShield(Math.max(0, ship.getShield() - 10));
                    }
                    break;
                } else {
                    System.out.println("Input not recognized.");
                }
            }
        }
    }
} 
