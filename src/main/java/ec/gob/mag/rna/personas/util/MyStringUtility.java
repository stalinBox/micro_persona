package ec.gob.mag.rna.personas.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyStringUtility {
	 public static String encodeObjectToJsonString(Object obj) {
		 ObjectMapper mapper= new ObjectMapper();
		 try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	 }

}
