package Handlers.HUD;

public class HealthHandler {

    private final double initialHealth = 100;
    private double health;

    private final int maxHearts = 8;
    private double currentHearts;

    public HealthHandler(double health) {
        this.health = health;
    }

    public void updateHealth(double health) {
        System.out.println("HERE");
        this.health = health;
        determineHearts();

    }

    private void determineHearts() {
        currentHearts = (health / initialHealth) * maxHearts;
        System.out.println(currentHearts);
    }


}
