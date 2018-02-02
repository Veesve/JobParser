package ru.veesve.jobparser;


import ru.veesve.jobparser.controller.Controller;
import ru.veesve.jobparser.model.HHStrategy;
import ru.veesve.jobparser.model.MCStrategy;
import ru.veesve.jobparser.model.Model;
import ru.veesve.jobparser.view.ConsoleView;
import ru.veesve.jobparser.view.GUIView;
import ru.veesve.jobparser.view.HtmlView;
import ru.veesve.jobparser.view.ViewType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class Aggregator {

    public static void main(String[] args) {
        if(args.length == 0){
            startGUIView();
        }
        switch(args[0].toLowerCase())
        {
            case "console":{
               startConsoleView();
            }
            case "gui":{
               startGUIView();
            }
            case "html":{
                startHTMLView();
            }
            default:{
                System.out.println("Wrong command");
                System.exit(0);
            }
        }
    }

    private static void startHTMLView() {
        ViewType view = new HtmlView();
        Model model = new Model();
        model.setStrategy(new HHStrategy(),new MCStrategy());
        Controller controller = new Controller(model,view);
        controller.getVacancies(readRequest());
        controller.showResults();
    }

    private static void startConsoleView(){
        ViewType view = new ConsoleView();
        Model model = new Model();
        model.setStrategy(new HHStrategy(),new MCStrategy());
        Controller controller = new Controller(model,view);
        controller.getVacancies(readRequest());
        controller.showResults();
    }
    private static void startGUIView(){
        ViewType view = new GUIView();
        Model model = new Model();
        model.setStrategy(new HHStrategy(),new MCStrategy());
        Controller controller = new Controller(model,view);
        controller.showResults();
    }
    private static String readRequest(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String request;
        try {
            request = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return request.replaceAll(" ","+");
    }
}
