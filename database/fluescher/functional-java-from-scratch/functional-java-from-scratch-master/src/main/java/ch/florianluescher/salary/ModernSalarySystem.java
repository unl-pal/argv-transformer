package ch.florianluescher.salary;

import ch.florianluescher.salary.bank.Bank;
import ch.florianluescher.salary.hrsystem.EmployeeRecord;
import ch.florianluescher.salary.hrsystem.HRSystem;
import ch.florianluescher.salary.hrsystem.SalaryType;
import ch.florianluescher.salary.timetracker.TimeTracker;
import ch.florianluescher.salary.timetracker.TimeTrackingInformation;

public class ModernSalarySystem implements SalarySystem {

    private final Bank bank;
    private final HRSystem hrSystem;
    private final TimeTracker timeTracker;
}
