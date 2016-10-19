import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javafx.scene.input.KeyCode.T;

public class Streams<T> {



    private Collection<Optional<T>> dataCollection = new ArrayList<>();
    private Queue<Operation<Optional<T>>> taskQueue = new ArrayDeque<>();

    private  Streams (Collection<T> it){
        it.forEach(item->dataCollection.add(Optional.of(item)));
    }

    /**
     * Возвращает Streams с элементами из указанной коллекции
     *
     * @param collection - source collection
     * @param <T> type of elements
     * @return a new Stream
     */
    static <T> Streams<T> of(Collection<? extends T> collection){return new Streams(collection);
    }

    /**
     *
     * @param predicate
     * @return Streams of elements, for which predicate returns true.
     */
    public Streams<T> filter(Predicate<? super T> predicate){
        Operation<Optional<T>> filterValue = new Operation<Optional<T>>(){
            @Override
            public Optional<T> apply(Optional<T> value){
                if(value.isPresent()) {
                    return (predicate.test(value.get())) ? value : null;
                }
                else return null;
            }

        };
        taskQueue.add(filterValue);
        return this;
    }

    /**
     * Аналог метода map в Stream.
     *
     * @return the new Streams
     * @throws Exception
     */
    final  Streams<T> transform(Function<? super T, ? extends T> transformator)throws Exception{
        Operation<Optional<T>> transformOperation = new Operation<Optional<T>>(){
            @Override
            public Optional<T> apply(Optional<T> value){
                return(value.isPresent())? Optional.of(transformator.apply(value.get())) : null;
           }
        };
        taskQueue.add(transformOperation);
        return  this;
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
        dataCollection.forEach(t ->{
            Iterator<Operation<Optional<T>>> iterator = taskQueue.iterator();
            Optional<T> processedItem = t;
            while(iterator.hasNext()) {
                processedItem = iterator.next().apply(processedItem);
            }
            if (processedItem.isPresent()) {
                result.put(keyGen.apply(processedItem.get()), valueGen.apply(processedItem.get()));
            }
        });
        return result;
    }


    class Operation<T> implements Function<T,T>{
        @Override
        public T apply(T value){
            return null;
        }

    };
}
