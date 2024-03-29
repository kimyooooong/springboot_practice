package com.kimyoong.practice.exception;

import com.kimyoong.practice.common.RestResponse;
import com.kimyoong.practice.component.SlackSender;
import com.kimyoong.practice.domain.CommonMap;
import com.kimyoong.practice.utill.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final SlackSender slackSender;

    //서비스에서 오류나는 부분 처리.
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<RestResponse> ServiceException(Exception e){

        String errorMsg = ExceptionUtils.getStackTrace(e);
        log.error(errorMsg);
        slackSender.sendMessage(errorMsg);
        CommonMap map = new CommonMap();
        String msg = e.getMessage();
        map.put("msg", msg);
        return ResponseEntity.ok(RestResponse.builder().success(false).data(map).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> intervalException(Exception e){

        String errorMsg = ExceptionUtils.getStackTrace(e);
        log.error(errorMsg);
        slackSender.sendMessage(errorMsg);
        return ResponseEntity.internalServerError()
                .body(RestResponse.builder().success(false)
                    .data(errorMsg)
                    .build());

    }


}
