package Utilities;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by naor on 12/8/2015.
 */
public class ParametersVerifier {
    private Set setToVerify = null;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ParametersVerifier(Set set){
        this.setToVerify = set;
    }

    public ParametersVerifier(Map map){
        this.setToVerify = map.keySet();
    }

    public boolean verify(String ... params){
        for (String param : params){
            if (!setToVerify.contains(param)){
                logger.warning("Missing parameter = " + param);
                return false;
            }
        }
        return true;
    }

}
