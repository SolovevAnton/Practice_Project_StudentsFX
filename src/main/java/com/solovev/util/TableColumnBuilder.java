package com.solovev.util;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TableColumnBuilder<T> {
    private final List<Supplier<Button>> listOfButtonSuppliers = new ArrayList<>();
    private final TableColumn<T, Button> buttonTableColumn = new TableColumn<>();
    public TableColumn<T, Button> getColumnWithButtons() {
        buttonTableColumn.setCellFactory(tc -> new TableCell<>() {
                    final ButtonBar buttonBar = new ButtonBar();
                    final ObservableList<Node> buttons = buttonBar.getButtons();

                    {
                        listOfButtonSuppliers.forEach(supplier -> buttons.add(supplier.get()));
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(buttonBar);
                            setText(null);
                        }
                    }
                }
        );
        return buttonTableColumn;
    }

    public void addButtonToColumn(Supplier<Button> buttonSupplier) {
        listOfButtonSuppliers.add(buttonSupplier);
    }
    public void addHeader(Button button){
        HBox buttonWrapper = new HBox(button);
        buttonWrapper.setAlignment(Pos.CENTER_RIGHT);
        buttonTableColumn.setGraphic(buttonWrapper);
    }
}
