package com.divary.carsearchservice.event.ampq;

import org.springframework.amqp.core.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BaseListener {

    private static final String DEATH_LETTER_HEADER = "x-death";
    private static final String EXCHANGE = "exchange";
    private static final String ROUTING_KEYS = "routing-keys";
    private static final String REASON = "reason";
    private static final String COUNT = "count";

    private static final String REASON_REJECTED = "rejected";
    private static final String REASON_EXPIRED = "expired";

    protected boolean isStillAvailableConsume(Message payload, String exchange, String routingKey, int rejected, int expired) {

        if (payload.getMessageProperties().getHeaders() == null){
            return true;
        }
        try {
            List<HashMap> header = (ArrayList) payload.getMessageProperties().getHeaders().get(DEATH_LETTER_HEADER);
            List<HashMap> filterHeader = header
                    .stream()
                    .filter(s -> s.get(EXCHANGE).equals(exchange) && ((ArrayList) s.get(ROUTING_KEYS)).get(0).equals(routingKey)
                            && (s.get(REASON).equals(REASON_EXPIRED) || s.get(REASON).equals(REASON_REJECTED)))
                    .collect(Collectors.toList());

            List<HashMap> filterRejected = filterHeader
                    .stream()
                    .filter(s -> s.get(REASON).equals(REASON_REJECTED))
                    .collect(Collectors.toList());
            Long rejectedCount = !filterRejected.isEmpty() ? (Long) filterRejected.get(0).get(COUNT) : null;

            List<HashMap> filterExpired = filterHeader
                    .stream()
                    .filter(s -> s.get(REASON).equals(REASON_EXPIRED))
                    .collect(Collectors.toList());
            Long expiredCount = !filterExpired.isEmpty() ? (Long) filterExpired.get(0).get(COUNT) : null;

            return (rejectedCount != null && rejectedCount < rejected) || (expiredCount != null && expiredCount < expired);
        } catch (Exception ex) {
            return true;
        }
    }
}
