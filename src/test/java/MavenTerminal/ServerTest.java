package MavenTerminal;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Yrwing on 09.10.2016.
 */
public class ServerTest {

    private static TerminalServer testServer;
    @BeforeClass
    public static void setUpServer(){
        testServer = new TerminalServer();
        testServer.addAccount("name", (short) 0);
    }


    @Test
    public  void testPutMoney() throws TOException,IOException{
        setUpServer();
        testServer.putMoney("name", 1000);
    }

    @Test(expected = IOException.class)
    public  void testGetWrongMoney() throws TOException,IOException,NotEnoughGoldException{
        setUpServer();
        testServer.getMoney("name", 25);
    }

    @Test(expected = NotEnoughGoldException.class)
    public void testGetMoney() throws IOException, NotEnoughGoldException{
        setUpServer();
        testServer.getMoney("name", 100);
    }
}
