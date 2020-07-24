package org.jack.stringcalculator.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jack.stringcalculator.service.CalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/string")
public class StringCalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping("/sum")
    public Mono<Integer> calculateProvidedString(@RequestBody
                                                         StringCalculatorRequest request) {
        return calculatorService.sum(request.getProvidedString());
    }

    @Data
    private static class StringCalculatorRequest {
        private String providedString;
    }
}
