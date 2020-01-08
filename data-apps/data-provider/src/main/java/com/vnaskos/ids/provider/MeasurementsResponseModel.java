package com.vnaskos.ids.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vnaskos.ids.adapter.influxdb.Field;
import com.vnaskos.ids.adapter.influxdb.TemplatePoint;

import java.io.Serializable;
import java.util.List;

public class MeasurementsResponseModel implements Serializable {

    public static String asJsonFrom(List<TemplatePoint> templatePoints) {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode rootNode = mapper.createObjectNode();

        for (TemplatePoint templatePoint : templatePoints) {
            for (Field field : templatePoint.getFields()) {
                ArrayNode fieldValuesNode = rootNode.withArray(templatePoint.getMeasurementName());
                ArrayNode timeValuePair = mapper.createArrayNode();
                timeValuePair.add(templatePoint.getTime());
                timeValuePair.addPOJO(field.getValue());
                fieldValuesNode.add(timeValuePair);
            }
        }

        return rootNode.toString();
    }
}
