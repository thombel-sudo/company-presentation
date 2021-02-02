/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package company_presentation;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

/**
 * Контроль панелі адміністрування
 *
 * @author User
 */
public class AdminPanelController implements Initializable {
    
    
    @FXML
    private Pane panelAdd,panelDel;
    @FXML
    private ComboBox cbThemeDel, cbPageDel, cbAddTheme;
    @FXML
    private Button btnDelete, btnAdd;
    @FXML
    private TextArea tfAdd;
    Connection co;
    ArrayList<String> pages;
    /**
     * Обробник вибору елемента зі списка
     * @param event 
     */
    @FXML
    private void cbAddThemeChanged(ActionEvent event){
        btnAdd.setDisable(false);
    }
    /**
     * Обробник вибору елемента зі списка
     * @param event 
     */
    @FXML
    private void cbPageChanged(ActionEvent event){
        btnDelete.setDisable(false);
    }
    /**
     * Обробник вибору елемента зі списка
     * @param event 
     */
    @FXML
    private void cbThemeDelClick(ActionEvent event){
        cbPageDel.getItems().clear();
        btnDelete.setDisable(true);
        pages = new ArrayList<String>();    
                try
                {
                Statement statement = co.createStatement();
                String query = 
                        "SELECT text "
                        + "FROM " + cbThemeDel.getValue().toString();
                System.out.println(query);
                ResultSet rs = statement.executeQuery (query);
                while (rs.next())
                        {
                         pages.add(rs.getString("text"));
                        }
                rs.close();
                statement.close();
                }
                catch (Exception e)
		{
		System.out.println( e.getMessage());
		} 
                cbPageDel.getItems().addAll(pages);
    }
    /**
     * Обробник додавання запису при натискані відповідної кнопки
     * @param event 
     */
    @FXML
    private void btnAddClicked(ActionEvent event){
        String theme = cbAddTheme.getValue().toString();
        try
		{
                String text = tfAdd.getText();
                
		String query = 
		"INSERT INTO "+theme +"(text, pic) " +
		"VALUES ('" + text + "', 'web.png')";
		Statement statement = co.createStatement();
		statement.executeUpdate(query);
                statement.close();
                System.out.println("Success");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результат дії");
                alert.setHeaderText(null);
                alert.setContentText("Запит виконано!");
                alert.showAndWait();
		}
		catch (Exception e)
		{
		System.out.println( e.getMessage());
		}
    }
    /**
     * Обробник видалення елементу з БД при натисканні кнопки
     * @param event 
     */
    @FXML
    private void btnDeleteClicked(ActionEvent event){
        try
		{
		String theme = cbThemeDel.getValue().toString();
                String page = cbPageDel.getValue().toString();
		String query = 
		"DELETE FROM "+  theme + 
		" WHERE text= " + "'"+ page + "'" +  ";";
		Statement statement = co.createStatement();
		statement.executeUpdate(query);
                statement.close();
                System.out.println("Success");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результат дії");
                alert.setHeaderText(null);
                alert.setContentText("Запит виконано!");
                alert.showAndWait();
		}
		catch (Exception e)
		{
		System.out.println( e.getMessage());
		}
    }
    @FXML
    private void btnMenuAddClicked(ActionEvent event){
        panelDel.setVisible(false);
        panelAdd.setVisible(true);
    }
    @FXML
    private void btnMenuDelClicked(ActionEvent event){
        panelDel.setVisible(true);
        panelAdd.setVisible(false);
    }
    /**
     * Ініціалізація вікна
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panelDel.setVisible(false);
        panelAdd.setVisible(true);
        try
		{
			Class.forName("org.sqlite.JDBC");
			co = DriverManager.getConnection ( "jdbc:sqlite:database\\information.db" );
			System.out.println("Connected");
                       
		}
		catch (Exception e)
		{
			System.out.println( e.getMessage());
                       
		}
        cbThemeDel.getItems().addAll("work", "instruments");
        cbAddTheme.getItems().addAll("work", "instruments");
    }    
    
}
