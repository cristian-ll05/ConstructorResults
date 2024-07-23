import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Resultconstructor extends Application {

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        // Crear la tabla
        TableView<datos> tableView = new TableView<>();

        // Crear las columnas
        TableColumn<datos, Integer> idColumn = new TableColumn<>("Constructor ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("constructorId"));

        TableColumn<datos, String> refColumn = new TableColumn<>("Constructor Ref");
        refColumn.setCellValueFactory(new PropertyValueFactory<>("constructorRef"));

        TableColumn<datos, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<datos, String> nationalityColumn = new TableColumn<>("Nationality");
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        TableColumn<datos, String> urlColumn = new TableColumn<>("URL");
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));

        tableView.getColumns().addAll(idColumn, refColumn, nameColumn, nationalityColumn, urlColumn);

        // Conectar a la base de datos
        String url = "jdbc:mysql://localhost:3306/formula1";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM constructors")) {

            // Crear la lista de constructores para mostrar en la tabla
            ObservableList<datos> constructorList = FXCollections.observableArrayList();
            while (rs.next()) {
                datos constructor = new datos(
                        rs.getInt("constructorId"),
                        rs.getString("constructorRef"),
                        rs.getString("name"),
                        rs.getString("nationality"),
                        rs.getString("url")
                );
                constructorList.add(constructor);
            }

            // Asignar la lista de constructores a la tabla
            tableView.setItems(constructorList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Crear la escena y mostrar la tabla
        BorderPane root = new BorderPane();
        root.setCenter(tableView);

        Scene scene = new Scene(root, 750, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}