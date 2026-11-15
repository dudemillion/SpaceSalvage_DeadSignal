import java.util.Scanner;
public class UpgradeShop {
    private int[] prices;
    private String[] items;

    public UpgradeShop() {
        prices = new int[]{50, 45, 30, 15, 15, 10};
        items = new String[]{"Advanced Scanner", "Reinforced Shields", "Expanded Fuel Tank", "Repair Ship", "Recharge Shields", "Refuel"};
    }

    public int[] getPrices() {
        return this.prices;
    }

    public void setPrices(int[] prices) {
        boolean wasNeg = false;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < 0) {
                System.out.println("Cannot set any prices to negative.");
                wasNeg = true;
                break;
            }
        }
        if (!(prices.length == 6)) {
            System.out.println("6 prices required for upgrade shop.");
        } else if (!wasNeg) {
            this.prices = prices;
        }
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public int openShop(Ship ship, Scanner input) {
        int totalSpent = 0;
        int ans = 999;
        while (ans != 0) {
            System.out.println("======= Welcome to the upgrade shop. =======");
            System.out.println("Credit balance: " + ship.getCredits());
            System.out.println();
            System.out.println("PERMANENT UPGRADES");
            System.out.println("[1] Advanced Scanner  - " + prices[0]);
            System.out.println("[2] Reinforced Shields  - " + prices[1]);
            System.out.println("[3] Expanded Fuel Tank - " + prices[2]);
            System.out.println();
            System.out.println("MAINTENANCE");
            System.out.println("[4] Repair Ship (+20) - " + prices[3]);
            System.out.println("[5] Recharge Shields (+20) - " + prices[4]);
            System.out.println("[6] Refuel (+15) - " + prices[5]);
            System.out.println();
            System.out.println("[0] Return to menu.");
            ans = Helpers.tryAns(input);
            switch (ans) {
                case 0: System.out.println("Exiting shop..."); break;
                case 1: totalSpent += purchase(ship, 1, input); break;
                case 2: totalSpent += purchase(ship, 2, input); break;
                case 3: totalSpent += purchase(ship, 3, input); break;
                case 4: totalSpent += purchase(ship, 4, input); break;
                case 5: totalSpent += purchase(ship, 5, input); break;
                case 6: totalSpent += purchase(ship, 6, input); break;
                default: System.out.println("Unrecognized number.");
            }
        }
        return totalSpent;
    }


    public int purchase(Ship ship, int choice, Scanner input) {
        System.out.println("You chose to purchase " + items[choice-1] + " for " + prices[choice-1] + " credits. Confirm? (1(Yes)/0(No))");
        int ans = Helpers.tryAns(input);
        if (ans == 1) {
            Helpers.typewrite("Purchasing...", 60);
            if (ship.getCredits() < prices[choice-1]) {
                Helpers.typewrite("Error. Insufficient credits for purchase.", 60);
            } else {
                if (choice == 1 || choice == 2 || choice == 3) {
                    for (String upgrade : ship.getUpgrades()) {
                        if (items[choice-1].equals(upgrade)) {
                            System.out.println("You already own that upgrade.");
                            return 0;
                        }
                    }
                    ship.setCredits(ship.getCredits() - prices[choice-1]);
                    String[] updated = new String[ship.getUpgrades().length+1];
                    for (int i = 0; i < ship.getUpgrades().length; i++) {
                        updated[i] = ship.getUpgrades()[i];
                    }
                    updated[updated.length-1] = items[choice-1];
                    ship.setUpgrades(updated);
                    Helpers.typewrite("Success!", 60);
                    if (choice == 2) {
                        ship.setMaxShield(75);
                    } else if (choice == 3) {
                        ship.setMaxFuel(150.0);
                    }
                    return prices[choice-1];
                } else {
                    if (choice == 4) {
                        if (ship.getHull() == ship.getMaxHull()) {
                            System.out.println("Your ship is undamaged.");
                            return 0;
                        }
                        ship.setCredits(ship.getCredits() - prices[choice-1]);
                        ship.repair(20);
                        System.out.println("Ship repaired. Hull integrity at " + ship.getHull() + "/" + ship.getMaxHull() + ", and shields are at " + ship.getShield() + "/" + ship.getMaxShield());
                    } else if (choice == 5) { 
                        if (ship.getShield() == ship.getMaxShield()) {
                            System.out.println("Your shields are at max charge.");
                            return 0;
                        }
                        ship.setCredits(ship.getCredits() - prices[choice-1]);
                        ship.setShield(Math.min(ship.getMaxShield(), ship.getShield() + 20));
                        System.out.println("Successfully recharged shields.");
                    } else if (choice == 6) {
                        if (ship.getFuel() == ship.getMaxFuel()) {
                            System.out.println("Your ship's fuel tanks are full.");
                            return 0;
                        }
                        ship.setCredits(ship.getCredits() - prices[choice-1]);
                        ship.refuel(15);
                        System.out.println("Ship refueled. Fuel tanks are now at " + ship.getFuel() + "/" + ship.getMaxFuel());
                    } else {
                        System.out.println("Error.");
                    }
                    return prices[choice-1];
                }
            } 
        } else if (ans == 0) {
            System.out.println("Returned to shop menu.");
        } else {
            System.out.println("Unrecognized number.");
        }
        return 0;
    }

}
