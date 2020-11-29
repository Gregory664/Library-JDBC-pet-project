package org.library.utils;

import javafx.scene.control.ButtonBase;
import javafx.stage.Stage;

public class Utils {
    public static Stage getStage(ButtonBase buttonBase) {
        return (Stage) buttonBase.getScene().getWindow();
    }
}
