package domain;


import dao.*;

import java.util.Set;

public class HtmlInterpreter implements ReportInterpreter {
    StringBuilder resultingHtml;

    public HtmlInterpreter(StringBuilder resultingHtml) {
        this.resultingHtml = resultingHtml;
    }

    @Override
    public double getTotalSalary(Set<SalaryNote> salaryTable) {
        return salaryTable.stream().mapToDouble(SalaryNote::getSalary).reduce(0, (x, y)->x+y);
    }

    public StringBuilder getResultingHtml() {
        return resultingHtml;
    }

    /**
     *
     * @param report
     * @return an Object?
     */
    @Override
    public void produceReportDocument(Set<SalaryNote> salaryTable) {
        resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
       salaryTable.forEach(note->addNewRow(note.getName(), note.getSalary()));
        addNewRow("Total", getTotalSalary(salaryTable));
        resultingHtml.append("</table></body></html>");

    }

    private void addNewRow(String name, double salary) {
        resultingHtml.append("<tr>"); // add row start tag
        putInRow(name);
        putInRow(salary);
        resultingHtml.append("</tr>"); // add row end tag

    }
    private void putInRow(String line){
        resultingHtml.append("<td>").append(line).append("</td>");
    }
    private void putInRow(double number){
        resultingHtml.append("<td>").append(number).append("</td>");
    }
}
