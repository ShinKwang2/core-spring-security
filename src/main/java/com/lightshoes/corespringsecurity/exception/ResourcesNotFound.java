package com.lightshoes.corespringsecurity.exception;

public class ResourcesNotFound extends CustomException {

    private static final String MESSAGE = "해당하는 Resources가 없습니다.";

    public ResourcesNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
