package at.segv.akka.bootstrap;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import at.segv.play.broker.api.CallOrder;
import at.segv.play.broker.api.PutOrder;
import at.segv.play.broker.api.Register;
import at.segv.play.broker.api.Tick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BrokerActor extends UntypedActor {

    static Logger LOGGER = LoggerFactory.getLogger(BrokerActor.class);
    private String name;
    private String ref;

    public BrokerActor(String name, String ref){

        this.name = name;
        this.ref = ref;
    }

    public static Props props(final String name, final String ref) {
        return Props.create(BrokerActor.class, new Creator<BrokerActor>() {
            @Override
            public BrokerActor create() throws Exception {
                return new BrokerActor(name,ref);
            }
        });
    }

    @Override
    public void preStart(){
        register();
    }

    private void register() {
        context().actorFor(ref).tell(new Register("test"+Math.random()),self());
    }

    @Override
    public void onReceive(Object message) throws Exception {
       LOGGER.info(message.toString());
        if(message instanceof Tick){
            sender().tell(new PutOrder(1),self());
        }
                // api classes are Tick, Register(name), PutOrder(int), CallOrder(int)

    }

}
