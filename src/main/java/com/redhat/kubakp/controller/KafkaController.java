package com.redhat.kubakp.controller;

import com.redhat.kubakp.model.Response;
import com.redhat.kubakp.model.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class KafkaController {
    private Logger logger = LoggerFactory.getLogger(KafkaController.class);
    @Autowired
    private Response response;
    @Autowired
    private KafkaTemplate<String, Square> kafkaTemplate;

    @PostMapping("/square")
    public ResponseEntity postSquareRecord(@RequestBody Square square) throws Exception {
        logger.info(square.toString());
        kafkaTemplate.send("t10", square);
        return ResponseEntity
                .ok()
                .header("content-type", "application/json")
                .body(response.getJsonPayload());

    }


}
