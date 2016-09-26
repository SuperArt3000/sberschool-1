package ru.sbt.lesson2;

/**
 * Created by Yrwing on 14.09.2016.
 */
public class Person {
    private final boolean man;
    private final String name;
    private Person spouse;
    public Person(boolean man, String name) {
        this.man = man;
        this.name = name;
    }

    /**

          * This method checks gender of persons. If genders are not equal - tries to marry.
          * If one of them has another spouse - execute divorce(sets spouse = null for husband and wife.
     Example: if both persons have spouses - then divorce will set 4 spouse to null) and then executes
     marry().

          * @param person - new husband/wife for this person.

          * @return - returns true if this person has another gender than passed person and they are not
     husband and wife, false otherwise
     */
    public  boolean marry(Person person){
        if(this.man != person.man) {
            if (this.spouse != null) this.divorce();
            else if (person.spouse != null) person.divorce();
            this.spouse = person;
            person.spouse = this;
            return true;
        }
        else return  false;
    }

    /**
     * Sets spouse = null if spouse is not null
     * @return true - if person status has been changed
     */

     public boolean divorce() {
         if(this.spouse != null){
             Person cur = this.spouse;
             System.out.println(cur == (this.spouse));
             this.spouse.spouse = null;
             this.spouse = null;
             return true;
     }
         return false;
     }

    @Override
    public boolean equals(Object obj) throws NullPointerException {
        try {
            if (obj == this)
                return true;
            if ((!(obj instanceof Person)))
                return false;
            Person current = (Person) obj;
            return (this.name.equals(current.name) && (this.man == current.man));
        }
        catch (NullPointerException n){
            return false;
        }
    }

    @Override
    public int hashCode(){
        int hash = 0;

        if(man)
            hash = 1;
        hash =  31*hash + name.hashCode();
        if (this.spouse != null)
            if(this.spouse.man)
                hash = 31*hash + 31 + this.spouse.name.hashCode();
                else
                hash = 31*hash + this.spouse.name.hashCode();
        return hash;
    }

}
