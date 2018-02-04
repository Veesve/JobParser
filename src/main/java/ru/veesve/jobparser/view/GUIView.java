package ru.veesve.jobparser.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import ru.veesve.jobparser.Vacancy;
import ru.veesve.jobparser.controller.Controller;
import ru.veesve.jobparser.model.HHStrategy;
import ru.veesve.jobparser.model.MCStrategy;
import ru.veesve.jobparser.model.Model;


import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class GUIView extends Application implements ViewType {


    private ObservableList<Vacancy> vacancyList = FXCollections.observableList(new ArrayList<Vacancy>());


    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Group());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Job Aggregator");
        primaryStage.setWidth(810);
        primaryStage.setHeight(520);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        //TextField Section
        final TextField searchField = new TextField();
        searchField.setPromptText("I'm looking for...");

        //Label Section
        final Label titleLabel = new Label("Job Aggregator");
        titleLabel.setFont(new Font("Arial", 20));

        //Table Section
        final TableView<Vacancy> table = new TableView<>();

        TableColumn<Vacancy, String> companyNameCol =
                new TableColumn<>("Company Name");
        companyNameCol.setMinWidth(150);
        companyNameCol.setCellValueFactory(
                new PropertyValueFactory<>("companyName")
        );


        TableColumn<Vacancy, String> salaryCountCol =
                new TableColumn<>("Salary Count");
        salaryCountCol.setMinWidth(130);
        salaryCountCol.setCellValueFactory(
                new PropertyValueFactory<>("salaryCount")
        );

        TableColumn<Vacancy, LocalDate> creatingDateCol =
                new TableColumn<>("Creating Date");
        creatingDateCol.setMinWidth(90);
        creatingDateCol.setCellValueFactory(
                new PropertyValueFactory<>("creatingDate")
        );


        TableColumn<Vacancy, Hyperlink> titleCol =
                new TableColumn<>("Title");
        titleCol.setMinWidth(170);

        //Custom Factory for hyperlink cells, with creating hyperlink object
        titleCol.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<Hyperlink> call(TableColumn.CellDataFeatures<Vacancy, Hyperlink> param) {

                final String urlString = param.getValue().getUrl();
                Hyperlink link = new Hyperlink(urlString);
                link.setText(param.getValue().getTitle());
                link.setOnAction(new EventHandler<>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Desktop.getDesktop().browse(new URL(urlString).toURI());
                        } catch (IOException | URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return new SimpleObjectProperty<>(link);
            }
        });
        titleCol.setCellFactory(new HyperlinkCell());


        TableColumn<Vacancy, String> addressCol =
                new TableColumn<>("Address");
        addressCol.setMinWidth(110);
        addressCol.setCellValueFactory(
                new PropertyValueFactory<>("address")
        );

        TableColumn<Vacancy, String> siteNameCol =
                new TableColumn<>("Site Name");
        siteNameCol.setMinWidth(120);
        siteNameCol.setCellValueFactory(
                new PropertyValueFactory<>("siteName")
        );

        table.setItems(vacancyList);
        table.getColumns().addAll(companyNameCol, titleCol, salaryCountCol, addressCol, creatingDateCol, siteNameCol);

        //ButtonSection
        final Button searchButton = new Button("Job Search");
        searchButton.setOnAction((ActionEvent e) -> {
            GUIView currentView = this;

            Task findJobs = new Task<Void>() {
                @Override
                public Void call() {
                    Model model = new Model();
                    model.setStrategy(new HHStrategy(), new MCStrategy());
                    Controller controller = new Controller(model, currentView);
                    vacancyList.removeAll(vacancyList);
                    vacancyList.addAll(controller.getVacancies(searchField.getText()));
                    return null;
                }
            };
            new Thread(findJobs).start();
        });

        //Box Section
        final HBox hb = new HBox();
        hb.getChildren().addAll(searchField, searchButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(titleLabel, table, hb);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);


        //showing the scene
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void show(List<Vacancy> vacancyList) {
        this.vacancyList = FXCollections.observableArrayList(vacancyList);
        GUIView.launch(GUIView.class);
    }

    public class HyperlinkCell implements Callback<TableColumn<Vacancy, Hyperlink>, TableCell<Vacancy, Hyperlink>> {
        @Override
        public TableCell<Vacancy, Hyperlink> call(TableColumn<Vacancy, Hyperlink> arg) {
            TableCell<Vacancy, Hyperlink> cell = new TableCell<Vacancy, Hyperlink>() {
                @Override
                protected void updateItem(Hyperlink item, boolean empty) {
                    setGraphic(item);
                }
            };
            return cell;
        }
    }

}
