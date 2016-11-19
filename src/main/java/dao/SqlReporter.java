package dao;


import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SqlReporter implements Reporter {

    private Connection source;
    private String departmentId;
    private LocalDate dateFrom, dateTo;

    public SqlReporter(Connection source, String departmentID, LocalDate dateFrom, LocalDate dateTo) {
        this.source = source;
        this.departmentId = departmentID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public Set<SalaryNote> buildNewReport() {
        Set<SalaryNote> salaryTable= new HashSet<>();
        try {
            PreparedStatement ps = createQueryWithParam(departmentId, dateFrom, dateTo);
            ResultSet results = ps.executeQuery();
            fillTable(salaryTable, results);
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salaryTable;
    }

    private void fillTable(Set<SalaryNote> salaryTable, ResultSet results) throws SQLException {
        while (results.next()) {
            salaryTable.add(new SalaryNote(results.getString("emp_name"), results.getDouble("salary")));
        }
    }

    private PreparedStatement createQueryWithParam(String departmentId, LocalDate dateFrom, LocalDate dateTo)
            throws SQLException{
        PreparedStatement ps = source.prepareStatement("select emp.id as emp_id, emp.name as amp_name," +
                " sum(salary) as salary from employee emp left join" +
                "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
        ps.setString(0, departmentId);
        ps.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
        ps.setDate(2, new java.sql.Date(dateTo.toEpochDay()));
        return ps;
    }
}
