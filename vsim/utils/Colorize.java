package vsim.utils;


public final class Colorize {

    public static String red(String s) {
        return "\033[0;91m" + s + "\033[0m";
    }

    public static String green(String s) {
        return "\033[0;92m" + s + "\033[0m";
    }

    public static String yellow(String s) {
        return "\033[0;93m" + s + "\033[0m";
    }

    public static String blue(String s) {
        return "\033[0;94m" + s + "\033[0m";
    }

    public static String purple(String s) {
        return "\033[0;95m" + s + "\033[0m";
    }

    public static String cyan(String s) {
        return "\033[0;96m" + s + "\033[0m";
    }

}