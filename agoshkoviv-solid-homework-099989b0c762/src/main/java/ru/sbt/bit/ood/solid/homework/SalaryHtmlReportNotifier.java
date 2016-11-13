package ru.sbt.bit.ood.solid.homework;


import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier {

    private Connection connection;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        SalaryHtmlReportGenerator reportGenerator = new SalaryHtmlReportGenerator(connection);
        StringBuilder resultingHtml = reportGenerator.generateSalaryReport(departmentId, dateFrom, dateTo);
        SalaryHtmlReportSender reportSender = new SalaryHtmlReportSender(recipients, resultingHtml);
        reportSender.sendSalaryReport();
    }
}
