package lk.ise.pos.Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ise.pos.bo.BoFactory;
import lk.ise.pos.bo.custom.UserBo;
import lk.ise.pos.db.Database;
import lk.ise.pos.dto.UserDto;
import lk.ise.pos.entity.User;
import lk.ise.pos.enums.BoType;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginFormContext;
    public TextField txtUsername;
    public PasswordField pwd;

    private UserBo bo = BoFactory.getInstance().getBo(BoType.USER);

    public void initialize(){

    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        try {
            UserDto selectedUser = bo.findUser(txtUsername.getText());
            if (selectedUser != null) {
                if (BCrypt.checkpw(pwd.getText(), selectedUser.getPassword())) {
                    Stage stage = (Stage) loginFormContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader
                            .load(getClass().getResource("../view/DashboardForm.fxml"))));
                    stage.centerOnScreen();
                } else {
                    System.out.println("Wrong password");
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Username not found").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loginOnKeyPressed(KeyEvent event) throws IOException {
        if(event.getCode().equals(KeyCode.ENTER)) {
            try {
                UserDto selectedUser = bo.findUser(txtUsername.getText());
                if (selectedUser != null) {
                    if (BCrypt.checkpw(pwd.getText(), selectedUser.getPassword())) {
                        Stage stage = (Stage) loginFormContext.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader
                                .load(getClass().getResource("../view/DashboardForm.fxml"))));
                        stage.centerOnScreen();
                    } else {
                        System.out.println("Wrong password");
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Username not found").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

}
