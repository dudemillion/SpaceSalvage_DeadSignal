public class MainTest {
    public static void main(String[] args) {
        Ship ship = new Ship(100, 50, 100.0, 50, 100, 50, 100, new String[1]);
        System.out.println("Ship status: Hull: " + ship.getHull() 
        + " Shields: " + ship.getShield() 
        + " Fuel: " + ship.getFuel()
        + " Credits: " + ship.getCredits());
    }
}
