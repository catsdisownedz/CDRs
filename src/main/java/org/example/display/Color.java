package org.example.display;

public class Color{
    // ANSI escape codes
    public static final String reset = "\033[0m";
    public static final String black = "\033[0;30m";
    public static final String red = "\033[0;31m";
    public static final String green = "\033[0;32m";
    public static final String yellow = "\033[0;33m";
    public static final String blue = "\033[0;34m";
    public static final String purple = "\033[0;35m";
    public static final String cyan = "\033[0;36m";
    public static final String white = "\033[0;37m";

    public static String colorText(String text, String colorCode) {
        return colorCode + text + reset;
    }
}
