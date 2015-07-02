package Utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ohad on 9/6/2015.
 */
public class ErcLogger {
    private Logger logger;

    public ErcLogger(){
        logger = Logger.getLogger(this.getClass().getName());
    }

    public void println(String s){
        logger.log(Level.INFO, s);
    }

    public void println(Level level, String s){
        logger.log(level, s);
    }
}
