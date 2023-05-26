package org.example;

import org.example.producer.EstablishWebsocketConnections;
import org.example.utils.ThreadPoolExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        final Logger LOGGER = LoggerFactory.getLogger(App.class);

        ExecutorService producerExecutorService = ThreadPoolExecutors.createEventExecutor("eb_load_test_producer",
                (t, e) -> LOGGER.error("UncaughtException while establishing connection to socket.", e),5,20);
        ExecutorService consumerExecutorService = ThreadPoolExecutors.createEventExecutor("eb_load_test_consumer",
                (t, e) -> LOGGER.error("UncaughtException while publishing event to socket.", e),10,20);
        new EstablishWebsocketConnections(10,10, producerExecutorService,consumerExecutorService);

    }
}
