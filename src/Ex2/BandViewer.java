package Ex2;

import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

//Meshi Sanker ID:205562747

public class BandViewer extends BorderPane {
	private Band band = new Band(0, null, 0, null, null, false, null);
	private BandsDataControllerImpl bandsControl;
	
	public BandViewer() {
		bandsControl = BandsDataControllerImpl.getInstance();
		band = bandsControl.next();
		band = bandsControl.previous();
		bandGridPane();
	}

	private void bandGridPane() {
		Label bandlbl = new Label("Band:");
		bandlbl.setTextFill(Color.RED);
		TextField bandTxt = new TextField(band.getName());
		bandTxt.setEditable(false);
		bandTxt.setFocusTraversable(isDisable());
		
		Label fanslbl = new Label("Fans:");
		fanslbl.setTextFill(Color.RED);
		TextField fansTxt = new TextField("" + band.getNumOfFans() + "");
		fansTxt.setEditable(false);
		fansTxt.setFocusTraversable(isDisable());

		Label formedlbl = new Label("Formed:");
		formedlbl.setTextFill(Color.RED);
		TextField formedTxt = new TextField("" + band.getFormedYear() + "");
		formedTxt.setEditable(false);
		formedTxt.setFocusTraversable(isDisable());

		Label originlbl = new Label("Origin:");
		originlbl.setTextFill(Color.RED);
		TextField originlTxt = new TextField(band.getOrigin());
		originlTxt.setEditable(false);
		originlTxt.setFocusTraversable(isDisable());

		Label splitlbl = new Label("Did they split:");
		splitlbl.setTextFill(Color.RED);
		CheckBox splitBox = new CheckBox();
		splitBox.setSelected(band.hasSplit());
		splitBox.setDisable(true);
		splitBox.setFocusTraversable(isDisable());

		Label stylelbl = new Label("Style:");
		stylelbl.setTextFill(Color.RED);
		TextField styleTxt = new TextField(band.getStyle());
		styleTxt.setEditable(false);
		styleTxt.setFocusTraversable(isDisable());

		GridPane pane = new GridPane();
		pane.addRow(0, bandlbl, bandTxt);
		pane.addRow(1, fanslbl, fansTxt);
		pane.addRow(2, formedlbl, formedTxt);
		pane.addRow(3, originlbl, originlTxt);
		pane.addRow(4, splitlbl, splitBox);
		pane.addRow(5, stylelbl, styleTxt);
		pane.setHgap(20);
		pane.setVgap(20);
		pane.setAlignment(Pos.CENTER);
		setCenter(pane);

	}

	public void nextClicked() {
		band = bandsControl.next();
		bandGridPane();
	}

	public void previousClicked() {
		band = bandsControl.previous();
		bandGridPane();
	}

	public void removeClicked() {
		bandsControl.remove();
		band = bandsControl.getBand();
		bandGridPane();

	}

	public void shortClicked(int value) {
		if (value == 0)
			bandsControl.sort(new BandsComparatorByName());

		else if (value == 1)
			bandsControl.sort(new BandsComparatorFans());

		else if (value == 2)
			bandsControl.sort(new BandsComparatorOrigin());

		band=bandsControl.getBand();
		bandGridPane();

	}
	
	public void undoClicked(){
		bandsControl.undo();
		band=bandsControl.getBand();
		bandGridPane();
	}
	
	public void revertClicked(){
		bandsControl.revert();
		band=bandsControl.getBand();
		bandGridPane();
	}
	
	public void saveClicked (){
		try {
			bandsControl.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
