import org.junit.BeforeClass;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

public class StreamsTest {
    @Parameterized.Parameter
    private Streams<Number> testStreams= Streams.of(new LinkedList<>(Arrays.asList(3, 5, 1, 2, 4, 2, 4 )));


    @org.junit.Test
    public void filter() throws Exception {
        testStreams.filter(t -> (t.intValue())%2 == 0).getDataCollection().forEach(System.out :: println);
    }

    @org.junit.Test
    public void map() throws Exception {
        testStreams.transform(t->t.intValue()*2).getDataCollection().forEach(System.out :: println);
    }

    @org.junit.Test
    public void reduce() throws Exception {
        System.out.println(testStreams.reduce(1, (y ,t) -> t.intValue() + y.intValue()));
    }

    @org.junit.Test
    public void testToMap() throws Exception {
        testStreams.toMap(Number::intValue, Number :: doubleValue).forEach((key, value)->System.out.println(key + "-->" + value));
    }

}