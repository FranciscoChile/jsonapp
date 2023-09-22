package com.sonatafy.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonatafy.exception.JsonException;
import com.sonatafy.model.ChangeType;
import com.sonatafy.model.ListUpdate;
import com.sonatafy.model.PropertyUpdate;
import com.sonatafy.util.DiffTool;

public class JsonTool {
    
    ObjectMapper objectMapper = new ObjectMapper();

    public List<ChangeType> parserJson (String json) throws JsonException, JsonProcessingException {        
        
        JsonValidatorSchema jsonValidatorSchema = new JsonValidatorSchema();

        int errorsDiff = jsonValidatorSchema.validator(json, "/schemaDiff.json");

        if (errorsDiff == 0) {            
            PropertyUpdate p = objectMapper.readValue(json, PropertyUpdate.class);
            
                if (p.getProperty().indexOf(".") != -1) {
                    String listName = p.getProperty().substring(0, p.getProperty().indexOf("."));
                    String[] id = listName.split("[\\[\\]]", -1);

                    if (id.length > 1) {
                        if (id[1].equals("id") || id[1].equals("@AuditKey")) {
                            return addProperty(p);
                        }
                        else {
                            throw new JsonException("Audit system lacks the information it needs to determine what has changed.");
                        }
                    } else {
                        return addProperty(p);
                    }
                } else {
                    return addProperty(p);
                }
                        
        }
        
        List<ChangeType> list = new ArrayList<>();

        int errorsDiffList = jsonValidatorSchema.validator(json, "/schemaDiffList.json");        
        if (errorsDiffList == 0) {
            ListUpdate l = objectMapper.readValue(json, ListUpdate.class);
            list.add(l);
        }

        return list;
    }


    private List<ChangeType> addProperty(PropertyUpdate p) {        
        DiffTool diffTool = new DiffTool();
        List<ChangeType> a = diffTool.diff(p.getCurrent(), p.getPrevious());

        if (!a.isEmpty()) {
            PropertyUpdate propertyUpdate = (PropertyUpdate)a.get(0);
            propertyUpdate.setProperty(p.getProperty());
        }

        return a;
    }


}
