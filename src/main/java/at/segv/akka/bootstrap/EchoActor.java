package at.segv.akka.bootstrap;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EchoActor extends UntypedActor {

    static Logger LOGGER = LoggerFactory.getLogger(EchoActor.class);

    public static Props props() {
        return Props.create(EchoActor.class, new Creator<EchoActor>() {
            @Override
            public EchoActor create() throws Exception {
                return new EchoActor();
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof String){
            LOGGER.info("got message: " + message);
            getSender().tell("echo: "+message,getSelf());
        }

    }

}
