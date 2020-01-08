package com.vnaskos.consumer.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/subscription/temperature")
public class SubscriptionController {

    private final static Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);

    private static String debugLastMessage = "";

    @PostMapping(path = "/changed")
    public ResponseEntity<?> onTemperatureChange(@RequestBody final Object req) {
        LOG.info(req.toString());
        debugLastMessage = req.toString();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(path = "/debug")
    public String debug() {
        return debugLastMessage;
    }
}
