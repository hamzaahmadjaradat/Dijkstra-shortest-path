module com.example.dijkstra {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.dijkstra to javafx.fxml;
    exports com.example.dijkstra;
}