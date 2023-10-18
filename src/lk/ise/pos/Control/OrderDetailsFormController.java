package lk.ise.pos.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ise.pos.db.Database;
import lk.ise.pos.entity.Order;
import lk.ise.pos.entity.OrderDetails;
import lk.ise.pos.view.tm.OrderDetailsTM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

public class OrderDetailsFormController {
    public TextField txtOrderId;
    public TextField txtCustomerName;
    public TextField txtDate;
    public TextField txtTotalCost;
    public TableView<OrderDetailsTM> tblOrderDetails;
    public TableColumn colItem;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;

    public void initialize(){
        colItem.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        loadDetails();

    }


    public void setOrder(String orderId){
        Optional<Order> order = Database.orders.stream().filter(e -> e.getOrderId().equals(orderId)).findFirst();
        if (!order.isPresent()){
            new Alert(Alert.AlertType.WARNING,"Not Found").show();
            return;
        }

        txtOrderId.setText(order.get().getOrderId());
        txtCustomerName.setText(
                Database.customers.stream().
                filter(e-> e.getId().equals(order.get().getCustomer())).
                findFirst().get().getName()
        );
        txtDate.setText(
                new SimpleDateFormat("yyyy-MM-dd").format(order.get().getDate())
        );
        txtTotalCost.setText(String.valueOf(order.get().getTotal()));

        loadDetails();
    }

    private void loadDetails() {

       // ===============================================================================
        /*ObservableList<OrderDetailsTM> tmlist = FXCollections.observableArrayList();
        for (Order order:Database.orders){
            if (order.getOrderId().equals(txtOrderId.getText())){
                ArrayList<OrderDetails> products = order.getProducts();

                String code = String.valueOf(products.get(0));
                String description = "";
                int qty = Integer.parseInt(String.valueOf(products.get(2)));
                double unitPrice = Double.parseDouble(String.valueOf(products.get(2)));

                OrderDetailsTM tm = new OrderDetailsTM(code,description,qty,unitPrice);
                tmlist.add(tm);
            }
        }
        tblOrderDetails.setItems(tmlist);*/

        //===========================================================================================




    }


}
