package com.lightshoes.corespringsecurity.exception;

public class RoleNotFound extends CustomException {

    private static final String MESSAGE = "해당하는 ROLE이 없습니다.";

    public RoleNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
