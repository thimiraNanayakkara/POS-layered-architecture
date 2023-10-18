package lk.ise.pos.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ise.pos.db.Database;
import lk.ise.pos.entity.Order;
import lk.ise.pos.view.tm.OrderTM;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class OrdersFormController {
    public AnchorPane context;
    public TableView<OrderTM> tblOrders;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colCost;
    public TableColumn colDate;
    public Button btnBackToHome;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadData();


        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    loadDetails(newValue.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        });
    }

    private void loadDetails(String id) throws IOException {
        //load UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OrderDetailsForm.fxml"));
        Parent load = loader.load();
        OrderDetailsFormController controller = loader.getController();
        controller.setOrder(id);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    private void loadData() {
        ObservableList<OrderTM> observableList = FXCollections.observableArrayList();
        for (Order order:Database.orders){
            observableList.add(new OrderTM(
                    order.getOrderId(),
                    Database.customers.stream().
                            filter(e-> e.getId().equals(order.getCustomer())).
                            findFirst().get().getName(),
                    order.getTotal(),
                    new SimpleDateFormat("yyyy-MM-dd").format(order.getDate())
            ));
        }
        tblOrders.setItems(observableList);

    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader
                .load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
    }
}
