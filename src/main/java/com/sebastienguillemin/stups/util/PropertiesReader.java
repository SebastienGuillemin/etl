package com.sebastienguillemin.stups.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class PropertiesReader {
    private static PropertiesReader propertiesReader;

    public synchronized static PropertiesReader getInstance() {
        if (propertiesReader == null)
            propertiesReader = new PropertiesReader();

        return propertiesReader;
    }

    private Map<String, Object> properties;

    private PropertiesReader() {
        try {
            this.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    public String getPropertyValue(String propertyName) {
        String parts[] = propertyName.split("\\.");

        String value = "";
        Map<String, Object> nextNode = this.properties;
        Object next;
        for (int i = 0; i < parts.length; i ++) {
            next = nextNode.get(parts[i]);
            if (next == null) {
                return "";
            }
            else if (next instanceof Map)
                nextNode = (Map<String, Object>) next;
            else if (next instanceof String) {
                if (i < parts.length - 1)
                    return "";
    
                value = (String) next;
            }
        }

        return value;
    }

    public boolean getPropertyValueBoolean(String propertyName) {
        return Boolean.valueOf(this.getPropertyValue(propertyName));
    }

    public int getPropertyValueInteger(String propertyName) {
        return Integer.valueOf(this.getPropertyValue(propertyName));
    }

    public List<Integer> getWhiteList() {
        List<Integer> whiteList  = new ArrayList<>();

        String[] ids = this.getPropertyValue("evaluation.white_list").split(", ");

        for (String id : ids) {
            whiteList.add(Integer.parseInt(id.trim()));
        }

        return whiteList;
    }

    public List<Integer> getBlackList() {
        List<Integer> whiteList  = new ArrayList<>();

        String[] ids = this.getPropertyValue("evaluation.black_list").split(", ");

        for (String id : ids) {
            if (id.equals(""))
                continue;
            whiteList.add(Integer.parseInt(id.trim()));
        }

        return whiteList;
    }

    private void loadProperties() throws IOException {
        System.out.println("Loading properties.");
        
        Yaml yaml = new Yaml();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("properties.yml")) {
            this.properties = yaml.load(in);
        }
        System.out.println(this.properties);
        System.out.println("Properties loaded.");
    }
}
