package ch.florianluescher.salary;

import ch.florianluescher.salary.bank.Bank;
import ch.florianluescher.salary.hrsystem.EmployeeRecord;
import ch.florianluescher.salary.hrsystem.HRSystem;
import ch.florianluescher.salary.timetracker.TimeTracker;
import ch.florianluescher.salary.timetracker.TimeTrackingInformation;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ch.florianluescher.salary.hrsystem.SalaryType.HOURLY;
import static ch.florianluescher.salary.hrsystem.SalaryType.MONTHLY;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class SalarySystemTest {

    private final SalarySystem salarySystem;

    private static final Bank bankMock = mock(Bank.class);
    private static final HRSystem hrSystemMock = mock(HRSystem.class);
    private static final TimeTracker timeTrackerMock = mock(TimeTracker.class);
}
