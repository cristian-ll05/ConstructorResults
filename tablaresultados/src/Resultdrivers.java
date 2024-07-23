import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Resultdrivers extends Application {

    private TableView<drivers> tableView;
    private ObservableList<drivers> driverList;
    private ComboBox<Integer> yearComboBox;
    

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        // Conectar a la base de datos
        String url = "jdbc:mysql://localhost:3306/formula1";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Crear la tabla de drivers
            tableView = new TableView<>();
            tableView.setPrefSize(600, 400);
            

            // Crear columnas de la tabla
            TableColumn<drivers, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<drivers, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<drivers, String> teamColumn = new TableColumn<>("Team");
            teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
            TableColumn<drivers, Integer> winsColumn = new TableColumn<>("Wins");
            winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
            TableColumn<drivers, Integer> pointsColumn = new TableColumn<>("Points");
            pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
            TableColumn<drivers, Integer> positionColumn = new TableColumn<>("Position");
            positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
            TableColumn<drivers, Integer> yearColumn = new TableColumn<>("Year");
            yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

            tableView.getColumns().addAll(idColumn, nameColumn, teamColumn, winsColumn, pointsColumn, positionColumn, yearColumn);

            // Cargar datos de la base de datos
            loadDrivers(conn);

            // Crear comboBox para filtrar por año
            yearComboBox = new ComboBox<>();
            loadYears(conn, yearComboBox);

            // Crear botón para filtrar
            Button filterButton = new Button("Filtrar");
            filterButton.setStyle("-fx-background-color: #00CC00; -fx-text-fill: #ffffff;");
            filterButton.setOnAction(e -> filterByYear());

            // Crear botón para resetear filtro
            Button resetButton = new Button("Resetear filtro");
            resetButton.setOnAction(e -> tableView.setItems(driverList));
            resetButton.setStyle("-fx-background-color: #FF0033; -fx-text-fill: #ffffff;");

            // Crear el layout
            BorderPane root = new BorderPane();

            // Agregar la tabla al centro del BorderPane
            root.setCenter(tableView);

            // Crear un HBox para los controles de filtrado
            HBox filterBox = new HBox(10);
            filterBox.getChildren().addAll(yearComboBox, filterButton, resetButton);
            filterBox.setAlignment(Pos.CENTER_LEFT);

            // Agregar el HBox al top del BorderPane
            root.setTop(filterBox);


            // Crear la escena y mostrarla
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();

            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDrivers(Connection conn) throws SQLException {
        // Consulta SQL para cargar todos los drivers
        String query = "SELECT * FROM driversresult";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        driverList = FXCollections.observableArrayList();
        while (rs.next()) {
            drivers driver = new drivers(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("team"),
                    rs.getInt("wins"),
                    rs.getInt("points"),
                    rs.getInt("position"),
                    rs.getInt("year")
            );
            driverList.add(driver);
        }
        tableView.setItems(driverList);
    }

    private void loadYears(Connection conn, ComboBox<Integer> comboBox) throws SQLException {
        // Consulta SQL para cargar los años existentes
        String query = "SELECT DISTINCT year FROM driversresult";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        ObservableList<Integer> years = FXCollections.observableArrayList();
        while (rs.next()) {
            years.add(rs.getInt("year"));
        }
        comboBox.setItems(years);
    }

    private void filterByYear() {
        // Obtener el año seleccionado en el comboBox
        Integer selectedYear = yearComboBox.getSelectionModel().getSelectedItem();

        // Filtrar los drivers por año
        ObservableList<drivers> filteredList = FXCollections.observableArrayList();
        for (drivers driver : driverList) {
            if (driver.getYear() == selectedYear) {
                filteredList.add(driver);
            }
        }
        tableView.setItems(filteredList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


