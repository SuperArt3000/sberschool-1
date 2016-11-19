package dao;


import java.util.Set;
import java.util.stream.Stream;

/**
 * Не исползуется. Оставил на всякий случай. Вместо него передаётся сразу Set<Notes>
 */
public class Report {
    private final Set<SalaryNote> salaryDataSet;

    public Report(Set<SalaryNote> salaryDataSet) {
        this.salaryDataSet = salaryDataSet;
    }

    public void addNote(String name, double salary){
        salaryDataSet.add(new SalaryNote(name, salary));
    }
    public Stream<SalaryNote> getSalaryData(){
        return salaryDataSet.stream();
    }
}
