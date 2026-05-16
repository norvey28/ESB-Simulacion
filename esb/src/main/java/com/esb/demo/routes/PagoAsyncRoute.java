package com.esb.demo.routes;

import com.esb.demo.dto.PagoRequest;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PagoAsyncRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:procesarPagoAsync")

            .log("Publicando pago en RabbitMQ: ${body}")

            .marshal().json()

            .to(
                "spring-rabbitmq:pagos.exchange" +
                "?queues=pagos.queue" +
                "&routingKey=pagos.routing"
            )

            .log("Mensaje enviado a RabbitMQ");
    }
}