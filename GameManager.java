import java.util.ArrayList;
import java.util.Scanner;
public class GameManager {
    private Ship ship;
    private ArrayList<ExpeditionResult> expeditionHistory;
    private int totalSpent;
    private int successfulExpeditions;
    private String saveName;

    public GameManager() {
        ship = new Ship(100, 50, 100.0, 10, 100, 50, 100, new String[0]);
        expeditionHistory = new ArrayList<ExpeditionResult>();
        totalSpent = 0;
        successfulExpeditions = 0;
        saveName = "Placeholder";
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public void startGame() {
        Scanner input = new Scanner(System.in);
        Helpers.typewrite("Welcome to Space Salvage: Dead Signal.", 80);
        System.out.println("Would you like to load a save? (1(Yes)/0(No))");
        while (true) {
            int ans = Helpers.tryAns(input);
            if (ans == 1) {
                // SaveManager.loadGame(); // Dummy code until SaveManager is written, unknown if static or not
                System.out.println("Not implemented.");
            } else if (ans == 0) {
                System.out.println("Name your save.");
                boolean sure = false;
                do {
                    input.nextLine();
                    saveName = input.nextLine();
                    System.out.println("You named the save '" + saveName + "'. Is this okay? (1(Yes)/0(No))");
                    int ans2 = Helpers.tryAns(input);
                    if (ans2 == 0) {
                        System.out.println("Please enter a new name.");
                    } else if (!(ans2 == 1 || ans2 == 0)) {
                        System.out.println("Unrecognized number.");
                    } else {
                        sure = true; 
                    }
                } while(!sure);
                UpgradeShop shop = new UpgradeShop();
                Expedition[] expeditions = new Expedition[5];
                expeditions[0] = new Expedition(1, "Local Asteroid System", 0, 3);
                expeditions[1] = new Expedition(5, "Asteroid Belt", 5, 10);
                expeditions[2] = new Expedition(8, "Derelict Freighter", 8, 15);
                expeditions[3] = new DangerousExpedition(10, "Ship Graveyard [DANGEROUS]", 15, 25, 1, 5);
                expeditions[4] = new DangerousExpedition(15, "Alien Ruins [DANGEROUS]", 25, 40, 2, 7);

                openMenu(input, expeditions, shop);
            } else {
                System.out.println("Unrecognized input.");
            }
        }
    }

    public void openMenu(Scanner input, Expedition[] expeditions, UpgradeShop shop) {
        while (true) {
            System.out.println("======= Menu =======");
            System.out.println();
            System.out.println("[1] View available expeditions");
            System.out.println("[2] View ship status");
            System.out.println("[3] View shop");
            System.out.println("[4] View current game statistics");
            System.out.println("[5] Save game");
            System.out.println("[6] Help");
            System.out.println("[7] Exit");
            int ans = Helpers.tryAns(input);
            if (ans == 1) {
                System.out.println("======= Expeditions =======");
                System.out.println("Select an expedition to go on. Higher risk constitutes a higher reward.");
                for (int i = 0; i<expeditions.length; i++) {
                    System.out.println();
                    System.out.println("[" + (i+1) + "] " + expeditions[i].getName() + " (Credit cost: " + expeditions[i].getCredCost() + " Fuel cost: " + expeditions[i].getFuelCost() + ")");
                }
                System.out.println();
                System.out.println("[5] Return to menu");
                while (true) {
                    int expAns = Helpers.tryAns(input);
                    if (expAns == 1 || expAns == 2 || expAns == 3 || expAns == 4 || expAns == 5) {
                        startExpedition(expeditions[expAns-1], input);
                        break;
                    } else if (expAns == 6) {
                        break;
                    } else {
                        System.out.println("Unrecognized number.");
                    }
                }
            } else if (ans == 2) {
                showShipStats(input);
            } else if (ans == 3) {
                totalSpent += shop.openShop(ship, input);
            } else if (ans == 4) {
                showStats(input);
            } else if (ans == 5) {
                // save logic when SaveManager is implemented. Same as the answer 6 save logic.
            } else if (ans == 6) {
                help(input);
            } else if (ans == 7) {
                System.out.println("Would you like to save before you exit? (1(Yes)/0(No))");
                while (true) {
                    int saveAns = Helpers.tryAns(input);
                    if (saveAns == 1) {
                        // write this save code when SaveManager is made
                        System.out.println("Thank you for playing Space Salvage: Dead Signal.");
                        System.exit(0);
                    } else if (saveAns == 0) {
                        System.out.println("Thank you for playing Space Salvage: Dead Signal.");
                        System.exit(0);
                    } else {
                        System.out.println("Unrecognized number.");
                    }
                }
            }
        }
    }

    public void startExpedition(Expedition expedition, Scanner input) {
        if (ship.getCredits() < expedition.getCredCost()) {
            System.out.println("Insufficient credits to go on expedition.");
        } else if (ship.getFuel() < expedition.getFuelCost()) {
            System.out.println("Insufficient fuel to go on expedition.");
        } else {
            totalSpent += expedition.getCredCost();
            int salvage = 0;
            int matches = 0;
            int damageTaken = 0;
            boolean continuing = true;
            FreqScanner scanner = new FreqScanner();
            ship.setCredits(ship.getCredits() - expedition.getCredCost());
            ship.setFuel(ship.getFuel() - expedition.getFuelCost());
            Helpers.typewrite("You enter your ship, ready for this expedition.", 70);
            Helpers.typewrite("The station aligns your trajectory towards the " + expedition.getName() + " and you await clearance.", 70);
            Helpers.typewrite("...", 250);
            Helpers.typewrite("*kzzrt* 'You are clear to launch, employee. Do not let us down.'", 60);
            Helpers.typewrite("You engage the hyperdrive, and not long after, your ship launches into space.", 70);
            Helpers.typewrite("...", 250);
            Helpers.typewrite("You arrive at the " + expedition.getName() + ". You start up the frequency scanner in search of viable salvage.", 70);
            while (continuing) {
                int matched = scanner.generateSignal(ship, input);
                double mult = 0.0;
                matches += matched;
                if (matched > 0) {
                    switch (matched) {
                        case 1: mult = 1.2; break;
                        case 2: mult = 1.5; break;
                        case 3: mult = 2.0; break;
                        case 4: mult = 2.5; break;
                        case 5: mult = 3.0; break;
                        default: System.out.println("Error.");
                    }
                    salvage += (expedition.getBaseReward() * mult);
                    Helpers.typewrite("Found some salvage. Now...", 70);
                } else {
                    Helpers.typewrite("'Didn't find any salvage. Could this go any worse!?'", 70);
                }
                Helpers.typewrite("Let's just see if there'll be any problems ahead.", 70);
                int beforeHull = ship.getHull();
                int beforeShield = ship.getShield();
                int beforeCredits = ship.getCredits();
                salvage += expedition.resolveHazard(ship, input);
                damageTaken += beforeHull - ship.getHull();
                damageTaken += beforeShield - ship.getShield();
                totalSpent += beforeCredits - ship.getCredits();
                if (ship.getHull() == 0) {
                    salvage = 0;
                    rescueShip(expedition, salvage, matches, damageTaken);
                    break;
                } else {
                    System.out.println("Continue expedition or extract? (1(Continue)/0(Extract))");
                    while (true) {
                        int ans = Helpers.tryAns(input);
                        if (ans == 1) {
                            if (ship.getFuel() < expedition.getFuelCost()) {
                                System.out.println("You cannot continue. Extraction is required.");
                                extract(expedition, salvage, matches, damageTaken);
                                continuing = false;
                                break;
                            } else {
                                ship.setFuel(ship.getFuel() - expedition.getFuelCost());
                                System.out.println("You continue in search of more salvage, re-activating the frequency scanner.");
                                break;
                            }
                        } else if (ans == 0) {
                            extract(expedition, salvage, matches, damageTaken);
                            continuing = false;
                            break;
                        } else {
                            System.out.println("Unrecognized number.");
                        }
                    }
                }
            }
        }
    }

    public void showShipStats(Scanner input) {
        System.out.println("======= Ship Status =======");
        System.out.println("Hull: " + ship.getHull() + "/" + ship.getMaxHull());
        System.out.println("Shields: " + ship.getShield() + "/" + ship.getMaxShield());
        System.out.println("Fuel: " + ship.getFuel() + "/" + ship.getMaxFuel());
        System.out.println("Credits: " + ship.getCredits());
        System.out.println("Upgrades: ");
        for (int i = 0; i < ship.getUpgrades().length; i++) {
            System.out.print(ship.getUpgrades()[i] + " ");
        }
        System.out.println();
        System.out.println("Enter any key to return to menu...");
        input.next();
    }

    public void showStats(Scanner input) {
        int totalDamage = 0;
        int totalMatches = 0;
        int totalCredits = 0;
        System.out.println("======= Past Expeditions =======");
        for (int i = 0; i <expeditionHistory.size(); i++) {
            totalDamage += expeditionHistory.get(i).getDamageTaken();
            totalMatches += expeditionHistory.get(i).getMatches();
            totalCredits += expeditionHistory.get(i).getReward();
            System.out.println();
            System.out.println(expeditionHistory.get(i).getExpeditionName() + ": Credits gained: " + expeditionHistory.get(i).getReward() + " Matches made: " + expeditionHistory.get(i).getMatches() + " Damage taken: " + expeditionHistory.get(i).getDamageTaken());
        }
        System.out.println();
        System.out.println("====== Total Statistics =======");
        System.out.println("Total credits gained: " + totalCredits);
        System.out.println("Total credits spent: " + totalSpent);
        System.out.println("Total matches made: " + totalMatches);
        System.out.println("Total damage taken: " + totalDamage);
        System.out.println("You have had " + successfulExpeditions + " successful expeditions.");
        System.out.println("You've went on a total of " + expeditionHistory.size() + " expeditions.");
        System.out.println("You've had a net profit of " + (totalCredits-totalSpent) + " credits.");
        double successRate = (double)(successfulExpeditions) / (double)(expeditionHistory.size());
        if (Double.isNaN(successRate)) {
            successRate = 0.0;
        }
        System.out.println("Your expedition success rate is " + successRate + "%.");
    }

    public void rescueShip(Expedition expedition, int salvage, int matches, int damageTaken) {
        Helpers.typewrite("...", 300);
        Helpers.typewrite("'EMERGENCY RESCUE INITIATED. DEDUCTING CREDITS...'", 60);
        if (ship.getCredits() < 25) {
            Helpers.typewrite("'INSUFFICIENT CREDITS. EMPLOYEE IS DEEMED A LIABILITY. GOODBYE.'", 60);
            System.out.println("Game over.");
            System.exit(0);
        } else {
            ship.setCredits(ship.getCredits() - 25);
            totalSpent += 25;
            Helpers.typewrite("'SUCCESSFUL. PROTOCOL ENGAGED.'", 60);
            System.out.println("An emergency rescue vessel approaches, and takes you and the ship back to the station.");
            Helpers.typewrite("...", 250);
            Helpers.typewrite("You arrive back at the station, and your ship is immediately taken into the shop for repairs.", 70);
            Helpers.typewrite("A very angry HR representative approaches you.", 70);
            Helpers.typewrite("'Disappointing performance employee. We expected better.'", 60);
            Helpers.typewrite("They shove paperwork into your hands.", 70);
            Helpers.typewrite("'Fill this out. The company requires it. All the statistics.'", 60);
            Helpers.typewrite("*You spend the next few minutes filling out the fields...*", 70);
            ExpeditionResult result = new ExpeditionResult(expedition.getName(), matches, salvage, damageTaken, false);
            expeditionHistory.add(result);
            Helpers.typewrite("It's all written down. You hand it back to the HR representative.", 70);
            Helpers.typewrite("'Do better.'", 70);
            Helpers.typewrite("Can they ever give us a break?", 70);
            ship.setHull(50);
            ship.setShield(10);
            ship.setFuel(15);
        }
    }

    public void extract(Expedition expedition, int salvage, int matches, int damageTaken) {
        successfulExpeditions++;
        Helpers.typewrite("You turn the ship back towards the station, and start returning, satisfied with your expedition.", 70);
        Helpers.typewrite("...", 250);
        Helpers.typewrite("You approach the docking station, and let the ship do the work.", 70);
        Helpers.typewrite("*kzzzrt* 'Welcome back employee. Good work on a successful expedition.'", 60);
        Helpers.typewrite("You exit the ship, and immediately, paperwork is shoved into your hand.", 70);
        Helpers.typewrite("'Fill this out. The company requires it. All the statistics.'", 60);
        Helpers.typewrite("*You spend the next few minutes filling out the fields...*", 70);
        ExpeditionResult result = new ExpeditionResult(expedition.getName(), matches, salvage, damageTaken, true);
        ship.setCredits(ship.getCredits() + salvage);
        expeditionHistory.add(result);
        Helpers.typewrite("It's all written down. You hand it back to the HR representative.", 70);
        Helpers.typewrite("'Thank you.'", 70);
        Helpers.typewrite("They're always so inhuman.", 70);
    }

    public void help(Scanner input) {
        System.out.println("======= INFORMATION =======");
        System.out.println();
        System.out.println("OBJECTIVE");
        System.out.println("You are the captain of a salvage ship exploring dangerous regions of space. Complete expeditions, collect salvage, earn credits, and upgrade your ship to survive increasingly dangerous missions.");
        System.out.println();
        System.out.println("EXPEDITIONS");
        System.out.println("Each expedition costs credits and fuel. More dangerous locations offer greater rewards but expose your ship to stronger hazards.");
        System.out.println();
        System.out.println("FREQUENCY SCANNING");
        System.out.println("During expeditions, you must guess five frequencies (1–5). Some positions are revealed to help you. The more frequencies you match in the correct positions, the higher your reward multiplier (up to 3×).");
        System.out.println();
        System.out.println("HAZARDS");
        System.out.println("You may encounter asteroids, radiation storms, pirates, and other dangers. Choose your actions carefully! Your shields absorb damage before your hull, and some choices consume fuel or credits.");
        System.out.println();
        System.out.println("EXTRACTION");
        System.out.println("After each encounter, you can return to the station and secure your salvage or continue exploring for more rewards. If your ship is destroyed, all unbanked salvage is lost.");
        System.out.println();
        System.out.println("RESCUE");
        System.out.println("If your hull reaches zero, emergency rescue activates. Rescue costs 25 credits and restores limited ship resources. If you cannot afford rescue, your run ends.");
        System.out.println();
        System.out.println("UPGRADES & REPAIRS");
        System.out.println("Spend credits at the Upgrade Shop to improve your scanner, shield capacity, and fuel tank. You can also repair your hull, recharge shields, and refuel.");
        System.out.println();
        System.out.println("SAVING");
        System.out.println("Save your progress to preserve your ship, upgrades, and expedition history. You can also view your past expeditions and total statistics.");
        System.out.println();
        System.out.println("Good luck, Captain. The company expects results.");
        System.out.println("Enter any key to return to menu...");
        input.next();
    }
}
