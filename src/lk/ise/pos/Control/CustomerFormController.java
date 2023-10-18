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
import lk.ise.pos.db.Database;
import lk.ise.pos.dto.CustomerDto;
import lk.ise.pos.entity.Customer;
import lk.ise.pos.enums.BoType;
import lk.ise.pos.view.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane customerFormContext;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView<CustomerTM> tbl;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOptions;
    public Button btnSaveCustomer;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("btn"));

        // set the data of the selected customerTM into the fields.
        tbl.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData(newValue);
            }
        }));
    }

    private void setData(CustomerTM newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
        btnSaveCustomer.setText("Update Customer");
    }

    public void saveCustomer(ActionEvent actionEvent) {
        CustomerDto c = new CustomerDto(txtId.getText(),txtName.getText(),txtAddress.getText(),
                Double.parseDouble(txtSalary.getText()));

        if (btnSaveCustomer.getText().equals("Save Customer")){
            try{
                if (customerBo.saveCustomer(c)){
                    new Alert(Alert.AlertType.INFORMATION,"Customer Saved").show();
                    loadAll("");
                }
                else {
                    new Alert(Alert.AlertType.WARNING,"try again").show();
                }
            }
            catch (Exception exception){
                exception.printStackTrace();
                new Alert(Alert.AlertType.ERROR,exception.getMessage()).show();
            }

        }
        else {

            try {
                if (customerBo.updateCustomer(c)){
                    new Alert(Alert.AlertType.INFORMATION,"Customer Updated").show();
                    loadAll("");
                }
                else {
                    new Alert(Alert.AlertType.WARNING,"try again").show();
                }
            } catch (ClassNotFoundException| SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        clearData();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)customerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader
                .load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
    }

    private void clearData() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }

    public void loadAll(String searchText){

        ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();

        try{
            for (CustomerDto c:customerBo.findAllCustomers()) {
                Button btn = new Button("Delete");
                CustomerTM tm = new CustomerTM(c.getId(),c.getName(),c.getAddress(),c.getSalary(),btn);

                btn.setOnAction(e->{
                    Alert alert = new Alert(Alert.AlertType.WARNING,"Are you sure?",ButtonType.YES,
                            ButtonType.NO);
                    Optional<ButtonType> type = alert.showAndWait();
                    if(type.get()==ButtonType.YES){
                        try{
                            if (customerBo.deleteCustomer(c.getId())){
                                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted").show();
                                loadAll("");
                            }
                            else {
                                new Alert(Alert.AlertType.WARNING,"try again").show();
                            }

                        }catch (ClassNotFoundException| SQLException exception) {
                            exception.printStackTrace();
                            new Alert(Alert.AlertType.ERROR,exception.getMessage()).show();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                });

                tmList.add(tm);
            }
            tbl.setItems(tmList);
        }catch (ClassNotFoundException| SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        clearData();
        btnSaveCustomer.setText("Save Customer");
    }
}
