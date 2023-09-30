package com.solovev.util;

import com.solovev.model.Student;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class TableColumnFactory<T> {

    private TableColumn<T,Button> addDeleteButtonColumn(){
        TableColumn<T, Button> buttonTableColumn = new TableColumn<>("Delete");
        buttonTableColumn.setCellFactory(tc -> new TableCell<>(){
                    final Button btn = deleteButtonFactory();
                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                }
        );
        return buttonTableColumn;
    }

    private Button deleteButtonFactory(){
        Button deleteButton = new Button("Delete");
        deleteButton.setAlignment(Pos.CENTER);
        deleteButton.getStyleClass().add("danger");
        //todo add func
        deleteButton.setOnAction(event -> System.out.println("deleted"));
        return deleteButton;
    }
}
