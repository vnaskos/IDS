package com.vnaskos.adapter.dao;

import com.vnaskos.adapter.dto.TemperaturePoint;

import java.util.List;

public interface TemperatureDAO {

    List<TemperaturePoint> getTemperatureMeasurements();

    TemperaturePoint getLastMeasurement();
}
