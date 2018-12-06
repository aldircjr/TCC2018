/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author aldir
 */
public class UsuarioNovoFX extends Application {
    
    private static Stage stage;
    
    
    @Override
    public void start(Stage stage) throws Exception {
//        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
//        stage.resizableProperty().setValue(Boolean.FALSE);
        Parent root = FXMLLoader.load(getClass().getResource("/View/FXMLUsuarioNovo.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
    
    

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        launch(args);
//    }
    
    
    
}
