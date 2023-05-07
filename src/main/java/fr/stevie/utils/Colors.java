package fr.stevie.utils;

import org.bukkit.Color;

public class Colors {

    public static Color getColor(int i) {
        return switch (i) {
            case 1 -> Color.AQUA;
            case 2 -> Color.BLACK;
            case 3 -> Color.BLUE;
            case 4 -> Color.FUCHSIA;
            case 5 -> Color.GRAY;
            case 6 -> Color.GREEN;
            case 7 -> Color.LIME;
            case 8 -> Color.MAROON;
            case 9 -> Color.NAVY;
            case 10 -> Color.OLIVE;
            case 11 -> Color.ORANGE;
            case 12 -> Color.PURPLE;
            case 14 -> Color.SILVER;
            case 15 -> Color.TEAL;
            case 16 -> Color.WHITE;
            case 17 -> Color.YELLOW;
            default -> Color.RED;
        };
    }

}