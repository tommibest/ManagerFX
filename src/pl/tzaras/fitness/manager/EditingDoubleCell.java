package pl.tzaras.fitness.manager;

import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class EditingDoubleCell<T,S> extends TableCell<T, S> {
	 
    private TextField textField;
   
    public EditingDoubleCell() {}
   
    @Override
    public void startEdit() {
        super.startEdit();
       
        if (textField == null) {
            createTextField();
        }
       
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.selectAll();
    }
   
    @Override
    public void cancelEdit() {
        super.cancelEdit();
       
        setText(String.valueOf(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(S item, boolean empty) {
        super.updateItem(item, empty);
       
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                	S val = (S) new Double(textField.getText());
                	commitEdit(val);
                    //commitEdit((S) textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }


        });
    }

	private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
