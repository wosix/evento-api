package com.wojtek.evento.exceptions;

public class EException extends RuntimeException {
    public EException(String mex) {
        super(mex);
    }

    public EException(String mex, Exception exc) {
        super(mex, exc);
    }
}
