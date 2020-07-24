package org.jack.stringcalculator.constants;

public enum ErrorMessages {
    INPUT_NUMBERS_ARE_MORE_THAN_TWO("The amount of inputs is greater than 2. Please correct it."),
    INPUT_NUMBERS_IS_NOT_NUMERIC("The input is not numeric type, please verify and fix your input."),
    INPUT_NUMBERS_HAVE_NEGATIVE_NUMBER("The input contains the negative numbers - \"{0}\", please verify and fix your input."),
    ;

    private final String msg;

    private ErrorMessages(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }

    public String getMessage() {
        return msg;
    }
}
