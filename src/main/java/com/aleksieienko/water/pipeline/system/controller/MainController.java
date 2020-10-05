package com.aleksieienko.water.pipeline.system.controller;

import com.aleksieienko.water.pipeline.system.db.GraphDao;
import com.aleksieienko.water.pipeline.system.parser.CSVParser;
import com.aleksieienko.water.pipeline.system.parser.Parser;
import com.aleksieienko.water.pipeline.system.pipeline.Graph;
import com.aleksieienko.water.pipeline.system.pipeline.Question;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class MainController {
    @FXML
    public Label dataInfo;
    private Graph graph = new Graph();

    @FXML
    public void upload(ActionEvent actionEvent) {
        FileChooser fileChooser = getFileChooser("Open file",false);
        File file;

        if ((file = fileChooser.showOpenDialog(null)) != null) {
            try {
                Parser parser = new CSVParser();
                Object obj=parser.parse(file);

                if(obj instanceof Graph){

                    graph = (Graph) obj;
                    dataInfo.setText("Pipeline is accepted!");
                    GraphDao graphDao = new GraphDao();
                    if(!graphDao.insertGraph(file.getPath())){
                        showAlert("Cannot write data to database!!!");
                    }
                }else{
                    if(graph.getPoints().isEmpty()) {
                        showAlert("Pipeline is not accepted!!!");
                    } else {
                        FileChooser fileChooserForSave = getFileChooser("Select directory for save", true);
                        File fileForSave;
                        if ((fileForSave = fileChooserForSave.showSaveDialog(null)) != null) {

                            if (!fileForSave.exists()) {

                                fileForSave.createNewFile();
                            }

                            parser.write(fileForSave, (List<Question>) obj, this.graph);
                            showResult(fileForSave);
                        }
                    }
                }

            } catch (IOException e) {
                showAlert("Reading or writing error!!!");
                e.printStackTrace();
            } catch (SQLException e) {
                showAlert("Wrong connection to database!");
                e.printStackTrace();
            }
        }
    }

    private FileChooser getFileChooser(String title, String format, Boolean isSave) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));

        if(isSave) {

            fileChooser.setInitialFileName("result");
        }

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(format.toUpperCase() + "-files (*." + format + ")", "*." + format));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
        fileChooser.setTitle(title);

        return fileChooser;
    }

    private FileChooser getFileChooser(String title,Boolean isSave) {

        return getFileChooser(title, "csv",isSave);
    }

    private void showResult(File file) throws IOException {
        Desktop.getDesktop().open(file);
    }

    private void showAlert(String body) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(body);
        alert.showAndWait();
    }
}
