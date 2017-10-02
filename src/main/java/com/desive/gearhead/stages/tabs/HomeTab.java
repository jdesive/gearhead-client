/*
 * Copyright (C) 2017  GearHead
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive.gearhead.stages.tabs;

import com.desive.gearhead.entities.Car;
import com.desive.gearhead.entities.MaintenanceRecord;
import com.desive.gearhead.nodes.StyledToolTip;
import com.desive.gearhead.stages.DashboardStage;
import com.desive.gearhead.utilities.Utilities;
import com.desive.gearhead.utilities.requests.Request;
import com.desive.gearhead.utilities.requests.RequestMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeTab extends Tab {

    private ListView<Car> carList;
    private ListView<MaintenanceRecord> maintenanceList;
    private DashboardStage primaryStage;

    private Label carPageNumber = new Label("Page 0 of 0"), maintenancePageNumber = new Label("Page 0 of 0");
    private int currentCarPageNumber = 0, currentMaintenancePageNumber = 0,
                maxCarPageNumber = 0, maxMaintenancePageNumber = 0;

    public HomeTab(DashboardStage primaryStage) {
        super("Home");
        ImageView imageView = new ImageView(new Image("assets/icons/home.png"));
        imageView.setFitWidth(15.0);
        imageView.setFitHeight(15.0);
        this.setGraphic(imageView);


        this.primaryStage = primaryStage;

        carList = new ListView<>();
        maintenanceList = new ListView<>();
        VBox container = new VBox();
        HBox carLabelBox = new HBox(), recordLabelBox = new HBox();
        getCarList(0).forEach( car -> carList.getItems().add(new Car(car)));

        Text carLabel = new Text("Car List"), recordLabel = new Text("Maintenance List");
        carLabel.setId("car-title");
        recordLabel.setId("record-title");

        carLabelBox.setAlignment(Pos.CENTER);
        recordLabelBox.setAlignment(Pos.CENTER);

        carLabelBox.getChildren().add(carLabel);
        recordLabelBox.getChildren().add(recordLabel);

        GridPane pane = new GridPane();
        pane.setPrefSize(primaryStage.getScreenMaxWidth(), (primaryStage.getScreenMaxHeight() -
                (primaryStage.getScreenMaxHeight()%0.3)));
        pane.add(carLabelBox, 0 ,0 );
        pane.add(recordLabelBox, 1, 0);
        pane.add(carList, 0, 1);
        pane.add(maintenanceList, 1, 1);

        carList.setPrefWidth(primaryStage.getScreenMaxWidth()/2);
        maintenanceList.setPrefWidth(primaryStage.getScreenMaxWidth()/2);

        HBox carListController = this.createCarController(carList), maintenanceListController =
                this.createMaintenanceController(maintenanceList);

        pane.add(carListController, 0, 2);
        pane.add(maintenanceListController, 1, 2);

        pane.setAlignment(Pos.CENTER);
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(pane);

        this.setContent(container);
        this.setClosable(false);
        this.setTooltip(new StyledToolTip("View cars and maintenance records"));

        // Event handlers
        carList.setOnMouseClicked((event) -> {
            maintenanceList.getItems().clear();
            if(carList.getSelectionModel().getSelectedItem() != null) {
                getCarMaintenaceList(0, carList.getSelectionModel().getSelectedItem().getId()).forEach(record -> {
                    if (record != null)
                        maintenanceList.getItems().add(new MaintenanceRecord(record));
                });
            }
        });

    }

    private List<JSONObject> getCarList(int page){
        Map<String, String> params = new HashMap<>(), headers = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", "30");
        headers.put("Accept", "application/json");
        try {
            String requestResponse = Request.get(Request.HOSTADDRESS + RequestMap.LIST_CARS.getEndpoint(), params, headers);
            JSONObject pageRes = ((JSONObject) Utilities.getJsonParser().parse(requestResponse));
            JSONArray content = (JSONArray) pageRes.get("content");
            currentCarPageNumber = Integer.valueOf(String.valueOf(((Long) pageRes.get("number")+1)));
            maxCarPageNumber = Integer.valueOf(String.valueOf((pageRes.get("totalPages"))));
            carPageNumber.setText("Page " + currentCarPageNumber + " of " + maxCarPageNumber);
            return content;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<JSONObject> getCarMaintenaceList(int page, long carid){
        Map<String, String> params = new HashMap<>(), headers = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", "30");
        headers.put("Accept", "application/json");
        try {
            String requestResponse = Request.get(Request.HOSTADDRESS +
                    String.format(RequestMap.LIST_MAINTENANCE_RECORDS.getEndpoint(), carid), params, headers);
            JSONObject pageRes = ((JSONObject) Utilities.getJsonParser().parse(requestResponse));
            JSONArray content = (JSONArray) pageRes.get("content");
            currentMaintenancePageNumber = Integer.valueOf(String.valueOf(((Long) pageRes.get("number")+1)));
            maxMaintenancePageNumber = Integer.valueOf(String.valueOf((pageRes.get("totalPages"))));
            maintenancePageNumber.setText("Page " + currentMaintenancePageNumber + " of " + maxMaintenancePageNumber);
            return content;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HBox createCarController(ListView<Car> parentList){
        HBox box = new HBox(), innerBox = new HBox(), pageBox = new HBox();
        innerBox.setSpacing(5.0);
        box.setPadding(new Insets(5));
        Button back = new Button("<<Back"), next = new Button("Next>>"), view = new Button("View"),
        addCar = new Button("+"), refresh = new Button();

        carPageNumber.setId("page-label");
        pageBox.getChildren().add(carPageNumber);
        pageBox.setAlignment(Pos.CENTER);

        ImageView refreshView = new ImageView(new Image("assets/icons/refresh.png"));
        refreshView.setFitHeight(15.0);
        refreshView.setFitWidth(15.0);
        refresh.setGraphic(refreshView);

        innerBox.getChildren().add(addCar);
        innerBox.getChildren().add(back);
        innerBox.getChildren().add(pageBox);
        innerBox.getChildren().add(next);
        innerBox.getChildren().add(view);
        innerBox.getChildren().add(refresh);

        box.getChildren().add(innerBox);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(primaryStage.getScreenMaxWidth()/2);

        view.setTooltip(new StyledToolTip("View Car details"));
        next.setTooltip(new StyledToolTip("Goto next page"));
        back.setTooltip(new StyledToolTip("Goto back a page"));
        addCar.setTooltip(new StyledToolTip("Add new car"));
        refresh.setTooltip(new StyledToolTip("Refresh car list"));

        // Event handlers
        back.setOnAction((event) -> {
            if(currentCarPageNumber > 1){
                parentList.getItems().clear();
                getCarList(currentCarPageNumber-2).forEach( car -> parentList.getItems().add(new Car(car)));
            }
        });

        next.setOnAction((event) -> {
            if(currentCarPageNumber >= 1 && currentCarPageNumber != maxCarPageNumber){
                parentList.getItems().clear();
                getCarList(currentCarPageNumber).forEach( car -> parentList.getItems().add(new Car(car)));
            }
        });

        view.setOnAction((event) -> {
            this.createCarTab(carList.getSelectionModel().getSelectedItem());
        });

        refresh.setOnAction((event) -> this.refreshCarList());
        addCar.setOnAction((event) -> this.createNewCarTab());

        return box;
    }

    private HBox createMaintenanceController(ListView<MaintenanceRecord> parentList){
        HBox box = new HBox(), innerBox = new HBox(), pageBox = new HBox();
        innerBox.setSpacing(5.0);
        box.setPadding(new Insets(5));
        Button back = new Button("<<Back"), next = new Button("Next>>"), view = new Button("View"),
            addRecord = new Button("+"), refresh = new Button();

        maintenancePageNumber.setId("page-label");
        pageBox.getChildren().add(maintenancePageNumber);
        pageBox.setAlignment(Pos.CENTER);

        ImageView refreshView = new ImageView(new Image("assets/icons/refresh.png"));
        refreshView.setFitHeight(15.0);
        refreshView.setFitWidth(15.0);
        refresh.setGraphic(refreshView);

        innerBox.getChildren().add(addRecord);
        innerBox.getChildren().add(back);
        innerBox.getChildren().add(pageBox);
        innerBox.getChildren().add(next);
        innerBox.getChildren().add(view);
        innerBox.getChildren().add(refresh);

        box.getChildren().add(innerBox);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(primaryStage.getScreenMaxWidth()/2);

        view.setTooltip(new StyledToolTip("View maintenance record"));
        next.setTooltip(new StyledToolTip("Goto next page"));
        back.setTooltip(new StyledToolTip("Goto back a page"));
        addRecord.setTooltip(new StyledToolTip("Add new record"));
        refresh.setTooltip(new StyledToolTip("Refresh maintenance record list"));

        // Event handlers
        back.setOnAction((event) -> {
            if(currentMaintenancePageNumber > 1){
                parentList.getItems().clear();
                getCarMaintenaceList(currentMaintenancePageNumber-2, carList.getSelectionModel().getSelectedItem()
                        .getId()).forEach( record -> parentList.getItems().add(new MaintenanceRecord(record)));
            }
        });

        next.setOnAction((event) -> {
            if(currentMaintenancePageNumber >= 1 && currentMaintenancePageNumber != maxMaintenancePageNumber){
                parentList.getItems().clear();
                getCarMaintenaceList(currentMaintenancePageNumber, carList.getSelectionModel().getSelectedItem().getId())
                        .forEach( record -> parentList.getItems().add(new MaintenanceRecord(record)));
            }
        });

        view.setOnAction((event) -> this.createMaintenanceTab(carList.getSelectionModel().getSelectedItem(),
                maintenanceList.getSelectionModel().getSelectedItem()));
        refresh.setOnAction((event) -> this.refreshMaintenanceRecordList());
        addRecord.setOnAction((event) -> this.createNewMaintenanceRecordTab(carList.getSelectionModel().getSelectedItem()
                != null ? carList.getSelectionModel().getSelectedItem() : null));

        return box;
    }

    private void createCarTab(Car car){
        if(car == null) {
            Utilities.throwAlert("No Car selected", "Please select a car" , "You did not select a " +
                    "car from the list! Please select one and try again.", primaryStage);
            return;
        }
        Tab tab = new CarTab(car, primaryStage);
        this.addAndSelectTab(tab);
    }

    private void createNewCarTab(){
        Tab tab = new NewCarTab(primaryStage);
        this.addAndSelectTab(tab);
    }

    private void createNewMaintenanceRecordTab(Car car){
        if(car == null) {
            Utilities.throwAlert("No Car selected", "Please select a car" , "You did not select a " +
                    "car from the list! Please select one and try again.", primaryStage);
            return;
        }
        Tab tab = new NewMaintenanceTab(car, primaryStage);
        this.addAndSelectTab(tab);
    }

    private void createMaintenanceTab(Car car, MaintenanceRecord record){
        if(car == null) {
            Utilities.throwAlert("No Car selected", "Please select a car" , "You did not select a " +
                    "car from the list! Please select one and try again.", primaryStage);
            return;
        }

        if(record == null) {
            Utilities.throwAlert("No Record selected", "Please select a record" , "You did not select a " +
                    "record from the list! Please select one and try again.", primaryStage);
            return;
        }

        Tab tab = new MaintenanceTab(car, record);
        this.addAndSelectTab(tab);
    }

    private void addAndSelectTab(Tab tab){
        primaryStage.getTabPane().getTabs().add(tab);
        primaryStage.getTabPane().getSelectionModel().select(tab);
    }

    private void refreshCarList(){
        currentCarPageNumber--;
        carList.getItems().clear();
        getCarList(currentCarPageNumber).forEach( car -> carList.getItems().add(new Car(car)));
    }

    private void refreshMaintenanceRecordList(){
        if(carList.getSelectionModel().getSelectedItem() == null) {
            Utilities.throwAlert("No Car selected", "Please select a car", "You have not selected " +
                    "a car from the car list!", primaryStage);
            return;
        }
        currentMaintenancePageNumber--;
        maintenanceList.getItems().clear();
        getCarMaintenaceList(currentMaintenancePageNumber, carList.getSelectionModel().getSelectedItem().getId())
                .forEach( record -> maintenanceList.getItems().add(new MaintenanceRecord(record)));
    }

}
