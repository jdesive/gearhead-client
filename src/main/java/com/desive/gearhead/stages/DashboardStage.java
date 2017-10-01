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

package com.desive.gearhead.stages;

import com.desive.gearhead.GearHeadLogin;
import com.desive.gearhead.stages.tabs.HomeTab;
import com.desive.gearhead.utilities.Utilities;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardStage extends Stage{

    private Scene dashboardScene;

    private Screen screen = Screen.getPrimary();
    private Rectangle2D bounds = screen.getVisualBounds();
    private TabPane tabPane;

    private GearHeadLogin parentStage;

    public DashboardStage(GearHeadLogin parentStage) {

        this.parentStage = parentStage;

        this.setTitle("GearHead Dashboard");
        this.getIcons().add(new Image("assets/favicon.png"));

        this.fullscreenStage();
        this.initStyle(StageStyle.UNDECORATED);

        BorderPane pane = new BorderPane();
        pane.setTop(this.getHeader());
        pane.setCenter(this.getBody());
        pane.setBottom(this.getFooter());
        pane.setPrefHeight(this.getMaxHeight());
        pane.setPrefWidth(this.getMaxWidth());

        this.dashboardScene = new Scene(pane, 300, 100);
        this.setScene(this.dashboardScene);

        // Set master stylesheet
        pane.getStylesheets().add("css/dashboard.css");

        this.setOnCloseRequest((event) -> {
            Utilities.stop();
        });

    }

    private BorderPane getHeader(){
        BorderPane pane = new BorderPane();
        pane.setPrefWidth((this.getScreenMaxWidth()));
        pane.setPrefHeight((this.getScreenMaxHeight()%0.5)/2);
        // Quick information

        HBox titleBox = new HBox();
        Text title = new Text("Dashboard");
        title.setId("title1");
        titleBox.getChildren().add(title);
        titleBox.setPadding(new Insets(15));
        pane.setLeft(titleBox);

        HBox controlBox = new HBox();
        Button exit = new Button(""), logout = new Button("");
        ImageView exitView = new ImageView(new Image("assets/icons/exit.png")),
                logoutView = new ImageView(new Image("assets/icons/logout.png"));
        exitView.setFitWidth(20.0);
        exitView.setFitHeight(20.0);
        logoutView.setFitWidth(20.0);
        logoutView.setFitHeight(20.0);
        exit.setGraphic(exitView);
        exit.setTooltip(new Tooltip("Exit the application"));
        logout.setGraphic(logoutView);
        logout.setTooltip(new Tooltip("Sign out"));
        controlBox.getChildren().add(logout);
        controlBox.getChildren().add(exit);

        controlBox.setPadding(new Insets(15, 25,0,0));
        controlBox.setSpacing(5);
        pane.setRight(controlBox);

        //Event handlers
        exit.setOnAction((event) -> this.close());
        logout.setOnAction((event) -> {this.close();this.parentStage.reset();});
        return pane;
    }

    private GridPane getBody(){
        GridPane grid = new GridPane();
        grid.setPrefWidth(this.getScreenMaxWidth());
        grid.setPrefHeight(this.getScreenMaxHeight() - (this.getScreenMaxHeight()%0.5));
        grid.setAlignment(Pos.CENTER_LEFT);

        tabPane = new TabPane();
        grid.add(tabPane, 0 ,0);

        tabPane.getTabs().add(new HomeTab(this));

        return grid;
    }

    private GridPane getFooter(){
        GridPane grid = new GridPane();
        grid.setId("footer");
        grid.setAlignment(Pos.BOTTOM_LEFT);
        grid.setPrefWidth((this.getScreenMaxWidth()));
        grid.setPrefHeight((this.getScreenMaxHeight()%0.5)/2);

        Hyperlink githubHpl = new Hyperlink(""), authorHpl = new Hyperlink("@jdesive");

        Label createdBy = new Label("Created by ");

        HBox imageBox = new HBox(), linkBox = new HBox();
        Image icon = new Image("assets/favicon.png"), github = new Image("assets/icons/github-icon.png");
        ImageView logoView = new ImageView(icon), githubView = new ImageView(github);
        logoView.setFitHeight(80.0);
        logoView.setFitWidth(80.0);
        githubView.setFitHeight(30.0);
        githubView.setFitWidth(30.0);

        githubHpl.setGraphic(githubView);
        githubHpl.setTooltip(new Tooltip("View source on Github"));

        authorHpl.setGraphic(createdBy);
        authorHpl.setTooltip(new Tooltip("View my profile"));

        linkBox.getChildren().add(githubHpl);
        linkBox.getChildren().add(authorHpl);
        linkBox.setAlignment(Pos.BOTTOM_CENTER);

        imageBox.getChildren().add(logoView);
        imageBox.getChildren().add(linkBox);

        imageBox.setSpacing(5.0);
        imageBox.setPadding(new Insets(15));

        grid.add(imageBox, 0, 0);


        githubHpl.setOnAction((event) -> {
            Utilities.getHostServices().showDocument("https://github.com/jdesive/gearhead-client");
            githubHpl.setVisited(false);
        });

        authorHpl.setOnAction((event) -> {
            Utilities.getHostServices().showDocument("https://github.com/jdesive");
            authorHpl.setVisited(false);
        });

        return grid;
    }

    private void fullscreenStage(){
        this.setX(bounds.getMinX());
        this.setY(bounds.getMinY());
        this.setWidth(bounds.getWidth());
        this.setHeight(bounds.getHeight());
    }

    public double getScreenMaxWidth(){
        return bounds.getWidth();
    }

    public double getScreenMaxHeight(){
        return bounds.getHeight();
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
