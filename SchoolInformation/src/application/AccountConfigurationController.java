package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class AccountConfigurationController implements Initializable {
	
	@FXML
	private Text notificationText;
	
	@FXML
	private TextField nameTextField;
	
	@FXML
	private TextField phoneTextField;
	
	@FXML
	private TextField passwordTextField;
	
	@FXML
	private TextField roleTextField;
	
	@FXML
	private TextArea addressTextArea;
	
	@FXML
	private TableView<Account> accountTable;
	
	@FXML
	private TableColumn<Account, Integer> idCol;
	
	@FXML
	private TableColumn<Account, String> nameCol;
	
	@FXML
	private TableColumn<Account, String> phoneCol;
	
	@FXML
	private TableColumn<Account, String> passwordCol;
	
	@FXML
	private TableColumn<Account, String> roleCol;
	
	@FXML
	private TableColumn<Account, String> addressCol;
	
	private int id;
	
	private Connection connection;
	
	private ObservableList<Account> observableList;
	
	@FXML
	public void clear () {
		id = 0;
		nameTextField.setText("");
		phoneTextField.setText("");
		passwordTextField.setText("");
		roleTextField.setText("");
		addressTextArea.setText("");
	}
	
	@FXML
	public void save () {
		try {
			String name = nameTextField.getText();
			String phone = phoneTextField.getText();
			String password = passwordTextField.getText();
			String role = roleTextField.getText();
			String address = addressTextArea.getText();
			connection = DBConnection.getDBConnection();
			String sql = null;
			if (id == 0) {
				sql = "insert into account (name, phone, password, address, role) values ("
						+ "'"+name+"', '"+phone+"', '"+password+"', '"+address+"', '"+role+"' )";
			} else {
				sql = "update account set name = '"+name+"', phone = '"+phone+"', password = '"
						+password+"', address = '"+address+"', role = '"+role+"' where id = "+id;
			}
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
			clear();
			notificationText.setText("Save Record Successfully");
			setUpTable();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@FXML
	public void delete () {
		try {
			Account account = accountTable.getSelectionModel().getSelectedItem();
			connection = DBConnection.getDBConnection();
			String sql = "delete from account where id = " + account.getId();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
			notificationText.setText("Delete Record Successfully");
			setUpTable();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@FXML
	public void update () {
		Account account = accountTable.getSelectionModel().getSelectedItem();
		id = account.getId();
		nameTextField.setText(account.getName());
		phoneTextField.setText(account.getPhone());
		passwordTextField.setText(account.getPassword());
		roleTextField.setText(account.getRole());
		addressTextArea.setText(account.getAddress());
	}
	
	public ArrayList<Account> getAccountData () {
		try {
			ArrayList<Account> accounts = new ArrayList<Account>();
			connection = DBConnection.getDBConnection();
			String sql = "select * from account";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Account account = new Account ();
				account.setId(resultSet.getInt(1));
				account.setName(resultSet.getString(2));
				account.setPhone(resultSet.getString(3));
				account.setPassword(resultSet.getString(4));
				account.setRole(resultSet.getString(6));
				account.setAddress(resultSet.getString(5));
				accounts.add(account);
			}
			return accounts;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void setUpTable () {
		idCol.setCellValueFactory(new PropertyValueFactory<Account, Integer>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("name"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<Account, String>("phone"));
		passwordCol.setCellValueFactory(new PropertyValueFactory<Account, String>("password"));
		roleCol.setCellValueFactory(new PropertyValueFactory<Account, String>("role"));
		addressCol.setCellValueFactory(new PropertyValueFactory<Account, String>("address"));
		observableList = FXCollections.observableArrayList(getAccountData());
		accountTable.setItems(observableList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setUpTable();
	}
	
}














