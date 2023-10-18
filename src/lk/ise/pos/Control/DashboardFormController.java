package lk.ise.pos.Control;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {
    public AnchorPane dashboardContext;
    public Label txtDateandTime;

    public void initialize(){
       //manageDateandTime();
    }

    private void manageDateandTime() {
        /*Timeline timeAndDate = new Timeline(new KeyFrame(Duration.ZERO,e->
            txtDateandTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm:ss")))),
               new KeyFrame(Duration.seconds(1))
        );
        timeAndDate.setCycleCount(Animation.INDEFINITE);
        timeAndDate.play();*/
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void openCustomerForm(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm");
    }

    public void openItemForm(ActionEvent actionEvent) throws IOException {
        setUi("ItemForm");
    }

    public void openOrdersForm(ActionEvent actionEvent) throws IOException {
        setUi("OrdersForm");
    }

    public void openNewOrderForm(ActionEvent actionEvent) throws IOException {
        setUi("PlaceOrderForm");
    }

    public void openIncomeForm(ActionEvent actionEvent) {
    }

    private void setUi(String formName) throws IOException {
        Stage stage = (Stage)dashboardContext.getScene().getWindow();

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+formName+".fxml"))));
        stage.centerOnScreen();

    }
}
