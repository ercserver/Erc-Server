package Utilities;

/**
 * Created by ohad on 9/6/2015.
 */
public class ErcLogger {
    private static boolean DEBUG = true;

    public static synchronized void setDEBUG(boolean bool){
        DEBUG = bool;
    }

    public static synchronized void println(String s){
        if (DEBUG){
            System.out.println(s);
        }
    }
}
