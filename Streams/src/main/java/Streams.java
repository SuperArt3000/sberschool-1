import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


public class Streams<T> {



     private  Collection<T> source;
    private Collection<Optional<T>> dataCollection = new ArrayList<>();
    private  Queue<Function> taskQueue = new ArrayDeque<>();

    private  Streams (Collection<T> collection){
        source = collection;
    }

    /**
     * Возвращает Streams с элементами из указанной коллекции
     *
     * @param collection - source collection
     * @param <T> type of elements
     * @return a new Stream
     */
    @SuppressWarnings("unchecked")
    static <T> Streams<T> of(Collection<? extends T> collection){
        return new Streams(collection);
    }

    /**
     *
     * @return Streams of elements, for which predicate returns true.
     */
    public Streams<T> filter(Predicate<? super T> predicate){
        Function<Optional<T>, Optional<T>> filterValue = new Operation<Optional<T>, Optional<T>>(){
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
    final <R> Streams<T> transform(Function<? super T, ? extends R> transformator)throws Exception{
        Function<Optional<T>, Optional<R>> transformOperation = new Operation<Optional<T>, Optional<R>>(){
            @Override
            public Optional<R> apply(Optional<T> value){
                return(value.isPresent())? Optional.ofNullable(transformator.apply(value.get())) : null;
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
    @SuppressWarnings("unchecked")
    final <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyGen, Function<Object, ? extends V> valueGen) {
        Map<K, V> result = new HashMap<K, V>();Inter
        source.forEach(t ->{
            Iterator<Function> iterator = taskQueue.iterator();
            Optional<> processedItem = Optional.ofNullable(t);
            while(iterator.hasNext()) {
                processedItem = Optional.ofNullable(iterator.next().apply(processedItem));
            }
            if (processedItem.isPresent()) {
                result.put(keyGen.apply(processedItem.get()), valueGen.apply(processedItem.get()));
            }
        });
        return result;
    }


    private class Operation<E, R> implements Function<E,R>{
        @Override
        public R apply(E value){
            return null;
        }

    };
}
