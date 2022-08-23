package com.liferay.translation.translator.deepl.internal.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * JSON Utility
 *
 * @author Yasuyuki Takeo
 */
public class JSONUtil {

  /**
   * Map json to the actual object
   *
   * @param jsonString json strings to map to the object
   * @param clazz      class
   * @param <T>        Mapped class
   * @return
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException
   */
  public static <T> T toObject(final String jsonString, final Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {

    ObjectMapper mapper = new ObjectMapper();

    //just ignore unknown properties in json
    //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.enable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES);

    return mapper.readValue(jsonString, clazz);
  }

  /**
   * Write JSON file
   *
   * @param fullPath including target file name. e.g. /path/to/thisfile.txt
   * @param obj      Object to be serialized.
   * @param <T>
   * @throws IOException
   * @throws URISyntaxException
   */
  public static <T> void writer(String fullPath, T obj) throws IOException, URISyntaxException {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    ow.writeValue(new File(fullPath), obj);
  }

  /**
   * Write JSON Value as String
   *
   * @param obj Object to convert to JSON String
   * @param <T> Target class
   * @return JSON String
   * @throws JsonProcessingException
   */
  public static <T> String writeValueAsString(T obj) throws JsonProcessingException {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(obj);
  }
}

