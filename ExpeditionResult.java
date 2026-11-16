/*
I have only used resources indicated that are allowable.
The work submitted is my, and only my work.
Signature: Nikhil Bhalla
Date: 11/15/2026
*/


/**
 * Generates results for expeditions for statistic tracking.
 */
public class ExpeditionResult {
    private String expeditionName;
    private int matches;
    private int reward;
    private int damageTaken;
    private boolean successful;

    /**
     * Creates a new result for an expedition.
     * @param expeditionName The name of the expedition.
     * @param matches The total matches made during the expedition.
     * @param reward The total credits earned for the expedition.
     * @param damageTaken The total damage taken during the expedition.
     * @param successful Whether the expedition ended in extraction or emergency rescue.
     */
    public ExpeditionResult(String expeditionName, int matches, int reward, int damageTaken, boolean successful) {
        this.expeditionName = expeditionName;
        this.matches = matches;
        this.reward = reward;
        this.damageTaken = damageTaken;
        this.successful = successful;
    }

    /**
     * Gets the name of the expedition.
     * @return The expedition's name.
     */
    public String getExpeditionName() {
        return expeditionName;
    }

    /**
     * Gets the total matches made in the expedition.
     * @return The expedition's total matches.
     */
    public int getMatches() {
        return matches;
    }

    /**
     * Gets the reward recieved from the expedition.
     * @return The total reward earned from the expedition.
     */
    public int getReward() {
        return reward;
    }

    /**
     * Gets the total damage taken during an expedition.
     * @return The total damage taken.
     */
    public int getDamageTaken() {
        return damageTaken;
    }

    /**
     * Whether the expedition ended in an extraction or emergency rescue.
     * @return True if it ended in extraction, false if by emergency rescue.
     */
    public boolean wasSuccessful() {
        return successful;
    }

    /**
     * Sets the name of the expedition.
     * @param name The expedition's name.
     */
    public void setExpeditionName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be null or blank.");
        } else {
            expeditionName = name;
        }
    }

    /**
     * Sets the matches made in the expedition.
     * @param matches The matches made.
     */
    public void setMatches(int matches) {
        if (matches < 0) {
            System.out.println("Cannot set matches to negative.");
        } else {
            this.matches = matches;
        }
    }

    /**
     * Sets the reward received from the expedition.
     * @param reward The reward.
     */
    public void setReward(int reward) {
        if (reward < 0) {
            System.out.println("Cannot set expedition reward to negative.");
        } else {
            this.reward = reward;
        }
    }

    /**
     * Sets the damage taken during the expedition.
     * @param damage The damage taken.
     */
    public void setDamageTaken(int damage) {
        if (damage < 0) {
            System.out.println("Cannot set damage taken to negative.");
        } else {
            damageTaken = damage;
        }
    }

    /**
     * Sets whether the expedition ended in extraction or emergency rescue.
     * @param success The result of the ending.
     */
    public void setSuccessful(boolean success) {
        successful = success;
    }

    /**
     * Displays the results of your expedition. Gives you stats of the expedition and personalized text based on whether it was successful or not.
     */
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
