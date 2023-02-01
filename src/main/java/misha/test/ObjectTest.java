package misha.test;

public class ObjectTest implements Comparable<ObjectTest> {

    private String srt1;
    private String str2;


    public ObjectTest(String srt1, String str2) {
        this.srt1 = srt1;
        this.str2 = str2;
    }

    public String getSrt1() {
        return srt1;
    }

    public void setSrt1(String srt1) {
        this.srt1 = srt1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    @Override
    public int compareTo(ObjectTest o) {
        return srt1.compareTo(o.getSrt1());
    }

    @Override
    public String toString() {
        return "ObjectTest{" +
                "srt1='" + srt1 + '\'' +
                ", str2='" + str2 + '\'' +
                '}';
    }
}
