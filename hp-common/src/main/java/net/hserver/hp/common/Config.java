package net.hserver.hp.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author hxm
 */
public class Config {
 public static final ObjectMapper JSON = (new ObjectMapper()).configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true).configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true).configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
}
