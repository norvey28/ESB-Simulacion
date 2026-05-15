package com.esb.demo.controller;

import com.esb.demo.dto.PagoRequest;
import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos-async")
public class PagoAsyncController {

    private final ProducerTemplate producerTemplate;

    public PagoAsyncController(
            ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @PostMapping
    public String procesar(
            @RequestBody PagoRequest request) {

        producerTemplate.sendBody(
                "direct:procesarPagoAsync",
                request);

        return "Pago enviado a procesamiento async";
    }
}