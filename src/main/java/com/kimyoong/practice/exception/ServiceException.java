package com.kimyoong.practice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceException extends RuntimeException{

    public ServiceException (String msg){
        super(msg);
    }
}
