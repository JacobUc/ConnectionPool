package configReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ValidarLectura {
    public Object readJSON(String pathToJSONFile){
        Object readResult = null;
        try{
            FileReader reader = new FileReader(pathToJSONFile);
            JSONParser jsonParser = new JSONParser();
            readResult = jsonParser.parse(reader);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }
        return readResult;
    }
    
    public JSONObject extractJSONObject(Object obj){
        JSONObject jsonObj = null;
        try{
            jsonObj  = new JSONObject((Map) obj);
        }catch(Exception e){
        }
        return jsonObj;
    }
    
    public JSONObject extractJSONObjectbyKey(JSONObject obj, String key){
        JSONObject employeeObject = new JSONObject();
        try{
            employeeObject = (JSONObject) obj.get(key);
        }catch(Exception e){
        }
        return employeeObject;
    }
    
    
    public JSONArray extractJSONArray(JSONObject jsonObj, String arrayName){
        JSONArray jsonArray = new JSONArray();
        try{
            jsonArray = (JSONArray) jsonObj.get(arrayName);
        }catch(Exception e){
        }
        return jsonArray;
    }
}
