import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainTest {

    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", 22));
        persons.add(new Person("Hamish", 23));
        persons.add(new Person("Watson", 24));
        persons.add(new Person("Jack", 30));
        persons.add(new Person("Sparrow", 32));

        try{
            Map<String, Person> m = Streams.of(persons)
                    .filter(p -> p.getAge() > 20)
                    .transform( p ->new Person(p.getName(), p.getAge() + 30))
                    .toMap(Person :: getName,  p -> p);
            m.forEach((key, value)-> System.out.println(key + "-->" + value.getAge()));
            System.out.println("____Starting list of persons____");
            persons.forEach(p-> System.out.println(p.getName() + "-->" + p.getAge()));
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
