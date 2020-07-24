package org.jack.stringcalculator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/server")
public class ServerController {
    private static InetAddress local;

    static {
        try {
            local = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.error("Unable to get localhost info.", e);
        }
    }

    @GetMapping
    public Mono<String> calculateProvidedString() {
        var serverInfo = MessageFormat.format("Host name: {0}; IP: {1}", local.getHostName(), local.getHostAddress());
        return Mono.just(serverInfo);
    }
}
