package mchehab.com.firebasedemo;

import org.parceler.Parcel;

/**
 * Created by muhammadchehab on 1/10/18.
 */
@Parcel
public class Person {

    private String key;
    private String firstName;
    private String lastName;
    private int age;

    public Person(){

    }

    public Person(String firstName, String lastName, int age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!Person.class.isAssignableFrom(object.getClass()))
            return false;
        final Person person = (Person)object;
        return person.getKey().equals(key);
    }
}
