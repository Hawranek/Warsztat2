package pl.coderslab.entity;

import java.util.Scanner;

public class KeyboardReader {
    private static Scanner scan = new Scanner(System.in);

    public static void close() {
        scan.close();
    }

    public static int readInt(String message) {  //działa


        System.out.println(message);
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.println("Podana wartość nie jest liczbą.");
        }
        int in = scan.nextInt();
        return in;

    }

    public static double readDouble(String message) {  //działa

        System.out.println(message);
        while (!scan.hasNextDouble()) {
            scan.next();
            System.out.println("Podana wartość nie jest liczbą.");
        }
        double dbl = scan.nextDouble();
        return dbl;

    }

    public static String readString(String message) {  //działą
        System.out.println(message);
        return scan.next();
    }

}
