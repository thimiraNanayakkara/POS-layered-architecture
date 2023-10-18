package lk.ise.pos.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ise.pos.bo.BoFactory;
import lk.ise.pos.bo.custom.CustomerBo;
import lk.ise.pos.bo.custom.ItemBo;
import lk.ise.pos.bo.custom.OrderBo;
import lk.ise.pos.db.Database;
import lk.ise.pos.dto.CustomerDto;
import lk.ise.pos.dto.ItemDto;
import lk.ise.pos.dto.OrderDetailsDto;
import lk.ise.pos.dto.OrderDto;
import lk.ise.pos.entity.Customer;
import lk.ise.pos.entity.Item;
import lk.ise.pos.entity.Order;
import lk.ise.pos.entity.OrderDetails;
import lk.ise.pos.enums.BoType;
import lk.ise.pos.view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class PlaceOrderFormController {
    public ComboBox<String> cmbCustomerID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public ComboBox<String> cmbItemCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtRequestQty;
    public TableView tblCart;
    public TableColumn ColItemCode;
    public TableColumn ColDescription;
    public TableColumn ColUnitPrice;
    public TableColumn ColQty;
    public TableColumn ColTotal;
    public TableColumn ColOption;
    public Label lblTotal;
    public AnchorPane context;
    public Label lblOrderId;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);


    public void initialize(){

        ColItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        ColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        ColUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        ColQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        ColTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        ColOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadCustomerIds();
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setCustomer(newValue);
            }
        });

        loadItemIds();
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue!=null){
                setItem(newValue);
            }
        });

        loadOrderId();
    }

    private void loadOrderId() {
            try {
                lblOrderId.setText(orderBo.generateOrderId());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    private void loadItemIds() {

        try {
            ObservableList<String> oblist = FXCollections.observableArrayList(itemBo.loadItemCodes());
            cmbItemCode.setItems(oblist);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

   private void loadCustomerIds() {
       try {
           ObservableList<String> oblist = FXCollections.observableArrayList(customerBo.loadCustomerIds());
           cmbCustomerID.setItems(oblist);
       } catch (SQLException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
   }

    private void setCustomer(String id) {
        try {
           CustomerDto customer = customerBo.findCustomer(id);
            if (customer!=null){
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtSalary.setText(String.valueOf(customer.getSalary()));
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Customer not found", ButtonType.OK).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void setItem(String newValue) {
        ItemDto item = null;
        try {
            item = itemBo.findItem(newValue);
            if (item!=null){
                txtDescription.setText(item.getDescription());
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Item Not Found",ButtonType.OK).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    ObservableList<CartTM> tmlist = FXCollections.observableArrayList();
    public void addToCart(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtRequestQty.getText());
        double total = unitPrice*qty;

        if (ifExists(cmbItemCode.getValue())){
            for (CartTM tm:tmlist) {
                if (tm.getItemCode().equals(cmbItemCode.getValue())){
                    tm.setQty(tm.getQty()+qty);
                    tm.setTotal(tm.getTotal()+total);
                    tblCart.refresh();
                }
            }
        }
        else{
            Button btn= new Button("Delete");
            CartTM tm = new CartTM(cmbItemCode.getValue(),txtDescription.getText(),unitPrice, qty,total,btn);

            btn.setOnAction(e->{
                tmlist.remove(tm);
                tblCart.refresh();
                calculateTotal();
            });

            tmlist.add(tm);
        }

        tblCart.setItems(tmlist);
        clearData();
        calculateTotal();
    }

    private void calculateTotal() {
        double total = 0;
        for (CartTM tm:tmlist) {
            total+=tm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    private void clearData() {
        cmbItemCode.setValue(null);
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtRequestQty.clear();
    }

    public boolean ifExists(String code){
        for (CartTM tm:tmlist) {
            if (tm.getItemCode().equals(code)){
                return true;
            }
        }
        return false;
    }

    public void backTohomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader
                .load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
    }

    public void removeCartOnAction(ActionEvent actionEvent) {
        for (CartTM tm:tmlist) {
            if (tm.getItemCode().equals(cmbItemCode.getValue())){
                tmlist.remove(tm);
                tblCart.refresh();
            };
        }
    }

    public void saveOrder(ActionEvent actionEvent) {
        ArrayList<OrderDetailsDto> products = new ArrayList<>();
        for(CartTM tm:tmlist){
            products.add(new OrderDetailsDto(tm.getItemCode(), lblOrderId.getText(), tm.getUnitPrice(),tm.getQty()));
            manageQty(tm.getItemCode(),tm.getQty());
        }

        OrderDto orderDto = new OrderDto(
                lblOrderId.getText(),
                cmbCustomerID.getValue(),
                new Date(),
                Double.parseDouble(lblTotal.getText())
        );

        try {
            boolean isSaved = orderBo.saveOrder(orderDto,products);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Order Completed").show();

                tmlist.clear();
                tblCart.refresh();
                lblTotal.setText(String.valueOf(0));
                loadOrderId();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void manageQty(String itemCode, int qty) {
        for(Item i:Database.items){
            if(i.getCode().equals(itemCode)){
                i.setQtyOnHand(i.getQtyOnHand() - qty);
                return;
            }
        }
    }
}
