package com.example.dijkstra;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;

public class AlertBox {

	public static void display(String message , String what) {

		Image image = new Image("file:al.jpg");
		ImageView imageView = new ImageView(image);

		Stage window = new Stage();
		
		window.getIcons().add(new Image("file:icon.png"));

		window.setTitle(what);
		window.setMinWidth(250);

		Label label = new Label();
		label.setText(message);
		label.setTextFill(Color.web("#fff", 1));
		
		
		label.setMinWidth(50);
		label.setMinHeight(50);
		
		label.setStyle("-fx-stroke-font-size: 15px;");
		
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> window.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.TOP_CENTER);
		layout.setSpacing(10);
		
		StackPane sp = new StackPane (imageView ,layout);

		Scene scene = new Scene(sp, 400, 300);

		window.setResizable(true);
		window.setScene(scene);
		window.showAndWait();
	}
	}