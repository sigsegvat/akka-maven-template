import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.TestActorRef;
import at.segv.akka.bootstrap.EchoActor;
import org.junit.Test;
import scala.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * Created by mat on 2/28/15.
 */
public class EchoActorTest {

    @Test
    public void testAnalyzeSentence() throws Exception {

        final Props props = Props.create(EchoActor.class);
        final TestActorRef<EchoActor> ref = TestActorRef.create(ActorSystem.create(), props, "testA");

        String message = "The quick brown fox jumps over the lazy dog";
        Future<Object> future = Patterns.ask(ref, message, 1000);
        Object answer = future.value().get().get();
        assertEquals("echo: "+message, answer);

    }
}
