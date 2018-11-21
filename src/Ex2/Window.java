package Ex2;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

//Meshi Sanker ID:205562747

public class Window extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.setStyle("-fx-background-color: BLACK");
		Scene scene = new Scene(layout, 1000, 550);

		BandViewer bandView = new BandViewer();
		layout.setCenter(bandView);

		
		Button next = new Button(">");
		next.setOnAction(e-> bandView.nextClicked());
		layout.setRight(next);
		BorderPane.setAlignment(next,Pos.CENTER);
		
		Button prev = new Button("<");
		prev.setOnAction(e->bandView.previousClicked());
		layout.setLeft(prev);
		BorderPane.setAlignment(prev,Pos.CENTER);

		Button save = new Button("Save");
		save.setOnAction(e-> bandView.saveClicked());
		
		Button remove = new Button("Remove Band");
		remove.setOnAction(e -> bandView.removeClicked());

		Button undo = new Button("Undo");
		undo.setOnAction(e-> bandView.undoClicked());
		
		Button revert = new Button("Revert");
		revert.setOnAction(e-> bandView.revertClicked());
		
		HBox userButtoms = new HBox(10, save, remove, undo, revert);
		layout.setBottom(userButtoms);
		userButtoms.setAlignment(Pos.CENTER);
		
		String[] titles = {"Sort By Name", "Sort By Fans", "Sort By Origin"};
		ObservableList<String> indexes = FXCollections.observableArrayList(titles);
		ComboBox<String> comboBox = new ComboBox<>(indexes);
		comboBox.setMinWidth(200);
		comboBox.setPromptText("Sort by....");
		comboBox.setOnAction(e-> bandView.shortClicked(indexes.indexOf(comboBox.getValue())));
		ClockPane clock = new ClockPane();
		
		GridPane gridPane = new GridPane();
		gridPane.addRow(0, clock);
		gridPane.addRow(1, comboBox);
		gridPane.setVgap(40);
		layout.setTop(gridPane);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setHalignment(comboBox, HPos.CENTER);
	
		PathTransition pathTransition = new PathTransition();

		pathTransition.setNode(clock);
		Line line = new Line();
		line.setStartY(10);
		line.setEndY(10);
		line.setStartX(-150);
		line.setEndX(500);

		pathTransition.setPath(line);
		pathTransition.setDuration(Duration.millis(10000));
		pathTransition.setCycleCount(Animation.INDEFINITE);
		pathTransition.setAutoReverse(true);
		pathTransition.play();

		clock.setOnMouseEntered(e -> pathTransition.pause());
		clock.setOnMouseExited(e -> pathTransition.play());

		layout.setOnKeyPressed(e ->  {
		      if (e.getCode() == KeyCode.RIGHT) {
		        bandView.nextClicked();       }
		      else if (e.getCode() == KeyCode.LEFT) {
		        bandView.previousClicked();            }
		      else if((e.getCode() == KeyCode.S) && e.isControlDown()){
		    	  bandView.saveClicked();
		      }
		       });
		
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	class ClockPane extends HBox {
		private final int DURATION = 100;
		private Text timeDisplay = new Text(getFormattedTime());

		public ClockPane() {
			setupTimeline();
			getChildren().add(timeDisplay);
			layoutSetup();
		}

		private void layoutSetup() {
			setAlignment(Pos.CENTER);
			timeDisplay.setFill(Color.RED);
			timeDisplay.setFont(Font.font(null, FontWeight.BOLD, 16));
		}

		private void setupTimeline() {
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(DURATION), event -> {
				timeDisplay.setText(getFormattedTime() + "	METAL HALL OF FAME");
			}));
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
		}

		private String getFormattedTime() {
			return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH : mm : ss"));
		}
	}
}
