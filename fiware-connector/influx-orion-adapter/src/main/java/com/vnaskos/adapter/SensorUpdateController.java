package com.vnaskos.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class SensorUpdateController {

    @Value("${orion.host}")
    private String orionHost;

    private final static String ORION_UPDATE_SENSOR_URL
            = "http://{orionHost}:1026/v2/entities/urn:ngsi-ld:Sensor:{sensorId}/attrs/temperature/value";

    @ResponseBody
    @GetMapping(path = AdapterUrls.UPDATE_SENSOR_TEMPERATURE)
    public ResponseEntity updateTemperatureValue(@PathVariable String sensorId, @PathVariable Float newTemperature) {
        final String requestBody = Float.toString(newTemperature);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("FIWARE-Service", "default");
        headers.set("FIWARE-ServicePath", "/");
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                ORION_UPDATE_SENSOR_URL.replace("{orionHost}", orionHost).replace("{sensorId}", sensorId),
                HttpMethod.PUT, entity, String.class);
    }
}
