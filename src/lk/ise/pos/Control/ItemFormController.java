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
import lk.ise.pos.db.Database;
import lk.ise.pos.entity.Customer;
import lk.ise.pos.entity.Item;
import lk.ise.pos.view.tm.CustomerTM;
import lk.ise.pos.view.tm.ItemTM;

import java.io.IOException;
import java.util.Optional;

import static javafx.collections.FXCollections.observableArrayList;

public class ItemFormController {
    public AnchorPane ItemFormContext;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public Button btnSaveItem;
    public TableView tbl;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQtyOnHand;
    public TableColumn colUnitPrice;
    public TableColumn colOptions;

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("btn"));

        tbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((ItemTM) newValue);
            }
        });

        loadAll();
    }

    private void setData(ItemTM newValue) {
        txtCode.setText(newValue.getCode());
        txtDescription.setText(newValue.getDescription());
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        btnSaveItem.setText("Update Item");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)ItemFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader
                .load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
    }


    public void newItemOnAction(ActionEvent actionEvent) {
        btnSaveItem.setText("Save Item");
        clearData();
    }

    public void saveItem(ActionEvent actionEvent) {
        Item item = new Item(txtCode.getText(),txtDescription.getText(),Integer.parseInt(txtQtyOnHand.getText()),
                Double.parseDouble(txtUnitPrice.getText()));
        if(btnSaveItem.getText().equals("Save Item")) {
            Database.items.add(item);

            new Alert(Alert.AlertType.INFORMATION, "Item added", ButtonType.OK).show();
            loadAll();
        }
        else {
            for (Item i:Database.items) {
                if(i.getCode().equals(txtCode.getText())){
                    i.setCode(txtCode.getText());
                    i.setDescription(txtDescription.getText());
                    i.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
                    i.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));

                    new Alert(Alert.AlertType.INFORMATION,"Item Updated",ButtonType.OK).show();
                    btnSaveItem.setText("Save Item");
                    loadAll();
                }

            }

        }
        clearData();
    }

    private void loadAll() {
        ObservableList<ItemTM> tmlist = FXCollections.observableArrayList();
        for (Item i:Database.items) {
            Button btn = new Button("Delete");
            ItemTM tm = new ItemTM(i.getCode(),i.getDescription(),i.getQtyOnHand(),i.getUnitPrice(),btn);

            btn.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.WARNING,"Are you sure?",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> type = alert.showAndWait();
                if(type.get()==ButtonType.YES){
                    Database.items.remove(i);
                    new Alert(Alert.AlertType.INFORMATION,"Item Deleted").show();
                    loadAll();
                    clearData();
                }

            });

            tmlist.add(tm);
        }

        tbl.setItems(tmlist);
    }

    public void clearData(){
        txtCode.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
    }
}
