package at.segv.akka.bootstrap;

import akka.actor.*;

import akka.actor.dsl.Creators;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Main {

    static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) throws IOException {


        ActorSystem botnet = ActorSystem.create("botnet");
        ActorRef actorRef = botnet.actorOf(EchoActor.props(),"EchoActor");
        LOGGER.info("created EchoActor: "+actorRef);


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while(! "exit".equals(input= br.readLine()) ){
            ActorSelection remoteactor = botnet.actorSelection(input);
            askEcho(remoteactor, "hello");
            remoteactor.tell(PoisonPill.getInstance(),null);
        }

        botnet.shutdown();


    }

    private static void askEcho(ActorSelection actorRef, String message) {
        Timeout timeout = new Timeout(1, TimeUnit.SECONDS);

        Future<Object> hello = Patterns.ask(actorRef, message, timeout);

        try {
            String result = (String) Await.result(hello, timeout.duration());
            LOGGER.info(result);
        } catch (Exception e) {
            LOGGER.info("no response from "+actorRef);
        }
    }

}

