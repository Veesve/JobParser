package ru.model;

import ru.Vacancy;

import java.util.List;

public class Model {
    private Strategy strategy;
    public List<Vacancy> getJavaVacancies(String request){
        return strategy.getVacancies(request);
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
