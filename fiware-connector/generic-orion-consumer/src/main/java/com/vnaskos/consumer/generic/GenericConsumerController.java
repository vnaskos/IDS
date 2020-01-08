package com.vnaskos.consumer.generic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping(value = "/v1/temperature")
public class GenericConsumerController {

    @Value("${orion.host}")
    private String orionHost;

    private final static String ORION_SENSOR_DATA_URL = "http://{orionHost}:1026/v2/entities/urn:ngsi-ld:Sensor:{sensorId}/?type=Sensor&options=keyValues";

    public GenericConsumerController() {
    }

    @ResponseBody
    @GetMapping(path = "/sensor/{sensorId}")
    public SensorOrionResponse doAPost(@PathVariable final String sensorId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("FIWARE-Service", "default");
        headers.set("FIWARE-ServicePath", "/");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<SensorOrionResponse> response =
                restTemplate.exchange(
                        ORION_SENSOR_DATA_URL
                                .replace("{orionHost}", orionHost)
                                .replace("{sensorId}", sensorId),
                        HttpMethod.GET, entity, SensorOrionResponse.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Something went horrible wrong, or the resource you requested may not exist");
        }

        return response.getBody();
    }

}
