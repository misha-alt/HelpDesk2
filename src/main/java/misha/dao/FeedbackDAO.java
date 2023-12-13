package misha.dao;

import misha.domain.FeedBack;

public interface FeedbackDAO {
    void saveRate(FeedBack feedBack);
    void updateRate(FeedBack feedBack);
    public FeedBack getAllRate(int id);


}
