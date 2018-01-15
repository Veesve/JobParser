package ru;

import ru.controller.Controller;
import ru.model.HHStrategy;
import ru.model.Model;
import ru.view.ConsoleView;
import ru.view.ViewType;

public class Aggregator {
    public static void main(String[] args) {
        ViewType view = new ConsoleView();
        Model model = new Model();
        model.setStrategy(new HHStrategy());


        Controller controller = new Controller(model,view);
        controller.getVacancies("Стажер"); //todo: Replace testString
        controller.showResults();

    }
}
