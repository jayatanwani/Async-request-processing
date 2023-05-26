package org.example.consumer;

import org.example.utils.AbstractTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class PublishMessageToSocket {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublishMessageToSocket.class);
    AbstractTaskHandler abstractTaskHandler = new AbstractTaskHandler();

    public PublishMessageToSocket(){
        //default constructor
    }

    public void startConsumer(Integer worker,Integer noOfMessagesToPublish, ExecutorService consumerExecutorService){
        System.out.println("start consumer for worker "+worker);
        publishEventsToSocket(worker, noOfMessagesToPublish,consumerExecutorService);
    }

    private void publishEventsToSocket(Integer worker, Integer noOfMessagesToPublish,ExecutorService consumerExecutorService){
        for(int j=1;j<=noOfMessagesToPublish;j++){
            int finalJ = j;
            abstractTaskHandler.handleTask(() -> publishMessage(worker, finalJ),consumerExecutorService);
        }
    }

    public void publishMessage(Integer worker, Integer messageId){
        System.out.println("message "+messageId+" published for "+ worker + " using thread "+Thread.currentThread().getName());
        LOGGER.info("message {} published for {} using thread {}",messageId,worker,Thread.currentThread().getName());
    }
}
