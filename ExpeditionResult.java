public class ExpeditionResult {
    private String expeditionName;
    private int matches;
    private int reward;
    private int damageTaken;
    private boolean successful;

    public ExpeditionResult(String expeditionName, int matches, int reward, int damageTaken, boolean successful) {
        this.expeditionName = expeditionName;
        this.matches = matches;
        this.reward = reward;
        this.damageTaken = damageTaken;
        this.successful = successful;
    }

    public String getExpeditionName() {
        return expeditionName;
    }

    public int getMatches() {
        return matches;
    }

    public int getReward() {
        return reward;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public boolean wasSuccessful() {
        return successful;
    }

    public void setExpeditionName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be null or blank.");
        } else {
            expeditionName = name;
        }
    }

    public void setMatches(int matches) {
        if (matches < 0) {
            System.out.println("Cannot set matches to negative.");
        } else {
            this.matches = matches;
        }
    }

    public void setReward(int reward) {
        if (reward < 0) {
            System.out.println("Cannot set expedition reward to negative.");
        } else {
            this.reward = reward;
        }
    }

    public void setDamageTaken(int damage) {
        if (damage < 0) {
            System.out.println("Cannot set damage taken to negative.");
        } else {
            damageTaken = damage;
        }
    }

    public void setSuccessful(boolean success) {
        successful = success;
    }

    public void displayResults() {
        Helpers.typewrite("Expedition complete.", 70);
        if (successful) {
            Helpers.typewrite("The company congratulates you on another successful expedition.", 70);
        } else {
            Helpers.typewrite("Your ship is being taken in for repairs. The company is dissatisfied.", 70);
        }
        System.out.println("Expedition: " + expeditionName + " stats:");
        Helpers.typewrite("You made " + matches + " matches in your expedition.", 60);
        Helpers.typewrite("You have earned " + reward + " credits for your work.", 60);
        Helpers.typewrite("You took " + damageTaken + " damage during the expedition.", 60);
        Helpers.typewrite("The company notes your performance.", 70);
    }
}
