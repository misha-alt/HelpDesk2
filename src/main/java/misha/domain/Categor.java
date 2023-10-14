package misha.domain;

public enum Categor {
    APPLICATIONANDSERVICES("Application & Services"),
    BENEFITSANDPAPERWORC("Benefits & Paper Work"),
    HARDWAREANDSOFTWARE("Hardware & Software"),
	 PEOPLEANDMANAGEMENT("People Management"),
	 SECURITYANDACCESS("Security & Access"),
	 WORKPLACESANDFACILITIES("Workplaces & Facilities");


    public String call;

    Categor(String call) {
        this.call = call;
    }

    public String getCall() {
        return call;
    }
}
