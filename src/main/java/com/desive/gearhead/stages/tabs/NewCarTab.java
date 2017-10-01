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

import com.desive.gearhead.stages.DashboardStage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewCarTab extends Tab {

    private TextField make, model, color, vin, oilType, oilCapacity, coolantCapacity, oilFilterModel, airFilterModel,
            cabinFilterModel, batteryModel;

    public NewCarTab(DashboardStage primaryStage) {

        super("New Car");
        ImageView imageView = new ImageView(new Image("assets/icons/car.png"));
        imageView.setFitWidth(20.0);
        imageView.setFitHeight(25.0);
        this.setGraphic(imageView);

        HBox container = new HBox(), inner = new HBox(), saveBox = new HBox();
        VBox box = new VBox(), recordBox = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(60, 15, 15, 15));

        inner.setAlignment(Pos.CENTER);
        inner.setPadding(new Insets(15, 15, 60, 15));

        Button save = new Button("Save");
        saveBox.getChildren().add(save);
        saveBox.setAlignment(Pos.CENTER);

        BorderPane makePane = new BorderPane(), modelPane = new BorderPane(), vinPane = new BorderPane(),
                oilTypePane = new BorderPane(), oilCapacityPane = new BorderPane(), coolantCapacityPane = new BorderPane(),
                oilFilterModelPane = new BorderPane(), airFilterModelPane = new BorderPane(), cabinFilterModelPane = new BorderPane(),
                batteryModelPane = new BorderPane(), colorPane = new BorderPane();

        Label makeLabel = new Label("Make:");
        makeLabel.setPadding(new Insets(1, 15, 1, 1));
        make = new TextField();
        makePane.setLeft(makeLabel);
        makePane.setRight(make);

        Label modelLabel = new Label("Model:");
        modelLabel.setPadding(new Insets(1, 15, 1, 1));
        model = new TextField();
        modelPane.setLeft(modelLabel);
        modelPane.setRight(model);

        Label colorLabel = new Label("Color:");
        colorLabel.setPadding(new Insets(1, 15, 1, 1));
        color = new TextField();
        colorPane.setLeft(colorLabel);
        colorPane.setRight(color);

        Label vinLabel = new Label("VIN:");
        vinLabel.setPadding(new Insets(1, 15, 1, 1));
        vin = new TextField();
        vinPane.setLeft(vinLabel);
        vinPane.setRight(vin);

        Label oilTypeLabel = new Label("Oil Type:");
        oilTypeLabel.setPadding(new Insets(1, 15, 1, 1));
        oilType = new TextField();
        oilTypePane.setLeft(oilTypeLabel);
        oilTypePane.setRight(oilType);

        Label oilCapacityLabel = new Label("Oil Capacity:");
        oilCapacityLabel.setPadding(new Insets(1, 15, 1, 1));
        oilCapacity = new TextField();
        oilCapacityPane.setLeft(oilCapacityLabel);
        oilCapacityPane.setRight(oilCapacity);

        Label coolantCapacityLabel = new Label("Coolant Capacity:");
        coolantCapacityLabel.setPadding(new Insets(1, 15, 1, 1));
        coolantCapacity = new TextField();
        coolantCapacityPane.setLeft(coolantCapacityLabel);
        coolantCapacityPane.setRight(coolantCapacity);

        Label oilFilterModelLabel = new Label("Oil Filter Model:");
        oilFilterModelLabel.setPadding(new Insets(1, 15, 1, 1));
        oilFilterModel = new TextField();
        oilFilterModelPane.setLeft(oilFilterModelLabel);
        oilFilterModelPane.setRight(oilFilterModel);

        Label airFilterModelLabel = new Label("Air Filter Model:");
        airFilterModelLabel.setPadding(new Insets(1, 15, 1, 1));
        airFilterModel = new TextField();
        airFilterModelPane.setLeft(airFilterModelLabel);
        airFilterModelPane.setRight(airFilterModel);

        Label cabinFilterModelLabel = new Label("Cabin Filter Model:");
        cabinFilterModelLabel.setPadding(new Insets(1, 15, 1, 1));
        cabinFilterModel = new TextField();
        cabinFilterModelPane.setLeft(cabinFilterModelLabel);
        cabinFilterModelPane.setRight(cabinFilterModel);

        Label batteryModelLabel = new Label("Battery Model:");
        batteryModelLabel.setPadding(new Insets(1, 15, 1, 1));
        batteryModel = new TextField();
        batteryModelPane.setLeft(batteryModelLabel);
        batteryModelPane.setRight(batteryModel);

        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(25);
        detailsGrid.setVgap(10);
        detailsGrid.add(makePane, 0, 0);
        detailsGrid.add(modelPane, 0, 1);
        detailsGrid.add(colorPane, 0, 2);
        detailsGrid.add(vinPane, 0, 3);
        detailsGrid.add(oilTypePane, 1, 0);
        detailsGrid.add(oilCapacityPane, 1, 1);
        detailsGrid.add(coolantCapacityPane, 1, 2);
        detailsGrid.add(oilFilterModelPane, 1, 3);
        detailsGrid.add(airFilterModelPane, 0, 4);
        detailsGrid.add(cabinFilterModelPane, 0, 5);
        detailsGrid.add(batteryModelPane, 1, 4);

        detailsGrid.add(saveBox, 0, 6, 2, 1);
        inner.getChildren().add(detailsGrid);
        box.getChildren().add(inner);

        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(60, 15, 15, 15));

        box.getChildren().add(recordBox);
        container.getChildren().add(box);

        save.setOnAction((event) -> {

            // craft json from request

            // send request to backend

            // close tab
            primaryStage.getTabPane().getTabs().remove(this);

        });

        this.setContent(container);
        this.setTooltip(new Tooltip("Create new car"));
    }


}
