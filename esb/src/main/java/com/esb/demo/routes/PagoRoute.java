package com.esb.demo.routes;

import com.esb.demo.dto.PagoRequest;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PagoRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:procesarPago")

                .process(exchange -> {

                    PagoRequest body = exchange.getMessage()
                            .getBody(PagoRequest.class);

                    String soapBody = """
                            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                              xmlns:leg="http://legacy.com/soap">
                               <soapenv:Header/>
                               <soapenv:Body>
                                  <leg:procesarPagoRequest>
                                     <referencia>%s</referencia>
                                     <valor>%s</valor>
                                  </leg:procesarPagoRequest>
                               </soapenv:Body>
                            </soapenv:Envelope>
                            """
                            .formatted(
                                    body.getReferencia(),
                                    body.getValor());

                    exchange.getMessage().setBody(soapBody);

                    exchange.getMessage().setHeader(
                            "Content-Type",
                            "text/xml;charset=UTF-8");
                })

                .to("http://legacy-soap:8080/ws")

                .convertBodyTo(String.class)

                .log("Respuesta SOAP: ${body}");
    }
}