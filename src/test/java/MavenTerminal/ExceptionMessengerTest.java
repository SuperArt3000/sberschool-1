package MavenTerminal;

import org.junit.rules.Timeout;
import org.junit.runners.Parameterized;

import javax.security.auth.login.AccountLockedException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


/**
 * Created by Yrwing on 09.10.2016.
 */
public class ExceptionMessengerTest {

    @Parameterized.Parameter
         private InputStream s = new ByteArrayInputStream("0 o i u p".getBytes(StandardCharsets.UTF_8));


    @Rule
    public final Timeout timeout = new Timeout(6000);

    @Test(expected = AccountLockedException.class)
    public void testAccessBlock()throws AccountLockedException{
        ExceptionsMessenger.AccessBlock(s);
    }

}
