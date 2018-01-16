package ru.model;

import ru.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private Strategy[] strategy;
    public List<Vacancy> getJavaVacancies(String request){
        if(request == null ||request.isEmpty())
            throw new IllegalArgumentException();
        List<Vacancy> vacancies = new ArrayList<>();
        for(Strategy iter : strategy)
            vacancies.addAll(iter.getVacancies(request));
        return vacancies;

    }

    public Strategy[] getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy... strategy) {
        this.strategy = strategy;
    }
}
