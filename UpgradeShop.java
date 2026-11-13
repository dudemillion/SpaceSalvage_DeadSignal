public class UpgradeShop {
    private int[] prices;

    public UpgradeShop() {
        prices = new int[]{50, 45, 30, 10, 15, 10};
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
}
