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

package com.desive.gearhead.utilities;

import com.desive.gearhead.GearHeadLogin;
import com.desive.gearhead.utilities.scheduler.Scheduler;
import com.desive.gearhead.stages.DashboardStage;
import javafx.application.HostServices;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.Optional;

public class Utilities {

    private static Scheduler SCHEDULER;
    private static JSONParser JSON_PARSER;
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
    private static HostServices HOST_SERVICES;

    private static final PseudoClass ERROR_CLASS = PseudoClass.getPseudoClass("error");

    public static void init(GearHeadLogin app) {
        SCHEDULER = new Scheduler(5);
        JSON_PARSER = new JSONParser();
        HOST_SERVICES = app.getHostServices();
    }

    public static void stop(){
        SCHEDULER.stop();
    }

    public static void throwAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("assets/favicon.png"));

        alert.showAndWait();
    }

    public static void throwAlert(String title, String header, String content, DashboardStage parent){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(parent);
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }

    public static Optional<ButtonType> throwConfirmationAlert(String title, String header, String content, DashboardStage parent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(parent);
        alert.initModality(Modality.APPLICATION_MODAL);

        return alert.showAndWait();
    }

    public static BorderPane createFieldLabel(String label, Object value){
        BorderPane box = new BorderPane();

        Label label1 = new Label(label + ":");
        TextField field = new TextField(String.valueOf(value));
        field.setEditable(false);
        label1.setPadding(new Insets(1, 15, 1, 1));

        box.setLeft(label1);
        box.setRight(field);

        return box;
    }

    public static BorderPane createTextFieldLabel(String label, Object value){
        BorderPane box = new BorderPane();

        Label label1 = new Label(label + ":");
        TextArea field = new TextArea(String.valueOf(value));
        field.setEditable(false);
        label1.setPadding(new Insets(1, 15, 1, 1));

        box.setLeft(label1);
        box.setRight(field);

        return box;
    }

    public static Scheduler getScheduler() {
        return SCHEDULER;
    }

    public static JSONParser getJsonParser() {
        return JSON_PARSER;
    }

    public static SimpleDateFormat getDateFormat() {
        return DATE_FORMAT;
    }

    public static HostServices getHostServices() {
        return HOST_SERVICES;
    }

    public static PseudoClass getErrorClass() {
        return ERROR_CLASS;
    }
}
