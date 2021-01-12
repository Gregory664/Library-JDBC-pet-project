package org.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.cli.*;
import org.library.utils.MySQLConnection;
import org.library.utils.Utils;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        initConnection(args);
        launch();
    }

    private static void initConnection(String[] args) {
        String host;
        String port;
        String dbName;
        String dbUserName;
        String dbPassword;
        
        if(args.length == 0) {
             host = "localhost";
             port = "3306";
             dbName = "library";
             dbUserName = "lib";
             dbPassword = "!Library777";
        } else {
            Options options = getOptions();
            CommandLineParser parser = new DefaultParser();
            HelpFormatter formatter = new HelpFormatter();
            CommandLine commandLine = null;
            try {
                commandLine = parser.parse(getOptions(), args);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                formatter.printHelp("Library", options);

                System.exit(1);
            }
            host = commandLine.getOptionValue("host");
            port = commandLine.getOptionValue("port");
            dbName = commandLine.getOptionValue("dbName");
            dbUserName = commandLine.getOptionValue("dbUserName");
            dbPassword = commandLine.getOptionValue("dbPassword");
        }
       
        MySQLConnection.init(host, port, dbName, dbUserName, dbPassword);
    }
    
    private static Options getOptions() {
        Options options = new Options();
        Utils.addCLIOption(options, "h", "host", "sql server host");
        Utils.addCLIOption(options, "p", "port", "sql server port");
        Utils.addCLIOption(options, "dbN", "dbName", "database name");
        Utils.addCLIOption(options, "dbUN", "dbUserName", "username");
        Utils.addCLIOption(options, "dbP", "dbPassword", "username");
        return options;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"));
        scene.getStylesheets().add(App.class.getResource("main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}