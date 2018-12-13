package name.songhui.concurr.learning.bean;

public class Person {
    public String name;
    public int age;

    public Person() {
        super();
    }

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String toString()
    {
        return "name:"+name+","+"age:"+age;
    }
}