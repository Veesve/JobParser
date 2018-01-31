package ru.controller;

import ru.Vacancy;
import ru.model.Model;
import ru.view.ViewType;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Model model;
    private ViewType view;
    private List<Vacancy> vacancies = new ArrayList<>();
    public Controller(Model model, ViewType view) {
        this.model = model;
        this.view = view;
    }

    public void showResults(){
        view.show(vacancies);
    }
    public List<Vacancy> getVacancies(String request){
        vacancies = model.getJavaVacancies(request);
        return vacancies;
    }


    public void setView(ViewType view) {
        this.view = view;
    }

    public ViewType getView() {
        return view;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}
