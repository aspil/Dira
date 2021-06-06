package di.uoa.gr.dira.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectCloner {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T deepCloneObject(T obj, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(obj), clazz);
    }
}
