package com.vnaskos.ids.provider;

import com.vnaskos.ids.adapter.influxdb.Field;
import com.vnaskos.ids.adapter.influxdb.Fields;
import com.vnaskos.ids.adapter.influxdb.Tags;
import com.vnaskos.ids.adapter.influxdb.TemplatePoint;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MeasurementsResponseModelTest {

    private static final String OIL_LEVEL = "oilLevel";
    private static final int RANDOM_OIL_LEVEL_VALUE = 123;
    private static final int ANOTHER_RANDOM_OIL_LEVEL_VALUE = 678;
    private static final String PRESSURE = "pressure";
    private static final int RANDOM_PRESSURE_VALUE = 45;

    @Test
    public void groupSameMeasurementTemplatePointsByTimestampAndFieldValue() {
        long firstTemplatePointTime = System.currentTimeMillis()-1000;
        TemplatePoint firstOilLevelPoint = new TemplatePoint(OIL_LEVEL,
                firstTemplatePointTime,
                Fields.of(new Field("value", RANDOM_OIL_LEVEL_VALUE)),
                Tags.of());

        long secondTemplatePointTime = System.currentTimeMillis();
        TemplatePoint secondOilLevelPoint = new TemplatePoint(OIL_LEVEL,
                secondTemplatePointTime,
                Fields.of(new Field("value", ANOTHER_RANDOM_OIL_LEVEL_VALUE)),
                Tags.of());

        List<TemplatePoint> results = new ArrayList<>();
        results.add(firstOilLevelPoint);
        results.add(secondOilLevelPoint);

        String jsonResponse = MeasurementsResponseModel.asJsonFrom(results);

        assertEquals(String.format("{\"%s\":[[%d,%d],[%d,%d]]}",
                OIL_LEVEL,
                firstTemplatePointTime, RANDOM_OIL_LEVEL_VALUE,
                secondTemplatePointTime, ANOTHER_RANDOM_OIL_LEVEL_VALUE),
                jsonResponse);
    }

    @Test
    public void groupMultipleMeasurementsTemplatePointsByTimestampAndFieldValue() {
        long firstTemplatePointTime = System.currentTimeMillis()-1000;
        TemplatePoint oilLevelPoint = new TemplatePoint(OIL_LEVEL,
                firstTemplatePointTime,
                Fields.of(new Field("value", RANDOM_OIL_LEVEL_VALUE)),
                Tags.of());

        long secondTemplatePointTime = System.currentTimeMillis();
        TemplatePoint pressurePoint = new TemplatePoint(PRESSURE,
                secondTemplatePointTime,
                Fields.of(new Field("value", RANDOM_PRESSURE_VALUE)),
                Tags.of());

        List<TemplatePoint> results = new ArrayList<>();
        results.add(oilLevelPoint);
        results.add(pressurePoint);

        String jsonResponse = MeasurementsResponseModel.asJsonFrom(results);

        assertEquals(String.format("{\"%s\":[[%d,%d]],\"%s\":[[%d,%d]]}",
                OIL_LEVEL,
                firstTemplatePointTime, RANDOM_OIL_LEVEL_VALUE,
                PRESSURE,
                secondTemplatePointTime, RANDOM_PRESSURE_VALUE),
                jsonResponse);
    }

}