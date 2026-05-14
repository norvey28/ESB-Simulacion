package com.legacy.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Endpoint
public class PagosEndpoint {

    private static final String NAMESPACE_URI = "http://legacy.com/soap";

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "procesarPagoRequest"
    )
    @ResponsePayload
    public Source procesarPago(@RequestPayload Source request) {

        String response =
                """
                <ns2:procesarPagoResponse xmlns:ns2="http://legacy.com/soap">
                    <ns2:status>OK</ns2:status>
                    <ns2:codigo>200</ns2:codigo>
                </ns2:procesarPagoResponse>
                """;

        return new StreamSource(new StringReader(response));
    }
}