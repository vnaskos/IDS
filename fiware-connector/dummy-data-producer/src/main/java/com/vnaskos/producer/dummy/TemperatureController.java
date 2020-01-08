package com.vnaskos.producer.dummy;

import com.vnaskos.producer.DataProducerUrlSpace;
import com.vnaskos.producer.api.template.TemplatePoint;
import com.vnaskos.producer.template.TemplatePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TemperatureController {

    private TemplatePointService templatePointService;

    private TemperatureService temperatureService;

    @Autowired
    public TemperatureController(final TemplatePointService templatePointService,
                                 final TemperatureService temperatureService) {
        this.templatePointService = templatePointService;
        this.temperatureService = temperatureService;
    }

    @ResponseBody
    @GetMapping(path = DataProducerUrlSpace.PRODUCE_RANDOM_TEMPERATURE_POINT)
    public TemplatePoint writeRandomTemperaturePoint() {
        final TemplatePoint templatePoint = temperatureService.createRandomTemperaturePoint();
        return templatePointService.writeTemplatePoint(templatePoint);
    }
}
