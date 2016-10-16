import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javafx.scene.input.KeyCode.T;

public class Streams<T> {



    private Collection<T> dataCollection;
    public final Collection<T> getDataCollection() {
        return dataCollection;
    }
    private  Streams (Collection<T> it){
        this.dataCollection = it;
    }

    /**
     * Возвращает Streams с элементами из указанной коллекции
     *
     * @param collection - source collection
     * @param <T> type of elements
     * @return a new Stream
     */
    static <T> Streams<T> of(Collection<? extends T> collection){
        return new Streams(collection);
    }

    /**
     *
     * @param predicate
     * @return Streams of elements, for which predicate returns true.
     */
    Streams<T> filter(Predicate<? super T> predicate){
                dataCollection.removeIf(t -> !predicate.test(t));
        return Streams.of(this.getDataCollection());
    }

    /**
     * Аналог метода map в Stream.
     *
     * @param function = mapper in Stream
     * @param <R> type of returned Streams elements
     * @return the new Streams
     * @throws Exception
     */
    final <R> Streams<R> transform(Function<? super T,? extends R> function)throws Exception{
        Collection<R> rCollection = dataCollection.getClass().newInstance();
        dataCollection.forEach((t -> rCollection.add(function.apply(t))));
        return  Streams.of(rCollection);
    }

    /**
     *
     * @param identity
     * @param accumulator
     * @return
     */
    final T reduce(T identity, BinaryOperator<T> accumulator){
        T result = identity;
        for (T item : dataCollection)
        result = accumulator.apply(result, item);
        return result;
    }

    /**
     *
     * @param keyGen Generates key from stream element.
     * @param valueGen Generates Map value from stream element, corresponding to the key.
     * @param <K> type of Map keys
     * @param <V> type of Map values
     * @return HashMap<K,V> (not a Map!)
     */
    final <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyGen, Function<? super T, ? extends V> valueGen) {
        Map<K, V> result = new HashMap<K, V>();
        dataCollection.forEach(t -> result.put(keyGen.apply(t), valueGen.apply(t)));
        return result;
    }

}
