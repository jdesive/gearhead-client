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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.desive.gearhead.utilities.Utilities.createFieldLabel;

public class CarTab extends Tab {

    private Label maintenancePageNumber = new Label("Page 0 of 0");
    private int currentMaintenancePageNumber = 0, maxMaintenancePageNumber = 0;

    private Car car;
    private DashboardStage primaryStage;

    public CarTab(Car car, DashboardStage primaryStage) {

        super(String.valueOf(car.getVin()));
        ImageView imageView = new ImageView(new Image("assets/icons/car.png"));
        imageView.setFitWidth(20.0);
        imageView.setFitHeight(25.0);
        this.setGraphic(imageView);

        this.car = car;
        this.primaryStage = primaryStage;

        HBox container = new HBox(), inner = new HBox();
        VBox box = new VBox(), recordBox = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(60, 15, 15, 15));

        inner.setAlignment(Pos.CENTER);
        inner.setPadding(new Insets(15, 15, 60, 15));

        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(25);
        detailsGrid.setVgap(10);
        detailsGrid.add(createFieldLabel("ID", car.getId()), 0, 0);
        detailsGrid.add(createFieldLabel("Make", car.getMake()), 0, 1);
        detailsGrid.add(createFieldLabel("Model", car.getModel()), 0, 2);
        detailsGrid.add(createFieldLabel("Color", car.getColor()), 0, 3);
        detailsGrid.add(createFieldLabel("VIN", car.getVin()), 1, 0);
        detailsGrid.add(createFieldLabel("Oil Type", car.getOilType()), 1, 1);
        detailsGrid.add(createFieldLabel("Oil Capacity", car.getOilCapacity()), 1, 2);
        detailsGrid.add(createFieldLabel("Coolant Capacity", car.getCoolantCapacity()), 1, 3);
        detailsGrid.add(createFieldLabel("Oil Filter Model", car.getOilFilterModel()), 0, 4);
        detailsGrid.add(createFieldLabel("Air Filter Model", car.getAirFilterModel()), 0, 5);
        detailsGrid.add(createFieldLabel("Cabin Filter Model", car.getCabinFilterModel()), 1, 4);
        detailsGrid.add(createFieldLabel("Battery Model", car.getBatteryModel()), 1, 5);

        inner.getChildren().add(detailsGrid);
        box.getChildren().add(inner);

        ListView<MaintenanceRecord> maintenanceTabListView = new ListView<>();
        getCarMaintenaceList(currentMaintenancePageNumber, car.getId()).forEach( record -> maintenanceTabListView.getItems().add(new MaintenanceRecord(record)));

        recordBox.getChildren().add(maintenanceTabListView);
        recordBox.getChildren().add(createMaintenanceController(maintenanceTabListView));

        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(60, 15, 15, 15));

        box.getChildren().add(recordBox);
        container.getChildren().add(box);

        this.setContent(container);
        this.setTooltip(new Tooltip("Details for car " + car.toString()));
    }

    private HBox createMaintenanceController(ListView<MaintenanceRecord> parentList){
        HBox box = new HBox(), innerBox = new HBox(), pageBox = new HBox();
        innerBox.setSpacing(5.0);
        box.setPadding(new Insets(5));
        Button back = new Button("<<Back"), next = new Button("Next>>"), view = new Button("View");

        maintenancePageNumber.setId("page-label");
        pageBox.getChildren().add(maintenancePageNumber);
        pageBox.setAlignment(Pos.CENTER);

        innerBox.getChildren().add(back);
        innerBox.getChildren().add(pageBox);
        innerBox.getChildren().add(next);
        innerBox.getChildren().add(view);

        box.getChildren().add(innerBox);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(primaryStage.getScreenMaxWidth()/2);

        view.setTooltip(new Tooltip("View maintenance record"));
        next.setTooltip(new Tooltip("Goto next page"));
        back.setTooltip(new Tooltip("Goto back a page"));

        // Event handlers
        back.setOnAction((event) -> {
            if(currentMaintenancePageNumber > 1){
                parentList.getItems().clear();
                getCarMaintenaceList(currentMaintenancePageNumber-2, car.getId()).forEach( record -> parentList.getItems().add(new MaintenanceRecord(record)));
            }
        });

        next.setOnAction((event) -> {
            if(currentMaintenancePageNumber >= 1 && currentMaintenancePageNumber != maxMaintenancePageNumber){
                parentList.getItems().clear();
                getCarMaintenaceList(currentMaintenancePageNumber, car.getId()).forEach( record -> parentList.getItems().add(new MaintenanceRecord(record)));
            }
        });

        view.setOnAction((event) -> {
            if(parentList.getSelectionModel().getSelectedItem() != null){
                primaryStage.getTabPane().getTabs().add(new MaintenanceTab(car, parentList.getSelectionModel().getSelectedItem()));
            }
        });

        return box;
    }

    private List<JSONObject> getCarMaintenaceList(int page, long carid){
        Map<String, String> params = new HashMap<>(), headers = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", "30");
        headers.put("Accept", "application/json");
        try {
            String requestResponse = Request.get(Request.HOSTADDRESS + String.format(RequestMap.LIST_MAINTENANCE_RECORDS.getEndpoint(), carid), params, headers);
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

}
