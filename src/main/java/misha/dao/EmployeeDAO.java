package misha.dao;

import misha.domain.Ticked;

import java.util.List;

public interface EmployeeDAO {
    List<Ticked> allTiscedCreatedByEmployee (String login);
}
