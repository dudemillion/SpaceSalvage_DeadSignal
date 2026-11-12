import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class DangerousExpedition extends Expedition {
    private int hazardLevel;
    private int bonusDamage;

    public DangerousExpedition(double fuelCost, String name, int credCost, int baseReward, int hazardLevel, int bonusDamage) {
        super(fuelCost, name, credCost, baseReward);
        this.hazardLevel = hazardLevel;
        this.bonusDamage = bonusDamage;
    }

    public int getHazardLevel() {
        return this.hazardLevel;
    }

    public int getBonusDamage() {
        return this.bonusDamage;
    }

    public void setHazardLevel(int hazardLevel) {
        if (hazardLevel < 0) {
            System.out.println("Hazard level cannot be negative.");
        } else {
            this.hazardLevel = hazardLevel;
        }
    }

    public void setBonusDamage(int bonusDamage) {
        if (bonusDamage < 0) {
            System.out.println("Bonus damage cannot be negative.");
        } else {
            this.bonusDamage = bonusDamage;
        }
    }

    public static int calculateHazardDamage(int baseDamage, int hazardLevel, int bonusDamage) {
        int damage = baseDamage + (hazardLevel * bonusDamage);
        return damage;
    }

    @Override 
    public int resolveHazard(Ship ship, Scanner input) {
        int random = ThreadLocalRandom.current().nextInt(0, 5);
        if (random == 0 || random == 1) {
            return super.resolveHazard(ship, input);
        } else if (random == 2 || random == 3) {
            electromagneticStorm(ship, input, this.hazardLevel, this.bonusDamage);
            return 0;
        } else if (random == 4) {
            return unstableReactor(ship, input, this.hazardLevel, this.bonusDamage);
        }
        return 0;
    }

    private static void electromagneticStorm(Ship ship, Scanner input, int hazardLevel, int bonusDamage) {
        Helpers.typewrite("Warning. Electromagnetic storm detected ahead.", 70);
        Helpers.typewrite("Systems malfunctioning. Immediate action req", 70);
        Helpers.typewrite("uirrred...", 700);
        System.out.println("Choose what to do...");
        int fuelLoss = ThreadLocalRandom.current().nextInt(30, 45);
        int damage = ThreadLocalRandom.current().nextInt(15, 20);
        System.out.println("[1] E4gag$ #ypErdriv3 (-" + fuelLoss + " Fuel)");
        System.out.println("[2] Shut d0Wn & dR1ft 7hRough th3 2t0rM (" + calculateHazardDamage(damage, hazardLevel, bonusDamage) + " damage");
        while (true) {
            int choice = Helpers.tryAns(input);
            if (choice == 1) {
                if (ship.getFuel() < fuelLoss) {
                    ship.takeDamage(calculateHazardDamage(damage, hazardLevel, bonusDamage));
                    Helpers.typewrite("H-Hyperdriiive unavaaili-b-ble.  Insu*kzrt*fficIent FUEl", 200);
                    Helpers.typewrite("Sys..tems shu-t-ting dooown...", 250);
                    Helpers.typewrite("...", 300);
                    System.out.println("...okay. Welp, Gotta hope the ship can take this damage.");
                    Helpers.typewrite("*You hear the ship whining as it pushes through the storm.*", 70);
                    Helpers.typewrite("...", 300);
                    System.out.println("I think that was it? Let's restart the system.");
                    Helpers.typewrite("...", 300);
                    if (ship.getHull() > 0) {
                        Helpers.typewrite("*A chime plays as the system's computers come back to life.", 70);
                        Helpers.typewrite("Performing diagnostic...", 100);
                        Helpers.typewrite("...", 300);
                        Helpers.typewrite("Warning! The ship has taken " + calculateHazardDamage(damage, hazardLevel, bonusDamage) + " damage!", 70);
                        System.out.println("I know that much...");
                    } else {
                        Helpers.typewrite("..?", 250);
                        System.out.println("The systems remain powered off.. It seems it is using it's emergency reserve to keep the ship from exploding.");
                    }
                    break;
                }
                ship.setFuel(ship.getFuel() - fuelLoss);
                Helpers.typewrite("Eng..aging Hy-yper*kzzrt*drive", 200);
                Helpers.typewrite("...", 300);
                System.out.println("You feel yourself sink back into your seat as the hyperdrive engages successfully.");
                Helpers.typewrite("      ", 100);
                Helpers.typewrite("Ship functions restored. Warp successful.", 70);
                break;
            } else if (choice == 2) {
                ship.takeDamage(calculateHazardDamage(damage, hazardLevel, bonusDamage));
                Helpers.typewrite("Sys..tems shu-t-ting dooown...", 250);
                Helpers.typewrite("...", 300);
                System.out.println("...okay. Gotta hope the ship can take this damage.");
                Helpers.typewrite("*You hear the ship whining as it pushes through the storm.*", 70);
                Helpers.typewrite("...", 300);
                System.out.println("I think that was it? Let's restart the system.");
                Helpers.typewrite("...", 300);
                if (ship.getHull() > 0) {
                    Helpers.typewrite("*A chime plays as the system's computers come back to life.", 70);
                    Helpers.typewrite("Performing diagnostic...", 100);
                    Helpers.typewrite("...", 300);
                    Helpers.typewrite("Warning! The ship has taken " + calculateHazardDamage(damage, hazardLevel, bonusDamage) + " damage!", 70);
                    System.out.println("I know that much...");
                } else {
                    Helpers.typewrite("..?", 250);
                    System.out.println("The systems remain powered off.. It seems it is using it's emergency reserve power to keep the ship from exploding.");
                }
                break;
            } else {
                System.out.println("Input not recognized.");
            }
        }
    }

    private static int unstableReactor(Ship ship, Scanner input, int hazardLevel, int bonusDamage) {
        Helpers.typewrite("Warning. Ship approaching dangerously critical vessel.", 70);
        Helpers.typewrite("Further analysis reveals that the reactor of the vessel has gone unstable.", 70);
        Helpers.typewrite("The ship cannot bear the damage of a reactor explosion. Immediate action required.", 70);
        System.out.println("Choose what to do...");
        int fuelLoss = ThreadLocalRandom.current().nextInt(30, 45);
        int salvageDamage = 999;
        int damage = ThreadLocalRandom.current().nextInt(20, 25);
        System.out.println("[1] Retreat (-" + fuelLoss + " Fuel)");
        System.out.println("[2] Attempt Salvage (40% chance of success, 60% chance of ship destruction)");
        while (true) {
            int choice = Helpers.tryAns(input);
            if (choice == 1) {
                if (ship.getFuel() < fuelLoss) {
                    Helpers.typewrite("Warning. Insufficient fuel to divert course efficiently.", 70);
                    Helpers.typewrite("Attempting to divert without fuel...", 70);
                    Helpers.typewrite("Warning! Vessel is going critical in range!", 70);
                    Helpers.typewrite("...", 70);
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        Helpers.typewrite("Successfully escaped radi", 65);
                        System.out.println("*BOOM*");
                        Helpers.typewrite("...us", 80);
                    } else {
                        Helpers.typewrite("Warning! Ship is within", 65);
                        System.out.println("*BOOM*");
                        ship.takeDamage(calculateHazardDamage(damage, hazardLevel, bonusDamage));
                        Helpers.typewrite("...looks like that knocked out the systems.", 70);
                        Helpers.typewrite("...", 200);
                        if (ship.getHull() > 0) {
                            Helpers.typewrite("*A chime plays as the system's computers come back to life.", 70);
                            Helpers.typewrite("Performing diagnostic...", 100);
                            Helpers.typewrite("...", 300);
                            Helpers.typewrite("Warning! The ship has taken " + calculateHazardDamage(damage, hazardLevel, bonusDamage) + " damage!", 70);
                            System.out.println("Oh well.");
                        } else {
                            Helpers.typewrite("..?", 250);
                            System.out.println("The systems remain powered off.. It seems it is using it's emergency reserve power to keep the ship from exploding.");
                        }
                    }
                    return 0;
                }
                ship.setFuel(ship.getFuel() - fuelLoss);
                Helpers.typewrite("Diverting course.", 70);
                Helpers.typewrite("*You look off to your side as you pass the vessel. It looks fine to you, but you suppose that not all unstable reactors are that obvious.*", 65);
                Helpers.typewrite("...", 300);
                Helpers.typewrite("Reactor explosion danger radius escaped.", 70);
                return 0;
            } else if (choice == 2) {
                System.out.println("Maybe I can take advantage of this. This would be good salvage.");
                Helpers.typewrite("Warning! Approaching unstable vessel! Whatever you're doing is not worth it!", 70);
                System.out.println("Silence machine. I'm talking. Listen and learn.");
                System.out.println("Engage the claw.");
                Helpers.typewrite("Extraction claw engaged.", 70);
                Helpers.typewrite("We can definitely take some off of this ship before it explodes.", 70);
                Helpers.typewrite("...", 300);
                if (ThreadLocalRandom.current().nextInt(1,11) >= 7) {
                    System.out.println("Ha! Got it! The economy will be flourishing!");
                    Helpers.typewrite("Get us out of here computer.", 70);
                    Helpers.typewrite("Engaging thrusters. Vessel instability almost critical.", 70);
                    Helpers.typewrite("...", 300);
                    Helpers.typewrite("Successfully escaped radi", 65);
                    System.out.println("*BOOM*");
                    Helpers.typewrite("...us", 80);
                    return 50;
                } else {
                    System.out.println("*BOOM*");
                    ship.takeDamage(salvageDamage);
                    Helpers.typewrite("...", 600);
                    System.out.println("The unstable vessel exploded during salvage extraction. The ship is obliterated.");
                    return 0;
                }
            } else {
                System.out.println("Input not recognized.");
            }
        }
    }
}
