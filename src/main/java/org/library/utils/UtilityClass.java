package org.library.utils;

import javafx.scene.control.ButtonBase;
import javafx.stage.Stage;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.library.entity.Book;
import org.library.entity.Shelf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UtilityClass {
    private UtilityClass() {
        throw new AssertionError();
    }

    public static Stage getStage(ButtonBase buttonBase) {
        return (Stage) buttonBase.getScene().getWindow();
    }

    public static void updateBook(Book forUpdate, Book updated) {
        forUpdate.setTitle(updated.getTitle());
        forUpdate.setAuthor(updated.getAuthor());
        forUpdate.setGenre(updated.getGenre());
        forUpdate.setPublisher(updated.getPublisher());
        forUpdate.setLength(updated.getLength());
    }

    public static String getProperty(String property) {
        String result = "";
        try {
            String propertyPath = "connectionProperties.prop";
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyPath);
            if (inputStream != null) {
                Properties properties = new Properties();
                properties.load(inputStream);
                return properties.getProperty(property);
            } else {
                throw new FileNotFoundException(String.format("property file '%s' not found!", propertyPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void resetShelf(Shelf shelf) {
        shelf.setId(-1);
        shelf.setInventNum("Нет данных");
    }

    public static void updateShelf(Shelf shelf, Shelf newShelf) {
        shelf.setId(newShelf.getId());
        shelf.setInventNum(newShelf.getInventNum());
    }

    public static void addCLIOption(Options options, String shortName, String longName, String description) {
        options.addOption(Option.builder(shortName)
                .longOpt(longName)
                .hasArg()
                .desc(description)
                .required()
                .build()
        );
    }
}
