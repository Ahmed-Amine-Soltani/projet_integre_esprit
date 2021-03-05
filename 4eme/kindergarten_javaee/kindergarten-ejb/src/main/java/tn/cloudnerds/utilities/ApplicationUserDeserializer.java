package tn.cloudnerds.utilities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import tn.cloudnerds.entities.ApplicationUser;

public class ApplicationUserDeserializer extends StdDeserializer<ApplicationUser> {
	
	public ApplicationUserDeserializer() { 
        this(null); 
    } 
 
    public ApplicationUserDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public ApplicationUser deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (Integer) ((IntNode) node.get("Id")).numberValue();
        String userName = node.get("UserName").asText();
        String email = node.get("Email").asText();
 
        return new ApplicationUser(id, email, userName);
    }
}
