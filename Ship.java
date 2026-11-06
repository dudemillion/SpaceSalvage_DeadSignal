public class Ship {
    private int hull;
    private int shield;
    private double fuel;
    private int credits;
    private int maxHull;
    private int maxShield;
    private double maxFuel;
    private String[] upgrades;

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
    
    public int getHull() {
        return this.hull;
    }

    public int getMaxHull() {
        return this.maxHull;
    }

    public void setHull(int hull) {
        if (hull < 0) {
            System.out.println("Cannot set hull to negative.");
        } else if (hull > maxHull) {
            System.out.println("Cannot set hull integrity above max hull integrity.");
        } else {
            this.hull = hull;
        }
    }
    
    public void setMaxHull(int max) {
        if (max <= 0) {
            System.out.println("Cannot set max hull integrity to negative or zero.");
        } else {
            this.maxHull = max;
        }
    }

    public int getShield() {
        return this.shield;
    }

    public int getMaxShield() {
        return this.maxShield;
    }

    public void setMaxShield(int max) {
        if (max <= 0) {
            System.out.println("Cannot set max shield battery to negative or zero.");
        } else {
            maxShield = max;
        }
    }

    public void setShield(int shield) {
        if (shield < 0) {
            System.out.println("Cannot set shield to negative.");
        } else if (shield > maxShield) {
            System.out.println("Cannot set shield battery above maximum charge.");
        } else {
            this.shield = shield;
        }
    }

    public double getFuel() {
        return this.fuel;
    }

    public double getMaxFuel() {
        return this.maxFuel;
    }

    public void setFuel(double fuel) {
        if (fuel < 0) {
            System.out.println("Cannot set fuel to negative.");
        } else if (fuel > maxFuel) {
            System.out.println("Cannot set fuel amount above fuel tank capacity.");
        } else {
            this.fuel = fuel;
        }
    }

    public void setMaxFuel(double max) {
        if (max <= 0) {
            System.out.println("Cannot set maximum fuel capacity to negative.");
        } else {
            maxFuel = max;
        }
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        if (credits < 0) {
            System.out.println("Cannot set credit balance to negative.");
        } else {
            this.credits = credits;
        }
    }

    public String[] getUpgrades() {
        return this.upgrades;
    }

    public void setUpgrades(String[] upgrades) {
        this.upgrades = upgrades;
    }

    public void repair(int amount) {
        if (amount < 0) {
            System.out.println("Cannot repair ship with negative value. Use takeDamage() to inflict damage.");
        } else if ((hull + amount) > maxHull) {
            hull = maxHull;
        } else {
            hull += amount;
        }
    }

    public void refuel(double fuel) {
        if (fuel < 0.0) {
            System.out.println("Cannot fuel ship with negative value. Use setFuel() to deduct fuel.");
        } else if ((this.fuel + fuel) > maxFuel) {
            this.fuel = maxFuel;
        } else {
            this.fuel += fuel;
        }
    }

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