package com.vnaskos.producer.api.template;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RandomFloatTemplateFieldType implements TemplateFieldType {

    private final static String PATTERN = "^\\$\\{randFloat(,(\\d+))?(,(\\d+))?\\}$";
    private final static Pattern P = Pattern.compile(RandomFloatTemplateFieldType.PATTERN);

    RandomFloatTemplateFieldType() { }

    @Override
    public Float parse(final String rawValue) {
        final Matcher m = P.matcher(rawValue);
        if (!m.find()) {
            return null;
        }

        int max = 1;
        int min = 0;

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
