package misha.domain;

public enum Urgency    {
    CRITICAL("1"),
    HIGHT("2"),
    AVEREGE("3"),
    LOW("4");

    private  String catt;

    Urgency(String catt) {
        this.catt = catt;
    }

    public String getCatt() {
        return catt;
    }

}




