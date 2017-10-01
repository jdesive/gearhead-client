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

import static com.desive.gearhead.utilities.Utilities.createFieldLabel;

public class NewMaintenanceTab extends Tab {

    private TextField newValue, oldValue, fieldname;
    private TextArea notes;

    public NewMaintenanceTab(Car car, DashboardStage primaryStage) {

        super("New Maintenance Record");
        ImageView imageView = new ImageView(new Image("assets/icons/wrench.png"));
        imageView.setFitWidth(15.0);
        imageView.setFitHeight(15.0);
        this.setGraphic(imageView);

        HBox container = new HBox(), saveBox = new HBox();
        VBox box = new VBox(), recordBox = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(60, 15, 15, 15));

        Button save = new Button("Save");
        saveBox.getChildren().add(save);
        saveBox.setAlignment(Pos.CENTER);

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

        box.getChildren().add(detailsGrid);

        BorderPane newValuePane = new BorderPane(), oldValuePane = new BorderPane(), fieldnamePane = new BorderPane(),
                notesPane = new BorderPane();
        Label newValueLabel = new Label("New Value:"), oldValueLabel = new Label("Old Value:"),
                fieldnameLabel = new Label("Fieldname: "), notesLabel = new Label("Notes: ");

        newValueLabel.setPadding(new Insets(1, 15, 1, 1));
        oldValueLabel.setPadding(new Insets(1, 15, 1, 1));
        fieldnameLabel.setPadding(new Insets(1, 15, 1, 1));
        notesLabel.setPadding(new Insets(1, 15, 1, 1));

        newValue = new TextField();
        newValuePane.setLeft(newValueLabel);
        newValuePane.setRight(newValue);

        oldValue = new TextField();
        oldValuePane.setLeft(oldValueLabel);
        oldValuePane.setRight(oldValue);

        fieldname = new TextField();
        fieldnamePane.setLeft(fieldnameLabel);
        fieldnamePane.setRight(fieldname);

        notes = new TextArea();
        notesPane.setLeft(notesLabel);
        notesPane.setRight(notes);

        GridPane recordGrid = new GridPane();
        recordGrid.setHgap(25);
        recordGrid.setVgap(10);
        recordGrid.setPadding(new Insets(60, 15, 15, 15));

        recordGrid.add(fieldnamePane, 0, 0);
        recordGrid.add(newValuePane, 1, 1);
        recordGrid.add(oldValuePane, 0, 1);
        recordGrid.add(notesPane, 0, 2, 2, 1);
        recordGrid.add(saveBox, 0, 3, 2, 1);

        box.getChildren().add(recordGrid);

        container.setAlignment(Pos.CENTER);
        box.setAlignment(Pos.CENTER);

        container.getChildren().add(box);

        save.setOnAction((event) -> {

            // craft json from request

            // send request to backend

            // close tab
            primaryStage.getTabPane().getTabs().remove(this);

        });

        this.setContent(container);
        this.setTooltip(new Tooltip("Create new maintenance record"));
    }


}
