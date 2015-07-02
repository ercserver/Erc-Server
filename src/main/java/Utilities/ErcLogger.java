package Utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ohad on 9/6/2015.
 */
public class ErcLogger{
    private Logger logger;

    public ErcLogger(String className){
        logger = Logger.getLogger(className);
    }

    public void println(String s){
        logger.log(Level.INFO, s);
    }

    public void println(Level level, String s){
        logger.log(level, s);
    }
}
