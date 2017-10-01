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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.desive.gearhead.utilities.Utilities.createFieldLabel;
import static com.desive.gearhead.utilities.Utilities.createTextFieldLabel;

public class MaintenanceTab extends Tab {

    public MaintenanceTab(Car car, MaintenanceRecord record) {

        super(String.valueOf(car.getVin()));
        ImageView imageView = new ImageView(new Image("assets/icons/wrench.png"));
        imageView.setFitWidth(15.0);
        imageView.setFitHeight(15.0);
        this.setGraphic(imageView);
        HBox container = new HBox();
        VBox box = new VBox();
        container.setAlignment(Pos.CENTER);
        box.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(60, 15, 15, 15));

        GridPane detailsGrid = new GridPane(), recordDetailsGrid = new GridPane();
        detailsGrid.setHgap(25);
        detailsGrid.setVgap(10);
        recordDetailsGrid.setHgap(25);
        recordDetailsGrid.setVgap(10);
        recordDetailsGrid.setPadding(new Insets(60, 15, 15, 15));

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

        recordDetailsGrid.add(createFieldLabel("ID", record.getId()), 0, 0);
        recordDetailsGrid.add(createFieldLabel("New Value", record.getNewValue()), 0, 1);
        recordDetailsGrid.add(createFieldLabel("Old Value", record.getOldValue()), 1, 1);
        recordDetailsGrid.add(createFieldLabel("Fieldname", record.getFieldname()), 1, 0);
        recordDetailsGrid.add(createTextFieldLabel("Notes", record.getNotes()), 0, 2, 2, 1);

        box.getChildren().add(detailsGrid);
        box.getChildren().add(recordDetailsGrid);
        container.getChildren().add(box);
        this.setContent(container);
        this.setTooltip(new Tooltip("Details for car " + car.toString()));
    }

}
