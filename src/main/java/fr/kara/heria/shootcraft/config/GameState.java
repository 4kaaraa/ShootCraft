package fr.kara.heria.shootcraft.config;

import lombok.Getter;
import lombok.Setter;

public enum GameState {
    WAITING,
    INGAME,
    TERMINATED,
    RESTARTING;

    @Setter
    @Getter
    private static GameState currentStep = GameState.RESTARTING;

    public static boolean isStep(final GameState step) {
        return GameState.currentStep == step;
    }

}
