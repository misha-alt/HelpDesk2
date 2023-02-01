package misha.test;

public enum SomeEnum {
    CRITICAL("3"),
    HIGHT("2"),
    LOW("1");

   private  String cat;

    SomeEnum(String cat) {
        this.cat = cat;
    }

    public String getCat() {
        return cat;
    }

    @Override
    public String toString() {
        return "SomeEnum{" +
                "cat='" + cat + '\'' +
                '}';
    }
}
