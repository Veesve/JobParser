package ru.veesve.jobparser.model;

import ru.veesve.jobparser.Vacancy;

import java.util.List;

public interface Strategy {
    List<Vacancy> getVacancies(String request);

}
