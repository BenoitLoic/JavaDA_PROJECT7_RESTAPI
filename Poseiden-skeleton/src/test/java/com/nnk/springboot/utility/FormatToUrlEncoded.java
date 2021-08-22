package com.nnk.springboot.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;

public class FormatToUrlEncoded {



  public static String getUrlEncoded(Object dto) {
    ObjectMapper MAPPER = new ObjectMapper();
    MAPPER.registerModule(new JavaTimeModule());
    Map map = MAPPER.convertValue(dto, Map.class);

    return map.toString()
        .replace(", ", "&")
        .replace("{", "")
        .replace("}", "");
  }
}
