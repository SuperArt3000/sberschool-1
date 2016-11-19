import dao.SalaryNote;
import domain.HtmlInterpreter;
import domain.HtmlReportSender;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        StringBuilder resultingHtml = new StringBuilder();
        HtmlInterpreter interpreter = new HtmlInterpreter(resultingHtml);
        Set<SalaryNote> table = new HashSet<>();
        table.add(new SalaryNote("John", 12.08));
        table.add(new SalaryNote("James", 30.55));
        table.add(new SalaryNote("Joshua", 23.00));
        table.add(new SalaryNote("Igor", 15.28));
        interpreter.produceReportDocument(table);
        HtmlReportSender sender = new HtmlReportSender("cabaljer@gmail.com", resultingHtml);
        sender.send();
    }
}
