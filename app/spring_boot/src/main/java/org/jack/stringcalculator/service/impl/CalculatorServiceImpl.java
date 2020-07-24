package org.jack.stringcalculator.service.impl;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.jack.stringcalculator.service.CalculatorService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.jack.stringcalculator.constants.ErrorMessages.INPUT_NUMBERS_HAVE_NEGATIVE_NUMBER;
import static org.jack.stringcalculator.constants.ErrorMessages.INPUT_NUMBERS_IS_NOT_NUMERIC;

@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {
    private static final Pattern INPUT_REGEX = Pattern.compile("\\/\\/(\\S+)\\n(\\S+)");
    private static final Pattern DELIMITERS_REGEX = Pattern.compile("\\[([^\\[^\\]]+)\\]");
    private static final String SEPARATORS_REGEX = ",|\\n";
    private static final Map<String, Integer> RESULT_CACHE = new ConcurrentHashMap<>();

    public Mono<Integer> sum(String numbersStr) {
        return Mono
                .just(numbersStr)
                .map(str -> {
                    if (!RESULT_CACHE.containsKey(str)) {
                        if (Strings.isNullOrEmpty(str)) {
                            return 0;
                        }
                        var delimiter = SEPARATORS_REGEX;

                        var inputNumbersMatcher = INPUT_REGEX.matcher(str);
                        if (inputNumbersMatcher.find() && inputNumbersMatcher.groupCount() > 1) {
                            var delimitersString = inputNumbersMatcher.group(1);
                            var delimitersMatcher = DELIMITERS_REGEX.matcher(delimitersString);
                            var delimiters = new ArrayList<String>();
                            while (delimitersMatcher.find() && delimitersMatcher.groupCount() > 0) {
                                delimiters.add(Pattern.quote(delimitersMatcher.group(1)));
                            }
                            delimiter = String.join("|", delimiters);
                            str = inputNumbersMatcher.group(2);
                        }

                        var numbers = Arrays.stream(str.split(delimiter))
                                .map(s -> {
                                    try {
                                        return Integer.valueOf(s);
                                    } catch (NumberFormatException e) {
                                        throw new IllegalArgumentException(INPUT_NUMBERS_IS_NOT_NUMERIC.getMessage(), e);
                                    }
                                })
                                .filter(i -> i <= 1000)
                                .collect(Collectors.toList());

                        var negativeNumbers = numbers.stream()
                                .filter(i -> i < 0).collect(Collectors.toList());
                        if (!negativeNumbers.isEmpty()) {
                            throw new IllegalArgumentException(MessageFormat.format(INPUT_NUMBERS_HAVE_NEGATIVE_NUMBER.getMessage(),
                                    negativeNumbers.stream().map(Object::toString)
                                            .collect(Collectors.joining(", "))));
                        }
                        var result = numbers.stream()
                                .reduce(0, Integer::sum);
                        RESULT_CACHE.put(str, result);
                    }
                    return RESULT_CACHE.get(str);
                });
    }
}
