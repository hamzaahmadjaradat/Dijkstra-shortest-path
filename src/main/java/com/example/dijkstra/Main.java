package com.example.dijkstra;
import javafx.scene.text.FontWeight;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.text.Font;
public class Main extends Application {

	String fromName;
	String toName;
	Dijkstra dijkstra;
	ArrayList<Vertex> buildings;

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.getIcons().add(new Image("file:iconjpg.jpg"));
		Image image = new Image("file:bg.jpg");
		ImageView imageView = new ImageView(image);

		Button path = new Button("Path");
		Button distance = new Button("Distance");
		Button map = new Button("Map");
		Button close = new Button("Close");
		Button load = new Button("Load");

		ComboBox<String> from = new ComboBox<String>();
		ComboBox<String> to = new ComboBox<String>();

		Label source = new Label("From");
		Label target = new Label("To");

		from.setLayoutX(14.0);
		from.setLayoutY(169.0);
		from.setPrefHeight(42.0);
		from.setPrefWidth(141.0);

		to.setLayoutX(453.0);
		to.setLayoutY(169.0);
		to.setPrefHeight(42.0);
		to.setPrefWidth(141.0);

		path.setLayoutX(14.0);
		path.setLayoutY(316.0);
		path.setPrefHeight(50.0);
		path.setPrefWidth(116.0);

		distance.setLayoutX(246.0);
		distance.setLayoutY(316.0);
		distance.setPrefHeight(50.0);
		distance.setPrefWidth(116.0);

		map.setLayoutX(478.0);
		map.setLayoutY(316.0);
		map.setPrefHeight(50.0);
		map.setPrefWidth(116.0);

		close.setLayoutX(478.0);
		close.setLayoutY(14);
		close.setPrefHeight(70);
		close.setPrefWidth(122);

		load.setLayoutX(14.0);
		load.setLayoutY(14.0);
		load.setPrefHeight(70);
		load.setPrefWidth(122);

		source.setLayoutX(14);
		source.setLayoutY(134.0);
		source.setPrefHeight(35);
		source.setPrefWidth(141.0);

		target.setLayoutX(453.0);
		target.setLayoutY(134);
		target.setPrefHeight(35);
		target.setPrefWidth(141);

		source.setTextFill(Color.web("#fff", 0.8));
		target.setTextFill(Color.web("#fff", 0.8));

		close.setOnAction(e0 -> {
			primaryStage.close();
		});

