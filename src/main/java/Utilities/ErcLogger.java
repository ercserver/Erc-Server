package Utilities;

/**
 * Created by ohad on 9/6/2015.
 */
public class ErcLogger {
    private final boolean DEBUG = true;


    public void println(String s){
        if (DEBUG){
            System.out.println(s);
        }
    }
}
