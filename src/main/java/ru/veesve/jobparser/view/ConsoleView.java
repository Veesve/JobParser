package ru.veesve.jobparser.view;

import ru.veesve.jobparser.Vacancy;

import java.util.List;

public class ConsoleView implements ViewType {

    @Override
    public void show(List<Vacancy> vacancyList) {
        for(Vacancy vacancy : vacancyList)
            System.out.println(vacancy);
    }
}
