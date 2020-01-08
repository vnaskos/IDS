package com.vnaskos.ids.adapter.influxdb.resolver;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomFloatFieldTypeResolver implements FieldTypeResolver {

    static final int DEFAULT_UPPER_LIMIT = 1;
    static final int DEFAULT_LOWER_LIMIT = 0;

    private final static String PATTERN = "^\\$\\{randFloat(,(\\d+))?(,(\\d+))?}$";
    private final static Pattern P = Pattern.compile(PATTERN);

    @Override
    public Object resolve(Object rawValue) {
        if (!(rawValue instanceof String)) {
            return rawValue;
        }

        String rawInput = (String) rawValue;

        Matcher m = P.matcher(rawInput);
        if (!m.find()) {
            return rawValue;
        }

        int max = DEFAULT_UPPER_LIMIT;
        int min = DEFAULT_LOWER_LIMIT;

        if(m.group(4) != null) {
            max = Integer.parseInt(m.group(4));
            min = Integer.parseInt(m.group(2));
        } else if (m.group(2) != null) {
            max = Integer.parseInt(m.group(2));
        }

        final Random rand = new Random();
        return rand.nextFloat() * (max-min) + min;
    }
}
