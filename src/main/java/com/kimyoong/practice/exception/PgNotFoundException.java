package com.kimyoong.practice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PgNotFoundException extends RuntimeException{

    public PgNotFoundException(String msg){
        super(msg);
    }

}
