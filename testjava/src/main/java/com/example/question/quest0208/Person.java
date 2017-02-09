package com.example.question.quest0208;

/**
 * Package com.example.question.quest0208.
 * Created by yaoh on 2017/02/09.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class Person {
    private String id;
    private String name;

    public Person(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Person) {
            Person person = (Person) o;
            return (id.equals(person.id) && name.equals(person.name));
        }
        return false;
    }
}
