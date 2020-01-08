package com.vnaskos.producer.template;

import com.vnaskos.producer.api.template.TemplatePoint;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TemplatePointServiceImpl implements TemplatePointService {

    private static final Logger LOG = LoggerFactory.getLogger(TemplatePointServiceImpl.class);

    @Value("${spring.influx.dbname}")
    private String databaseName;

    private InfluxDB influxdb;

    @Autowired
    public TemplatePointServiceImpl(final InfluxDB influxdb) {
        this.influxdb = influxdb;
    }

    @Override
    public TemplatePoint writeTemplatePoint(final TemplatePoint templatePoint) {
        final TemplatePoint resolvedPoint = TemplatePoint.resolved(templatePoint);
        final Point point = resolvedPoint.toPoint();

        LOG.info(point.toString());

        influxdb.setDatabase(databaseName);
        influxdb.write(point);
        return resolvedPoint;
    }
}
