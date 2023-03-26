package com.example.timer.enums;

public enum ButtonState {

    STOPPED("Iniciar"),
    RUNNING("Parar"),
    UNDEFINED("Undefined");

    private final String message;

    ButtonState(String state) {
        this.message = state;
    }

    public static ButtonState from(String state) {
        switch (state){
            case "start": return STOPPED;
            case "stop": return RUNNING;
            default: return UNDEFINED;
        }
    }

    public String getMessage() {
        return message;
    }
}
