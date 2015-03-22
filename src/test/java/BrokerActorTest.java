import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.TestActorRef;
import at.segv.akka.bootstrap.BrokerActor;
import at.segv.play.broker.api.PutOrder;
import at.segv.play.broker.api.Tick;
import org.junit.Test;
import scala.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * Created by mat on 2/28/15.
 */
public class BrokerActorTest {

    @Test
    public void testAnalyzeSentence() throws Exception {

        final Props props = Props.create(BrokerActor.class, "test", "tesxt");
        final TestActorRef<BrokerActor> ref = TestActorRef.create(ActorSystem.create(), props, "testA");

        Tick message = new Tick(1,1000,1,1);
        Future<Object> future = Patterns.ask(ref, message, 1000);
        Object answer = future.value().get().get();
        assertEquals(PutOrder.class, answer.getClass());

    }
}
