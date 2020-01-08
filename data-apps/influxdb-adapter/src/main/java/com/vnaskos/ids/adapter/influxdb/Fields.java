package com.vnaskos.ids.adapter.influxdb;

public class Fields extends MapWithNameAsKey<Field> {

    public static Fields of(Field... valuesArray) {
        final Fields map = new Fields();
        for (Field value : valuesArray) {
            map.add(value);
        }
        return map;
    }
}
