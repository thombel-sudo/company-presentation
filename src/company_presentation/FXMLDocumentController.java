/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package company_presentation;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Контролер головного віка
 * @author Oleg
 */
public class FXMLDocumentController implements Initializable {
    int currentTextWork = 0;
    int currentTextInstruments = 0;
    File file1;
    File file2;
    Image image1;
    Image image2;
    Connection co;
    private ArrayList idsWork = new ArrayList();
    private ArrayList idsInstruments = new ArrayList();
    private ArrayList workText = new ArrayList();
    private ArrayList instrumentsText = new ArrayList();
    private ArrayList workPic = new ArrayList();
    private ArrayList instrumentsPic = new ArrayList();
    @FXML
    private Label lblMain;
    @FXML
    private ImageView pbWork,pbInstruments;
    @FXML
    private Button btnBackWork, btnNextWork,btnBackInstruments,btnNextInstruments;
    @FXML
    private Label lblWork,lblInstruments;
    @FXML
    private Pane panelAll, panelWork,panelInstruments;
    /**
     * Обробник натискання на кнопки меню
     * @param event 
     */
    @FXML
    private void btnAllInfoClick(ActionEvent event) {
        //mainPane.setVisible(false);
        panelAll.setVisible(true);
        panelWork.setVisible(false);
        panelInstruments.setVisible(false);
    }
    /**
     * Обробник натискання на кнопки меню
     * @param event 
     */
    @FXML
    private void btnWorkClick(ActionEvent event) {
        panelAll.setVisible(false);
        panelWork.setVisible(true);
        panelInstruments.setVisible(false);
    }
    /**
     * Обробник натискання на кнопки меню
     * @param event 
     */
    @FXML
    private void btnInstrumentsClick(ActionEvent event) {
        panelAll.setVisible(false);
        panelWork.setVisible(false);
        panelInstruments.setVisible(true);
    }
    /**
     * Обробник натискання на кнопки переходу на панель андміністрування
     * @param event 
     */
    @FXML
    private void adminPanelAction(MouseEvent event) {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("adminPanel.fxml"));
            stage.setTitle("Панель керування");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();
        }catch(Exception e ){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Обробник натискання на кнопки зміни сторінки
     * @param event 
     */
    @FXML
    private void btnNextWorkClick(ActionEvent event){
        currentTextWork+=1;
        btnBackWork.setDisable(false);
        lblWork.setText((String)workText.get(currentTextWork));
        
        if(currentTextWork == idsWork.size()-1){
        btnNextWork.setDisable(true);
        }
        file1 = new File("pics\\" + (String)workPic.get(currentTextWork));
        System.out.println(file1.getAbsolutePath());
        image1 = new Image(file1.toURI().toString());
        pbWork.setImage(image1);
    }
    /**
     * Обробник натискання на кнопки зміни сторінки
     * @param event 
     */
    @FXML
    private void btnBackWorkClick(ActionEvent event){
        currentTextWork-=1;
        btnNextWork.setDisable(false);
        file1 = new File("pics\\" + (String)workPic.get(currentTextWork));
        image1 = new Image(file1.toURI().toString());
        pbWork.setImage(image1);
        lblWork.setText((String)workText.get(currentTextWork));
        if(currentTextWork == 0){
        btnBackWork.setDisable(true);
        }
    }
    /**
     * Обробник натискання на кнопки зміни сторінки
     * @param event 
     */
    @FXML
    private void btnNextInstrumentsClick(ActionEvent event){
        currentTextInstruments+=1;
        btnBackInstruments.setDisable(false);
        lblInstruments.setText((String)instrumentsText.get(currentTextInstruments));
        
        if(currentTextInstruments == idsInstruments.size()-1){
        btnNextInstruments.setDisable(true);
        }
        file2 = new File("pics\\" + (String)instrumentsPic.get(currentTextInstruments));
        System.out.println(file2.getAbsolutePath());
        image2 = new Image(file2.toURI().toString());
        pbInstruments.setImage(image2);
    }
    /**
     * Обробник натискання на кнопки зміни сторінки
     * @param event 
     */
    @FXML
    private void btnBackInstrumentsClick(ActionEvent event){
        currentTextInstruments-=1;
        btnNextInstruments.setDisable(false);
        file2 = new File("pics\\" + (String)instrumentsPic.get(currentTextInstruments));
        image2 = new Image(file2.toURI().toString());
        pbInstruments.setImage(image2);
        lblInstruments.setText((String)instrumentsText.get(currentTextInstruments));
        if(currentTextInstruments == 0){
        btnBackInstruments.setDisable(true);
        }
    }
    /**
     * Ініціалізація вікна
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panelAll.setVisible(false);
        panelWork.setVisible(false);
        panelInstruments.setVisible(false);
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
        
        try
                {
                Statement statement = co.createStatement();
                String query = 
                        "SELECT id, text, pic "
                        + "FROM work "
                        + "ORDER BY id";
                ResultSet rs = statement.executeQuery (query);
                while (rs.next())
                        {
                         idsWork.add(rs.getString("id"));
                         System.out.println(rs.getString("id"));
                         workText.add(rs.getString("text"));
                         workPic.add(rs.getString("pic"));
                        }
                rs.close();
                statement.close();
                }
            catch (Exception e)
		{
		System.out.println( e.getMessage());
		}
         try
                {
                Statement statement = co.createStatement();
                String query = 
                        "SELECT id, text, pic "
                        + "FROM instruments "
                        + "ORDER BY id";
                ResultSet rs = statement.executeQuery (query);
                while (rs.next())
                        {
                         idsInstruments.add(rs.getString("id"));
                         System.out.println(rs.getString("id"));
                         instrumentsText.add(rs.getString("text"));
                         instrumentsPic.add(rs.getString("pic"));
                        }
                rs.close();
                statement.close();
                }
            catch (Exception e)
		{
		System.out.println( e.getMessage());
		}
        try{ 
        lblWork.setText((String)workText.get(0));
        lblInstruments.setText((String)instrumentsText.get(0));
        
        btnBackWork.setDisable(true);
        btnBackInstruments.setDisable(true);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        file1 = new File("pics\\" + (String)workPic.get(0));
        System.out.println(file1.getAbsolutePath());
        image1 = new Image(file1.toURI().toString());
        pbWork.setImage(image1);
        
        
        file2 = new File("pics\\" + (String)instrumentsPic.get(0));
        System.out.println(file2.getAbsolutePath());
        image2 = new Image(file2.toURI().toString());
        pbInstruments.setImage(image2);
    }    
    
}
