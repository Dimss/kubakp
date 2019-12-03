package com.redhat.kubakp.controller;

import com.redhat.kubak.square.Square;
import com.redhat.kubakp.model.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/v1")
public class KafkaController {
    private Logger logger = LoggerFactory.getLogger(KafkaController.class);
    @Autowired
    private Response response;
    @Autowired
    private KafkaTemplate<String, Square> kafkaTemplate;

    @PostMapping("/square/topic/{topic}")
    public ResponseEntity postSquareRecord(@PathVariable String topic, @RequestBody Square square) throws Exception {
        logger.info(square.toString());
        kafkaTemplate.send(topic, square);
        return ResponseEntity
                .ok()
                .header("content-type", "application/json")
                .body(response.getJsonPayload());
    }

    @GetMapping("/autosquare/topic/{topic}/color/{color}")
    public ResponseEntity postAutoSquareRecord(@PathVariable String topic, @PathVariable String color) throws Exception {
        Random r = new Random();
        Square square = new Square();
        square.setColor(color);
        square.setX(r.nextInt((490 - 1) + 1) + 1);
        square.setY(r.nextInt((490 - 1) + 1) + 1);
        square.setSize(10);
        logger.info(square.toString());
        kafkaTemplate.send(topic, square);
        return ResponseEntity
                .ok()
                .header("content-type", "application/json")
                .body(response.getJsonPayload());
    }
}
