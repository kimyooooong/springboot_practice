package com.kimyoong.practice.component;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

@Log4j2
@Component
public class SlackSender {

    @Value("${slack.use}")
    Boolean use;
    @Value("${slack.token}")
    String token;
    @Value("${slack.channel.error.monitoring}")
    String channel;

    @Value("${spring.profiles.active}")
    String active;

    final Integer CAPACITY = 10;

    final Integer SLACK_MESSAGE_DELAY = 2 * 1000;

    final Integer MESSAGE_MAX_SIZE = 3000;

    ArrayBlockingQueue<String> slackBlockingQueue = new ArrayBlockingQueue<>(CAPACITY);

    @PostConstruct
    public void init(){
        log.info("slackSender init");
        slackBlockingQueue = new ArrayBlockingQueue<>(CAPACITY);
        startingQueue();
    }

    public synchronized void sendMessage(String message){

        if(!use){
            log.info("slack not using!!");
            return;
        }
        try {
            if(slackBlockingQueue.size() < CAPACITY) {

                String msg = "[ " + active.toUpperCase() + " ] " + ( StringUtils.isNotEmpty(message) && message.length() > MESSAGE_MAX_SIZE
                        ? message.substring(0 , MESSAGE_MAX_SIZE) + "...." : message );

                slackBlockingQueue.put(msg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startingQueue(){

        new Thread(() -> {
                try {
                    while(true) {
                        String meessage = slackBlockingQueue.take();

                        MethodsClient methods = Slack.getInstance().methods(token);

                        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                                .channel(channel)
                                .text(meessage)
                                .build();

                        methods.chatPostMessage(request);
                        log.info("success send message");
                        Thread.sleep(SLACK_MESSAGE_DELAY);
                    }
                } catch (SlackApiException | IOException | InterruptedException e) {
                    log.error(e.getMessage());
                }
        }).start();
    }


}
