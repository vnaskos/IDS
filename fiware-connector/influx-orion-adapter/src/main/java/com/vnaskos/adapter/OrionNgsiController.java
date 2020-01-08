package com.vnaskos.adapter;

import com.vnaskos.adapter.dao.TemperatureDAO;
import com.vnaskos.adapter.dto.TemperaturePoint;
import com.vnaskos.adapter.api.ngsi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrionNgsiController {

    private final static Logger LOG = LoggerFactory.getLogger(OrionNgsiController.class);

    private TemperatureDAO temperatureDAO;

    @Autowired
    public OrionNgsiController(final TemperatureDAO temperatureDAO) {
        this.temperatureDAO = temperatureDAO;
    }

    @ResponseBody
    @PostMapping(path = AdapterUrls.ORION_QUERY_TEMPERATURE_CONTEXT)
    public ResponseWrapper queryContext(@RequestBody Object req) {
        LOG.debug(req.toString());

        TemperaturePoint measurement = temperatureDAO.getLastMeasurement();

        final ContextElement contextElement = new ContextElement();
        contextElement.setId("urn:ngsi-ld:Sensor:435282J");
        contextElement.setType("Sensor");
        contextElement.addAttribute(ContextAttribute.create("temperature", "float", measurement.getValue()));

        final ContextResponse contextResponse = new ContextResponse();
        contextResponse.setContextElement(contextElement);
        contextResponse.setStatusCode(new StatusCode());

        final ResponseWrapper wrapper = new ResponseWrapper();
        wrapper.addContextResponse(contextResponse);

        return wrapper;
    }

}
