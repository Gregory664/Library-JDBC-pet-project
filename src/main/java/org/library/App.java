package org.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.cli.*;
import org.library.utils.MySQLConnection;
import org.library.utils.UtilityClass;

import java.io.IOException;

public class App extends Application {

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

        if (args.length == 0) {
            host = UtilityClass.getProperty("host");
            port = UtilityClass.getProperty("port");
            dbName = UtilityClass.getProperty("dbName");
            dbUserName = UtilityClass.getProperty("dbUserName");
            dbPassword = UtilityClass.getProperty("dbPassword");
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
        UtilityClass.addCLIOption(options, "h", "host", "sql server host");
        UtilityClass.addCLIOption(options, "p", "port", "sql server port");
        UtilityClass.addCLIOption(options, "dbN", "dbName", "database name");
        UtilityClass.addCLIOption(options, "dbUN", "dbUserName", "username");
        UtilityClass.addCLIOption(options, "dbP", "dbPassword", "username");
        return options;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("main"));
        scene.getStylesheets().add(App.class.getResource("main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}