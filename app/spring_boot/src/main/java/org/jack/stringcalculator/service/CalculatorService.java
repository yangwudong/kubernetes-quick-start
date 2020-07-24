package org.jack.stringcalculator.service;

import reactor.core.publisher.Mono;

public interface CalculatorService {
    Mono<Integer> sum(String numbersStr);
}
