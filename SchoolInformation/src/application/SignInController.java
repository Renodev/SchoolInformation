package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SignInController {
	
	@FXML
	private Button signInButton;
	
	@FXML
	private TextField phoneTextField;
	
	@FXML
	private TextField passwordTextField;
	
	@FXML
	public void clear () {
		phoneTextField.setText("");
		passwordTextField.setText("");
	}
	
	@FXML
	public void signIn () {
		try {
			String role = null;
			String phone = phoneTextField.getText();
			String password = passwordTextField.getText();
			Connection connection = DBConnection.getDBConnection();
			String sql = "select role from account where phone = '"
						+phone+"' and password = '"+password+"'";
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(sql);
			while (resultset.next()) {
				role = resultset.getString(1);
			}
			if (role.equals("Admin")) {
				switchPage("accountConfiguration.fxml");
			} else if (role.equals("Teacher")) {
				switchPage("teacherNewsFeed.fxml");
			} else {
				switchPage("studentNewsFeed.fxml");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void switchPage (String url) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource(url));
			Scene scene = new Scene(root);
			Stage stage = (Stage) signInButton.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}














