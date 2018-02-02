package ru.veesve.jobparser.view;

import ru.veesve.jobparser.Vacancy;

import java.util.List;

public interface ViewType {
    void show(List<Vacancy> vacancyList);

}
