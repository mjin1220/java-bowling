package bowling;

import bowling.controller.BowlingController;

public class BowlingApp {
    public static void main(String[] args) {
        BowlingController bowlingController = new BowlingController();
        bowlingController.start();
    }
}
