package com.vnaskos.ids.adapter.influxdb;

public class Tags extends MapWithNameAsKey<Tag> {

    public static Tags of(Tag... valuesArray) {
        final Tags map = new Tags();
        for (Tag value : valuesArray) {
            map.add(value);
        }
        return map;
    }
}
