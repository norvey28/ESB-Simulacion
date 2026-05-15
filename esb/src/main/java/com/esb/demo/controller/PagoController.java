package com.esb.demo.controller;

import com.esb.demo.dto.PagoRequest;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final ProducerTemplate producerTemplate;

    public PagoController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @PostMapping
    public String procesar(@RequestBody PagoRequest request) {

        return producerTemplate.requestBody(
                "direct:procesarPago",
                request,
                String.class
        );
    }
}