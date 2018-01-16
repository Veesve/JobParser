package ru;

import ru.controller.Controller;
import ru.model.HHStrategy;
import ru.model.MCStrategy;
import ru.model.Model;
import ru.view.ConsoleView;
import ru.view.HtmlView;
import ru.view.ViewType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aggregator {
    public static void main(String[] args) {
        ViewType view = new HtmlView();
        Model model = new Model();
        model.setStrategy(new HHStrategy(),new MCStrategy());


        Controller controller = new Controller(model,view);
        System.out.println("Type in search request:");
        controller.getVacancies(readRequest());
        controller.showResults();


    }

    private static String readRequest(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String request = null;
        try {
            request = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return request.replaceAll(" ","+");
    }
}
