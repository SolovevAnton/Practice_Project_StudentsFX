package com.solovev.util;

import com.solovev.model.Student;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
                        buttons.forEach((node) -> addButtonSelectRowAction((Button) node));
                    }
                    private void addButtonSelectRowAction(Button button){
                        EventHandler<ActionEvent> eventHandler = button.getOnAction();
                        button.setOnAction((event)-> {
                            getTableView().getSelectionModel().select(getIndex());
                            eventHandler.handle(event);
                        });
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

    public void addHeader(Button button) {
        HBox buttonWrapper = new HBox(button);
        buttonWrapper.setAlignment(Pos.CENTER_RIGHT);
        buttonTableColumn.setGraphic(buttonWrapper);
    }
}
