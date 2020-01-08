package com.vnaskos.adapter;

import com.vnaskos.adapter.dao.TemperatureDAO;
import com.vnaskos.adapter.dto.TemperaturePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdapterController {

    private TemperatureDAO temperatureDAO;

    @Autowired
    public AdapterController(final TemperatureDAO temperatureDAO) {
        this.temperatureDAO = temperatureDAO;
    }

    @ResponseBody
    @GetMapping(path = AdapterUrls.LIST_TEMPERATURE_INFLUX_POINTS)
    public List<TemperaturePoint> getTemperatureMeasurements() {
        return temperatureDAO.getTemperatureMeasurements();
    }
}
