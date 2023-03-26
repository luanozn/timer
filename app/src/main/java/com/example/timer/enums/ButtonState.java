package com.example.timer.enums;

public enum ButtonState {

    START("Iniciar"),
    STOP("Parar"),
    UNDEFINED("Undefined");

    private final String state;

    ButtonState(String state) {
        this.state = state;
    }

    public static ButtonState from(String state) {
        switch (state){
            case "start": return START;
            case "stop": return STOP;
            default: return UNDEFINED;
        }
    }

    public String getState() {
        return state;
    }
}
