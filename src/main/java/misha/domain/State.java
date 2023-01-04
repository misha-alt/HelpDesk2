package misha.domain;

import org.springframework.stereotype.Service;

import javax.persistence.Enumerated;

public enum State {
    DRAFT("DRAFT"),
    NEW("NEW"),
    APPROVED("APPROVED"),
    DECLINED("DECLINED"),
    INPROGRESS("INPROGRESS"),
    DONE("DONE"),
    CANCELED("CANCELED");

    private final String cat;

    State(String cat) {
        this.cat = cat;
    }

    public String getCat() {
        return cat;
    }
}


