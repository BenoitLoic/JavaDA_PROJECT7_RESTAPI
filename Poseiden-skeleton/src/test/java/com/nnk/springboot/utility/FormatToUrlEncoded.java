package com.nnk.springboot.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class FormatToUrlEncoded {

private static final ObjectMapper MAPPER=new ObjectMapper();

  public static String getUrlEncoded(Object dto) {
    Map map = MAPPER.convertValue(dto, Map.class);

    return map.toString()
        .replace(", ", "&")
        .replace("{", "")
        .replace("}", "");
  }
}
