package com.lightshoes.corespringsecurity.exception;

public class AccountNotFound extends CustomException {

    private static final String MESSAGE = "해당하는 Account가 없습니다.";

    public AccountNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
