import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConstructorResultsApp extends Application {

    private ObservableList<ConstructorResults> constructorResults = FXCollections.observableArrayList();

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        // Crear la tabla de resultados de constructores
        TableView<ConstructorResults> tableView = new TableView<>();
        tableView.setItems(constructorResults);

        TableColumn<ConstructorResults, Integer> constructorResultIdCol = new TableColumn<>("Results ID");
        constructorResultIdCol.setPrefWidth(80);
        TableColumn<ConstructorResults, Integer> raceIdCol = new TableColumn<>("Carrera");
        TableColumn<ConstructorResults, Integer> constructorIdCol = new TableColumn<>("Constructor ID");
        TableColumn<ConstructorResults, Double> pointsCol = new TableColumn<>("Puntos");
        TableColumn<ConstructorResults, String> statusCol = new TableColumn<>("Estado");

        constructorResultIdCol.setCellValueFactory(new PropertyValueFactory<>("constructorResultId"));
        raceIdCol.setCellValueFactory(new PropertyValueFactory<>("raceId"));
        constructorIdCol.setCellValueFactory(new PropertyValueFactory<>("constructorId"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().addAll(constructorResultIdCol, raceIdCol, constructorIdCol, pointsCol, statusCol);

       

        // Conectarse a la base de datos
        String url = "jdbc:mysql://localhost:3306/formula1";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Recuperar los datos de la base de datos
            String query = "SELECT * FROM constructorresults";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int constructorResultId = rs.getInt("constructorResultId");
                int raceId = rs.getInt("raceId");
                int constructorId = rs.getInt("constructorId");
                double points = rs.getDouble("points");
                String status = rs.getString("status");

                ConstructorResults constructorResult = new ConstructorResults(constructorResultId, raceId, constructorId, points, status);
                constructorResults.add(constructorResult);
            }
        } catch (SQLException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
        }

        // Crear botones para filtrar y resetear
        Button filterButton = new Button("Constructors con más puntos");
        filterButton.setOnAction(event -> {
            ObservableList<ConstructorResults> filteredResults = FXCollections.observableArrayList();
            ConstructorResults maxPointsResult = null;
            double maxPoints = 0;

            for (ConstructorResults result : constructorResults) {
                if (result.getPoints() > maxPoints) {
                    maxPoints = result.getPoints();
                    maxPointsResult = result;
                }
            }

            if (maxPointsResult != null) {
                filteredResults.add(maxPointsResult);
                tableView.setItems(filteredResults);
            }
        });
        filterButton.setStyle("-fx-background-color: #2F4F7F; -fx-text-fill: #ffffff;");

        Button resetButton = new Button("Todos los contructors");
        resetButton.setOnAction(event -> {
            tableView.setItems(constructorResults);
        });
        resetButton.setStyle("-fx-background-color: #2F4F7F; -fx-text-fill: #ffffff;");
        

        // Crear la escena y el stage
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        

        Label label = new Label("Resultados de Constructors");
        root.setTop(label);

        root.setCenter(tableView);

        root.setBottom(new BorderPane(filterButton, null, null, resetButton, null));


        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
