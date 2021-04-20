package com.rabkov.task2.util;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Util {
    public String getPath(String xmlPath){
        URL url = getClass().getClassLoader().getResource(xmlPath);
        String correctPath = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
        return correctPath;
    }
}
