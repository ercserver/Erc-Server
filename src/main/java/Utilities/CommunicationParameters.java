package Utilities;

import DatabaseModule.src.api.IDbController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 21/05/2015.
 */
public  class CommunicationParameters {

    private IDbController dbController = null;

    public CommunicationParameters()
    {
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
    }

    public HashMap<String, String> getDefaultInEmergency(String state) {
        HashMap<Integer,HashMap<String,String>> defult
                = dbController.getDefaultInEmergency(state);
        for (Map.Entry<Integer,HashMap<String,String>> objs : defult.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            String code = obj.get("default_caller");
            HashMap<String,String> response = convertCodeToDefaultCallerSettings(code);
            return response;

        }
        return null;
    }

    public HashMap<String, String> convertCodeToDefaultCallerSettings(String code) {
        HashMap<String,String> defalut = new HashMap<String,String>();
        defalut.put("column_name","'default_caller'");
        defalut.put("enum_code", "'" + code + "'");
        defalut.put("table_name","'DefaultCallerSettings'");
        HashMap<Integer, HashMap<String, String>> data =
                dbController.getFromEnum(defalut);

        //System.out.println(response);
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> response = new HashMap<String,String>();
            HashMap<String,String> obj = objs.getValue();
            response.put("name","default_caller");
            response.put("frequency",obj.get("enum_value")); // ???
            return response;
        }
        return null;
    }

    public HashMap<String,String> getFrequency(String code) {
        HashMap<String,String> response = new HashMap<String,String>();

        HashMap<String,String> kindOfFrequency = new HashMap<String,String>();
        kindOfFrequency.put("name",code);
        HashMap<Integer,HashMap<String,String>> freq
                = dbController.getFrequency(kindOfFrequency);
        if (freq == null)
            return null;
        for (Map.Entry<Integer,HashMap<String,String>> objs : freq.entrySet()){
            HashMap<String,String> obj = objs.getValue();

            response.put("name",obj.get("name"));
            response.put("frequency",obj.get("frequency"));
            return response;
        }
        return null;
    }

}
