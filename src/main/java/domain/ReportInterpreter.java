package domain;


import dao.SalaryNote;

import java.util.Set;

public interface ReportInterpreter {
    double getTotalSalary(Set<SalaryNote> salaryTable);

    void produceReportDocument(Set<SalaryNote> salaryTable);//так можно?

}
