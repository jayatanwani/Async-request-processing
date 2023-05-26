package org.example.producer;

import org.example.consumer.PublishMessageToSocket;
import org.example.utils.AbstractTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;


public class EstablishWebsocketConnections {

    Integer noOfWorkers;
    Integer noOfMessagesToPublish;
    ExecutorService producerExecutorService;
    ExecutorService consumerExecutorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EstablishWebsocketConnections.class);

    PublishMessageToSocket publishMessageToSocket;

    AbstractTaskHandler abstractTaskHandler = new AbstractTaskHandler();

    HashMap<String,String> workerToken = new HashMap<>();

    public EstablishWebsocketConnections(){
        //default constructor
    }

    public EstablishWebsocketConnections(Integer noOfWorkers, Integer noOfMessagesToPublish, ExecutorService producerExecutorService, ExecutorService consumerExecutorService){
        this.noOfWorkers = noOfWorkers;
        this.noOfMessagesToPublish = noOfMessagesToPublish;
        this.producerExecutorService = producerExecutorService;
        this.consumerExecutorService = consumerExecutorService;
        publishMessageToSocket = new PublishMessageToSocket();
        startProducer();
    }

    public void startProducer(){
        startCreatingWorkers();
    }

    public void startCreatingWorkers(){
        for(int i=1; i<=noOfWorkers;i++){
            int finalI = i;
            abstractTaskHandler.handleTask(() -> establishConnection(finalI),producerExecutorService);
        }
    }

    public void establishConnection(Integer worker){
        System.out.println("websocket connection established for "+ worker + " using thread "+Thread.currentThread().getName());
        LOGGER.info("websocket connection established for {} using thread {}",worker,Thread.currentThread().getName());
        publishMessageToSocket.startConsumer(worker,noOfMessagesToPublish,consumerExecutorService);
    }

}
