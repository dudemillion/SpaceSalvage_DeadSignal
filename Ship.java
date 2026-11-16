/**
 * The player's ship.
 */
public class Ship {
    private int hull;
    private int shield;
    private double fuel;
    private int credits;
    private int maxHull;
    private int maxShield;
    private double maxFuel;
    private String[] upgrades;

    /**
     * Creates a new ship for the player to use.
     * @param hull The hull integrity.
     * @param shield The shield battery.
     * @param fuel The fuel remaining.
     * @param credits The credits the player has.
     * @param maxHull The maximum hull integrity.
     * @param maxShield The maximum shield charge.
     * @param maxFuel The maximum fuel the ship can contain.
     * @param upgrades The upgrades the player has.
     */
    public Ship(int hull, int shield, double fuel, int credits, int maxHull, int maxShield, int maxFuel, String[] upgrades) {
        this.hull = hull;
        this.shield = shield;
        this.fuel = fuel;
        this.credits = credits;
        this.maxHull = maxHull;
        this.maxShield = maxShield;
        this.maxFuel = maxFuel;
        this.upgrades = upgrades;
    }
    /**
     * Gets the hull integrity.
     * @return The hull integrity.
     */
    public int getHull() {
        return this.hull;
    }

    /**
     * Gets the maximum hull integrity.
     * @return The maximum hull integrity.
     */
    public int getMaxHull() {
        return this.maxHull;
    }

    /**
     * Sets the hull integrity. Rejects invalid values.
     * @param hull The new integrity.
     */
    public void setHull(int hull) {
        if (hull < 0) {
            System.out.println("Cannot set hull to negative.");
        } else if (hull > maxHull) {
            System.out.println("Cannot set hull integrity above max hull integrity.");
        } else {
            this.hull = hull;
        }
    }
    /**
     * Sets the maximum hull integrity. Validates new maximum.
     * @param max The new maximum.
     */
    public void setMaxHull(int max) {
        if (max <= 0) {
            System.out.println("Cannot set max hull integrity to negative or zero.");
        } else {
            this.maxHull = max;
        }
    }

    /**
     * Gets the shield's charge.
     * @return The shield's charge.
     */
    public int getShield() {
        return this.shield;
    }

    /**
     * Gets the maximum shield charge.
     * @return The maximum shield charge.
     */
    public int getMaxShield() {
        return this.maxShield;
    }

    /**
     * Sets the maximum shield charge. Validates values.
     * @param max The new maximum.
     */
    public void setMaxShield(int max) {
        if (max <= 0) {
            System.out.println("Cannot set max shield battery to negative or zero.");
        } else {
            maxShield = max;
        }
    }

    /**
     * Sets the shield charge. Validates values.
     * @param shield The new shield charge.
     */
    public void setShield(int shield) {
        if (shield < 0) {
            System.out.println("Cannot set shield to negative charge.");
        } else if (shield > maxShield) {
            System.out.println("Cannot set shield battery above maximum charge.");
        } else {
            this.shield = shield;
        }
    }

    /**
     * Gets the fuel remaining in the ship.
     * @return The remaining fuel.
     */
    public double getFuel() {
        return this.fuel;
    }

    /**
     * Gets the maximum fuel the ship can contain.
     * @return The maximum fuel.
     */
    public double getMaxFuel() {
        return this.maxFuel;
    }

    /**
     * Sets the fuel in the ship. Validates values.
     * @param fuel The new fuel level.
     */
    public void setFuel(double fuel) {
        if (fuel < 0) {
            System.out.println("Cannot set fuel to negative.");
        } else if (fuel > maxFuel) {
            System.out.println("Cannot set fuel amount above fuel tank capacity.");
        } else {
            this.fuel = fuel;
        }
    }

    /**
     * Sets the maximum amount of fuel the ship can contain. Validates values.
     * @param max The new maximum.ff
     */
    public void setMaxFuel(double max) {
        if (max <= 0) {
            System.out.println("Cannot set maximum fuel capacity to negative.");
        } else {
            maxFuel = max;
        }
    }

    /**
     * Sets the credits the ship has.
     * @return The credits the ship has.
     */
    public int getCredits() {
        return this.credits;
    }

    /**
     * Sets the credits that the ship has. Validates values.
     * @param credits The new credit balance.
     */
    public void setCredits(int credits) {
        if (credits < 0) {
            System.out.println("Cannot set credit balance to negative.");
        } else {
            this.credits = credits;
        }
    }

    /**
     * Gets the upgrades in the ship.
     * @return The upgrades in the ship.
     */
    public String[] getUpgrades() {
        return this.upgrades;
    }

    /**
     * Sets the upgrades in the ship.
     * @param upgrades The new upgrades.
     */
    public void setUpgrades(String[] upgrades) {
        this.upgrades = upgrades;
    }

    /**
     * Repairs the ships hull. Validates values.
     * @param amount The amount to repair.
     */
    public void repair(int amount) {
        if (amount < 0) {
            System.out.println("Cannot repair ship with negative value. Use takeDamage() to inflict damage.");
        } else if ((hull + amount) > maxHull) {
            hull = maxHull;
        } else {
            hull += amount;
        }
    }

    /**
     * Refuels the ship. Validates values.
     * @param fuel The amount of refuel.
     */
    public void refuel(double fuel) {
        if (fuel < 0.0) {
            System.out.println("Cannot fuel ship with negative value. Use setFuel() to deduct fuel.");
        } else if ((this.fuel + fuel) > maxFuel) {
            this.fuel = maxFuel;
        } else {
            this.fuel += fuel;
        }
    }

    /**
     * Damages the ship. Shield first, then hull.
     * @param dmg The amount of damage to inflict.
     */
    public void takeDamage(int dmg) {
        if (dmg < 0) {
            System.out.println("Cannot inflict negative damage. Use repair() to repair ship.");
        } else if (shield > 0) {
            if (shield > dmg) {
                shield -= dmg;
            } else {
                dmg -= shield;
                shield = 0;
                if ((hull - dmg) <= 0) {
                    hull = 0;
                } else {
                    hull -= dmg;
                }
            }
        } else {
            if ((hull - dmg) <= 0) {
                hull = 0;
            } else {
                hull -= dmg;
            }
        } 
    }
}