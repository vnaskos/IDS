package com.vnaskos.producer.template;

import com.vnaskos.producer.DataProducerUrlSpace;
import com.vnaskos.producer.api.template.TemplatePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TemplatePointController {

    private TemplatePointService templatePointService;

    @Autowired
    public TemplatePointController(final TemplatePointService templatePointService) {
        this.templatePointService = templatePointService;
    }

    @ResponseBody
    @PostMapping(path = DataProducerUrlSpace.PRODUCE_POINT_FROM_TEMPLATE)
    public TemplatePoint writePointFromTemplate(@RequestBody final TemplatePoint templatePoint) {
        return templatePointService.writeTemplatePoint(templatePoint);
    }
}
