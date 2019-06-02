package com.im050.easyfoodcommon.utils.sqlhelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Fields extends ArrayList<HashMap<String, Object>> {

    public static Fields create() {
        return new Fields();
    }

    public Fields addField(String field, Object value) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("field", field);
        hashMap.put("value", value);
        this.add(hashMap);
        return this;
    }
}