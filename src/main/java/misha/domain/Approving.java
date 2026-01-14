package misha.domain;

public enum Approving {
    YES("YES"),
    NO("NO");


    public String turn;

    Approving(String turn) {
        this.turn = turn;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }


}
