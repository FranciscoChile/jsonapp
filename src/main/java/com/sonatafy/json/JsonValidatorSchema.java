package com.sonatafy.json;

import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.ValidationMessage;

public class JsonValidatorSchema {
    

    public int validator(String json, String schemaFile) throws JsonProcessingException {

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(VersionFlag.V7);
        JsonSchema jsonSchema = factory.getSchema(JsonValidatorSchema.class.getResourceAsStream(schemaFile));

        Set<ValidationMessage> errors = jsonSchema.validate(new ObjectMapper().readTree(json));

        return errors.size();

    }

}
