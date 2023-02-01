package misha.test;

public class Student  {

    private int age;
    private String name;

    public Student( String name, int age) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  /*  @Override
    public int compareTo(Student o) {
        {
            return this.name.compareTo(o.getName());
        }
    }*/

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
