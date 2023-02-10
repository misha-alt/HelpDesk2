package misha.test;

public class ObjectTest  {

    private SomeEnum someEnum;


    public ObjectTest(SomeEnum someEnum) {
        this.someEnum = someEnum;
    }

    public SomeEnum getSomeEnum() {
        return someEnum;
    }

    public void setSomeEnum(SomeEnum someEnum) {
        this.someEnum = someEnum;
    }



    @Override
    public String toString() {
        return "ObjectTest{" +
                "someEnum=" + someEnum +
                '}';
    }
}
