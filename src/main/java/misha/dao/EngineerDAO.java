package misha.dao;

import misha.domain.Ticked;

import java.util.List;

public interface EngineerDAO {
    List<Ticked> ticketsCreatedByAllEmployeesAndManagersInStatusApproved(String login);
}