		load.setOnAction(e -> {

			buildings = new ArrayList<Vertex>();
			dijkstra = new Dijkstra();
			String line;

			// Read in the vertices
			BufferedReader vertexFileBr = null;
			try {
				vertexFileBr = new BufferedReader(new FileReader("buildings.txt"));
			} catch (FileNotFoundException e8) {
				// TODO Auto-generated catch block
				e8.printStackTrace();
			}

			ArrayList<String> fileBuildings = new ArrayList<>();

			try {
				while ((line = vertexFileBr.readLine()) != null) {
					String[] parts = line.split("-");
					if (parts.length != 3) {
						vertexFileBr.close();
						throw new IOException("Invalid line in vertex file " + line);
					}
					String buildingName = parts[0];
					int x = Integer.valueOf(parts[1]);
					int y = Integer.valueOf(parts[2]);
					fileBuildings.add(buildingName);
					Vertex vertex = new Vertex(buildingName, x, y);
					dijkstra.addVertex(vertex);
					buildings.add(vertex);
				}
			} catch (NumberFormatException | IOException e8) {
				// TODO Auto-generated catch block
				e8.printStackTrace();
			}

			ObservableList<String> buldingsList = FXCollections.observableArrayList();

			for (String input : fileBuildings) {
				buldingsList.add(input);
			}

			try {
				vertexFileBr.close();
			} catch (IOException e7) {
				// TODO Auto-generated catch block
				e7.printStackTrace();
			}

			from.setItems(buldingsList);
			to.setItems(buldingsList);

			BufferedReader edgeFileBr = null;
			try {
				edgeFileBr = new BufferedReader(new FileReader("edgies.txt"));
			} catch (FileNotFoundException e6) {
				// TODO Auto-generated catch block
				e6.printStackTrace();
			}
			try {


				while ((line = edgeFileBr.readLine()) != null) {
					String[] parts = line.split("-");
					if (parts.length != 3) {
						try {
							edgeFileBr.close();
						} catch (IOException e4) {
							// TODO Auto-generated catch block
							e4.printStackTrace();
						}
						throw new IOException("Invalid line in edge file " + line);
					}
					dijkstra.addUndirectedEdge(parts[0], parts[1], Double.parseDouble(parts[2]));

				}
			} catch (NumberFormatException | IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			try {
				edgeFileBr.close();
			} catch (IOException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			from.setValue(fileBuildings.get(0));
			to.setValue(fileBuildings.get(fileBuildings.size()-1));

		});

		path.setOnAction(e1 -> {

			fromName = from.getValue();
			toName = to.getValue();

			findPath(fromName, toName, dijkstra);

		});

		distance.setOnAction(e2 -> {

			fromName = from.getValue();
			toName = to.getValue();

			findDistance(fromName, toName, dijkstra);

		});

		map.setOnAction(e3 -> {

			fromName = from.getValue();
			toName = to.getValue();
			map(fromName, toName, dijkstra);
		});

		Pane pane = new Pane(imageView, from, to, path, distance, map, load, close, source, target);
		Scene scene = new Scene(pane, 608, 380);

		primaryStage.setResizable(true);
		primaryStage.setTitle("Project 3");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	void map(String start, String end, Dijkstra dijkstra) {

		String startBulding = start;
		String endBuilding = end;

		List<Edge> path = dijkstra.getDijkstraPath(startBulding, endBuilding); //return a list with the path from edges

//		System.out.println(path.size());

		Line[] lines = new Line[path.size() ];

		for (int i = 0; i < path.size(); i++) {
			lines[i ] = new Line(path.get(i).source.x, path.get(i).source.y, path.get(i).target.x,
					path.get(i).target.y);
			lines[i].setStyle("-fx-stroke: red; -fx-stroke-width: 4px;");

		}

		Stage stage = new Stage();

		Image image = new Image("file:map.png");
		ImageView imageView = new ImageView(image);

		Group pane = new Group(imageView);

		for (int i = 0; i < buildings.size(); i++) {
			
			Label label = new Label (buildings.get(i).name);
			label.setLayoutX(buildings.get(i).x);
			label.setLayoutY(buildings.get(i).y);
			label.setTextFill(Color.BLACK);
			BackgroundFill backgroundFill = new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, null);
			Background background = new Background(backgroundFill);
			label.setBackground(background);
			label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
			pane.getChildren().add(label);
			pane.getChildren().add(new Circle(buildings.get(i).x, buildings.get(i).y, 5));
		}

		for (int i = 0; i < lines.length; i++) {
			pane.getChildren().add(lines[i]);
		}

		Scene scene = new Scene(pane, image.getWidth(), image.getHeight());

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("the X of the click is = " + event.getX() + "and the Y = " + event.getY());
			}
		});

		stage.setScene(scene);
		stage.getIcons().add(new Image("file:iconjpg.jpg"));
		stage.setResizable(false);
		stage.setTitle("Map");
		stage.show();
	}

	void findDistance(String cb1, String cb2, Dijkstra dijkstra) {

		String startBuilding = cb1;
		String endBuilding = cb2;

		double distance = 0;

		List<Edge> path = dijkstra.getDijkstraPath(startBuilding, endBuilding);

		for (int i = 0; i < path.size(); i++) {
			distance += path.get(i).distance;
		}

		int dis = (int) distance;
		
		if (dis < 2 && !cb1.equals(cb2) )
			AlertBox.display("" , "The Distance" );
		else
			AlertBox.display("the total distance is " + dis , "The Distance" );


	}

	void findPath(String cb1, String cb2, Dijkstra dijkstra) {
		
		

		String startBuilding = cb1;
		String endBuilding = cb2;

		List<Edge> path = dijkstra.getDijkstraPath(startBuilding, endBuilding);

		String thePath = "";

		String prev = "";

		for (int i = 0; i < path.size(); i++) {

			if (path.get(i).source.name.equals(prev)) {
				thePath += "" + path.get(i).source.name + " --> : " + path.get(i).target.name + " --> distance is  "+path.get(i).distance +"\n";
				prev = path.get(i).target.name;
			} else
				thePath += "" + path.get(i).target.name + " --> : " + path.get(i).source.name + " --> distance is  "+path.get(i).distance +"\n";
			prev = path.get(i).source.name;
		}
		
		if (cb1.equals(cb2))
			AlertBox.display("" , "The Path" );
		else if (thePath.equals("")) 
			AlertBox.display("No Path" , "The Path" );
		else
			AlertBox.display(thePath , "The Path" );

	}

	public static void main(String[] args) {
		launch(args);
	}
}
