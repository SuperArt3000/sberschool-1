package ru.sbt.MavenTerminal;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;


public class TerminalTest {

    @Mock
    private TerminalServer mockedServer;

 // @Mock
 //   private PinValidator mockedPin;

    @Parameter
    private final Terminal testTerm = new Terminal(mockedServer);

    /**
     * testTerm.PinV : хотим вызвать pinCheck, однако бросается исключение
     * @throws Exception
     */
    @Test
    public void testEnterAccount() throws Exception{
        doReturn(true).when(testTerm.PinV.pinCheck(anyString(), anyShort()));
        doReturn(true).when(mockedServer.getMoney("name", (short)100));
        doReturn(true).when(mockedServer.putMoney("name", (short)100));
        doReturn(0).when(mockedServer.AccountMap.get("name").CheckWallet());
        testTerm.enterAccount(new Scanner(new ByteArrayInputStream("name 0 -get -put".getBytes(StandardCharsets.UTF_8))));
    }
}
