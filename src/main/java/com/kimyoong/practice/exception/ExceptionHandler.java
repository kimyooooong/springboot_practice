package com.kimyoong.practice.exception;

import com.kimyoong.practice.component.SlackSender;
import com.kimyoong.practice.utill.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandler {

    private final SlackSender slackSender;

    //404 페이지 낫 파운드 , 혹은 잘못된 접근 ( POST 요청인데 Get 으로한경우 등등. )
    @org.springframework.web.bind.annotation.ExceptionHandler({
            PgNotFoundException.class , NoHandlerFoundException.class ,
            HttpRequestMethodNotSupportedException.class  ,
            MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String error404(Locale locale){
        log.info("404 {}", locale);
        return "";
    }


    //서버에서 오류난경우 오류페이지로 리턴시킬때 사용.( 500 에러페이지 리턴! )
    @org.springframework.web.bind.annotation.ExceptionHandler(InternalServerException.class)
    public String InternalSererException(Exception e , Locale locale){
        String errorMsg = ExceptionUtils.getStackTrace(e);
        log.error(errorMsg);
        slackSender.sendMessage(errorMsg);
        return "";
    }


    //그외 심각한 오류들
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String intervalException(Exception e , Locale locale){

        String errorMsg = ExceptionUtils.getStackTrace(e);
        log.error(errorMsg);
        slackSender.sendMessage(errorMsg);
        return "";
    }

}
