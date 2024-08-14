package org.example.display;

public class Color {
    public static final String reset = "\033[0m";
    public static final String black = "\033[0;30m";
    public static final String red = "\033[0;31m";
    public static final String green = "\033[0;32m";
    public static final String yellow = "\033[0;33m";
    public static final String blue = "\033[0;34m";
    public static final String purple = "\033[0;35m";
    public static final String cyan = "\033[0;36m";
    //public static final String white = "\033[0;37m";
    public static final String baby_pink = "\u001B[38;2;255;182;193m";
    public static final String baby_blue = "\u001B[38;2;173;216;230m";
    public static final String lavender = "\u001B[38;2;178;164;212m";
    public static final String grey = "\u001B[37m";
    public static final String italic_grey = "\033[3m" + "\033[90m";
    public static final String orange = "\033[0;33m";
    public static final String underline = "\u001B[4;37m";


    public static String colorText(String text, String colorCode) {
        return colorCode + text + reset;
    }
}
