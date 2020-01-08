package com.vnaskos.ids.adapter.influxdb.resolver;

public interface FieldTypeResolver {

    Object resolve(Object rawValue);

}
