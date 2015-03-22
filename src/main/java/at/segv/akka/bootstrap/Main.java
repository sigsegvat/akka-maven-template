package at.segv.akka.bootstrap;

import akka.actor.*;

import akka.actor.dsl.Creators;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Main {

    static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) throws IOException {


        ActorSystem botnet = ActorSystem.create("botnet");
        ActorRef actorRef = botnet.actorOf(BrokerActor.props(args[0], args[1]),"BrokerActor");
        LOGGER.info("created Actor: "+actorRef);


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while(! "exit".equals(input= br.readLine()) ){ }

        botnet.shutdown();


    }


}

