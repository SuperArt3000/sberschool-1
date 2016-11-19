package dao;


import java.util.Set;

public interface Reporter {

    Set<? extends Note> buildNewReport();

}
