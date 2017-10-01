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

package com.desive.gearhead;

import com.desive.gearhead.stages.DashboardStage;
import com.desive.gearhead.utilities.Utilities;
import com.desive.gearhead.utilities.requests.Request;
import com.desive.gearhead.utilities.requests.RequestMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

public class GearHeadLogin  extends Application{

    private Label statusLabel, statusValue;
    private TextField userTextField;
    private PasswordField pwBox;
    private Timeline statusHealthTimer;
    private Stage primaryStage;

    @Override public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        primaryStage.setTitle("GearHead Login"); // Set Title
        primaryStage.setResizable(false);

        setUserAgentStylesheet(STYLESHEET_MODENA);

        primaryStage.getIcons().add(new Image("assets/favicon.png"));

        Utilities.init(this);

        //Create Grid
        GridPane grid = new GridPane();
        grid.getStylesheets().add("css/login.css");
        grid.setId("background");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // create Scene
        Scene scene = new Scene(grid, 350, 200);
        primaryStage.setScene(scene); // Set Scene

        // Create and add Title
        Text scenetitle = new Text("Sign in");
        scenetitle.setId("title-text");
        grid.add(scenetitle, 0, 0, 2, 1);

        // Create and add User field
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);
        userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        // Create and add Pass field
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        // Create and add Sign in button
        Button signButton = new Button("Sign in"), register = new Button("Register");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        register.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(register);
        hbBtn.getChildren().add(signButton);
        grid.add(hbBtn, 1, 4);

        // Status label
        statusLabel = new Label("API Health: ");
        statusValue = new Label("UP");
        HBox statusBox = new HBox();
        statusLabel.setId("status-label");
        statusValue.setId("status-text-up");
        statusBox.getChildren().add(statusLabel);
        statusBox.getChildren().add(statusValue);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        grid.add(statusBox, 0, 4);

        // Here we will use Timeline to schedule a task for that api status
        // Because the task will modify the UI, it has to be ran on the JavaFX thread
        statusHealthTimer = new Timeline(new KeyFrame(Duration.seconds(15), (event) -> {
            try{
                String jsonRes = Request.get(Request.HOSTADDRESS + RequestMap.HEALTH.getEndpoint(), new HashMap<>(), new HashMap<>());
                JSONObject response = (JSONObject) Utilities.getJsonParser().parse(jsonRes);
                if(primaryStage.isShowing())
                    statusValue.setText(String.valueOf(response.get("status")));
                if(statusValue.getText().toUpperCase().equals("UP"))
                    statusValue.setId("status-text-up");
                else
                    statusValue.setId("status-text-down");

            }catch(IOException | ParseException | IllegalStateException ex){
                if(primaryStage.isShowing())
                    statusValue.setText("DOWN");
                statusValue.setId("status-text-down");
            }
        }));
        statusHealthTimer.setCycleCount(Timeline.INDEFINITE);
        statusHealthTimer.play();

        signButton.setOnAction( (event) -> {

            //Check with api/signup for creds
            if(!login())
                return;
            //Start main application
            primaryStage.close();
            new DashboardStage(this).show();
        }
        );

        // Shutdown hook
        primaryStage.setOnCloseRequest((event) -> {
            Utilities.stop();
            statusHealthTimer.stop();
        });

        // Show the stage
        primaryStage.show();
    }

    public void reset(){
        this.userTextField.setText("");
        this.pwBox.setText("");
        statusHealthTimer.play();
        this.primaryStage.show();
    }

    private boolean login(){
        HashMap<String, String> params = new HashMap<>();
        params.put("username", userTextField.getText());
        params.put("password", pwBox.getText());

        try {
            String resquestStr = Request.get(Request.HOSTADDRESS + RequestMap.SIGNIN.getEndpoint(), params, new HashMap<>());
            JSONObject resObj = (JSONObject) Utilities.getJsonParser().parse(resquestStr);
            if(((Boolean)resObj.get("successful"))){
                return true;
            }else{
                Utilities.throwAlert("Login failed", "Could not login", ((String)resObj.get("error")));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
