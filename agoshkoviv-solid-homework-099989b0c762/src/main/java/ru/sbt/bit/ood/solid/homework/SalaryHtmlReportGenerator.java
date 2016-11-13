package ru.sbt.bit.ood.solid.homework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalaryHtmlReportGenerator {

    private Connection connection;
    private final StringBuilder resultingHtml;

    public SalaryHtmlReportGenerator(Connection databaseConnection) {
        this.connection = databaseConnection;
        this.resultingHtml = new StringBuilder();
    }

    public StringBuilder generateSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo) {
        try {
            PreparedStatement ps = createQueryWithParam(departmentId, dateFrom, dateTo);
            ResultSet results = ps.executeQuery();
            generateHtmlReport(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.resultingHtml;
    }
    private PreparedStatement createQueryWithParam(String departmentId, LocalDate dateFrom, LocalDate dateTo)throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select emp.id as emp_id, emp.name as amp_name," +
                " sum(salary) as salary from employee emp left join" +
                "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
        ps.setString(0, departmentId);
        ps.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
        ps.setDate(2, new java.sql.Date(dateTo.toEpochDay()));
        return ps;
    }

    /**
     * Generates a HTML page with table representing employees' salaries.
     * @param results
     * @throws SQLException
     */
    private void generateHtmlReport(ResultSet results) throws SQLException{
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
            double totals = 0;
            while (results.next()) {
                addNewEmployee(results.getString("emp_name"), results.getDouble("salary"));
                totals += results.getDouble("salary"); // add salary to totals
            }
            addTotalRow(totals);
        resultingHtml.append("</table></body></html>");
    }

    private void addTotalRow(double totals){
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
    }
        private void addNewEmployee(String name, double salary){
            resultingHtml.append("<tr>"); // add row start tag
                appendName(name);
                appendSalary(salary);
            resultingHtml.append("</tr>"); // add row end tag
        }
        private void appendName(String name){
            resultingHtml.append("<td>").append(name).append("</td>");
        }

        private void appendSalary(double salary){
            resultingHtml.append("<td>").append(salary).append("</td>");
        }
}
