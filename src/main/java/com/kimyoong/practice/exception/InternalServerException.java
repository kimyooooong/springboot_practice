package com.kimyoong.practice.exception;

public class InternalServerException extends RuntimeException{

    public InternalServerException(String msg){
        super(msg);
    }

    public InternalServerException(Exception e){
        super(e);
    }
}
