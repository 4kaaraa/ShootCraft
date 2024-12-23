package fr.kara.heria.shootcraft.config;

public enum MessageConfigEnum {
    PREFIX("§6§lSHOOTCRAFT §8┃"),
    ;




    // Paramater
    private final String click;

    MessageConfigEnum(String click) {
        this.click = click;
    }

    public String getClick() {
        return click;
    }

    @Override
    public String toString() {
        return click;
    }
}
