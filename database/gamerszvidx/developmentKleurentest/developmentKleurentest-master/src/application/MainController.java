package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController implements Initializable {
	Stage parent;
	boolean question1 = false;
	boolean question2 = false;
	boolean question3 = false;
	boolean question4 = false;
	@FXML
	private Button uitslag;
	@FXML
	private RadioButton a1;
	@FXML
	private ImageView pic1;
	@FXML
	private ImageView pic2;
	@FXML
	private RadioButton a2;
	@FXML
	private ImageView pic3;
	@FXML
	private ImageView pic4;
	@FXML
	private RadioButton a3;
	@FXML
	private RadioButton a4;
	@FXML
	private Label antwoord1;
	@FXML
	private Label antwoord2;
	@FXML
	private Label antwoord3;
	@FXML
	private Label antwoord4;
}
