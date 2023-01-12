package com.divary.apigateway.config;

import com.divary.dto.BaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class Filter extends AbstractGatewayFilterFactory<Filter.Config>{

    @Autowired
    private ObjectMapper objectMapper;

    private final WebClient.Builder webClientBuilder;

    public Filter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errDetails, HttpStatus httpStatus) {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        try {
            response.getHeaders().add("Content-Type", "application/json");
            BaseResponse<Object> baseResponse = BaseResponse.builder()
                    .code(httpStatus.value())
                    .status(httpStatus.getReasonPhrase())
                    .message(errDetails)
                    .data(null)
                    .build();

            byte[] byteData = objectMapper.writeValueAsBytes(baseResponse);
            return response.writeWith(Mono.just(byteData).map(dataBufferFactory::wrap));

        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
        return response.setComplete();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String bearerToken = request.getHeaders().getFirst("Authorization");

            return webClientBuilder.build().get()
                    .uri("lb://authentication-service/authentication-service/api/v1/auth/validate-token")
                    .header("Authorization", bearerToken)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .map(resp -> exchange)
                    .flatMap(chain::filter)
                    .onErrorResume(error -> onError(exchange, "Token Not Valid", HttpStatus.UNAUTHORIZED));
        };
    }

    public static class Config {
    }

}
