package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import application.PIN.PinUtility;
import application.util.Utility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainScreenController implements Initializable {

	@FXML
	private TextField txtPan;

	@FXML
	private TextField txtPIN;

	@FXML
	private TextField txtPINBlock;

	@FXML
	private TextField txtPad;

	@FXML
	private ComboBox<String> cmbFrmt;

	@FXML
	private TextArea txtOutput;

	@FXML
	private Button btnClear;

	@FXML
	private Button btnGetPIN;

	@FXML
	private Button btnGetPINBlock;

	private ArrayList<String> formats = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		formats.add(PinUtility.PinBlockFormat_AnsiX98);
		formats.add(PinUtility.PinBlockFormat_Diebold);
		formats.add(PinUtility.PinBlockFormat_Docutel);
		formats.add(PinUtility.PinBlockFormat_IBM_3621);
		formats.add(PinUtility.PinBlockFormat_IBM_3624);
		formats.add(PinUtility.PinBlockFormat_IBM_4704);
		formats.add(PinUtility.PinBlockFormat_ISO_1);
		formats.add(PinUtility.PinBlockFormat_ISO_3);
		formats.add(PinUtility.PinBlockFormat_Plus);

		txtOutput.setText("");
		txtOutput.setStyle("-fx-font-family: monospace");
		txtPan.setText("");
		txtPIN.setText("");
		txtPINBlock.setText("");
		txtPad.setText("");

		cmbFrmt.getItems().add("Select PIN Block Format");
		cmbFrmt.getItems().addAll(formats);

		cmbFrmt.getSelectionModel().selectFirst();

		cmbFrmt.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && formats.contains(newValue)) {
					txtPan.setDisable(false);
					txtPIN.setDisable(false);
					txtPINBlock.setDisable(false);
					txtPad.setDisable(false);

					switch (newValue) {
					case PinUtility.PinBlockFormat_AnsiX98:
						txtPad.clear();
						txtPad.setDisable(true);
						txtPan.requestFocus();
						break;

					case PinUtility.PinBlockFormat_ISO_1:
						txtPan.clear();
						txtPan.setDisable(true);
						txtPad.clear();
						txtPad.setDisable(true);
						txtPIN.requestFocus();
						break;

					case PinUtility.PinBlockFormat_ISO_3:
						txtPad.clear();
						txtPad.setDisable(true);
						txtPan.requestFocus();
						break;

					case PinUtility.PinBlockFormat_Docutel:
						txtPan.clear();
						txtPan.setDisable(true);
						txtPIN.requestFocus();
						break;

					case PinUtility.PinBlockFormat_Diebold:
						txtPan.clear();
						txtPan.setDisable(true);
						txtPad.clear();
						txtPad.setDisable(true);
						txtPIN.requestFocus();
						break;

					case PinUtility.PinBlockFormat_Plus:
						txtPad.clear();
						txtPad.setDisable(true);
						txtPan.requestFocus();
						break;

					case PinUtility.PinBlockFormat_IBM_4704:
						txtPan.clear();
						txtPan.setDisable(true);
						txtPad.clear();
						txtPad.setDisable(true);
						txtPIN.requestFocus();
						break;

					case PinUtility.PinBlockFormat_IBM_3621:
						txtPan.clear();
						txtPan.setDisable(true);
						txtPIN.requestFocus();
						break;

					case PinUtility.PinBlockFormat_IBM_3624:
						txtPan.clear();
						txtPan.setDisable(true);
						txtPad.clear();
						txtPad.setDisable(true);
						txtPIN.requestFocus();
						break;

					default:

						break;
					}
				}
			}
		});

		btnClear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				txtPan.setText("");
				txtPad.setDisable(false);
				txtPIN.setText("");
				txtPIN.setDisable(false);
				txtPINBlock.setText("");
				txtPINBlock.setDisable(false);
				txtPad.setText("");
				txtPad.setDisable(false);
				cmbFrmt.getSelectionModel().selectFirst();
				txtOutput.setText("");
				txtPan.requestFocus();
			}
		});

		btnGetPINBlock.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				txtPINBlock.setText("");
				if (validData() && checkData(true)) {
					String PIN = "";
					String PINBlk = "";
					String PAN = "";
					String Padding = "";

					if (txtPIN.getText() != null)
						PIN = txtPIN.getText();
					if (txtPan.getText() != null)
						PAN = txtPan.getText();
					if (txtPad.getText() != null)
						Padding = txtPad.getText();

					PINBlk = PinUtility.ToPINBlock(cmbFrmt.getSelectionModel().getSelectedItem(), PIN, PAN, Padding);
					if (PINBlk != null) {
						txtPINBlock.setText(PINBlk);
						if (PAN.length() == 0) PAN = "N/A";
						if (PIN.length() == 0) PIN = "N/A";
						if (Padding.length() == 0 ) Padding = "N/A";
						txtOutput.setText("PIN blocks: PIN block encrypt operation finished"
								+ "\n **************************************** "
								+ "\nPAN:             " + PAN 
								+ "\nPIN:             " + PIN
								+ "\nPAD:             " + Padding
								+ "\nFormat:          " + cmbFrmt.getSelectionModel().getSelectedItem()
								+ "\n----------------------------------------"
								+ "\nClear PIN block: " + PINBlk ); 
					}
				}

			}
		});

		btnGetPIN.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				txtPIN.setText("");
				if (validData() && checkData(false)) {
					String PIN = "";
					String PINBlk = "";
					String PAN = "";
					String Padding = "";

					if (txtPINBlock.getText() != null)
						PINBlk = txtPINBlock.getText();
					if (txtPan.getText() != null)
						PAN = txtPan.getText();
					if (txtPad.getText() != null)
						Padding = txtPad.getText();

					PIN = PinUtility.ToPIN(cmbFrmt.getSelectionModel().getSelectedItem(), PINBlk, PAN, Padding);
					if (PIN != null) {
						txtPIN.setText(PIN);
						if (PAN.length() == 0) PAN = "N/A";
						if (PINBlk.length() == 0) PINBlk = "N/A";
						if (Padding.length() == 0 ) Padding = "N/A";
						txtOutput.setText("PIN blocks: PIN block decode operation finished"
								+ "\n **************************************** "
								+ "\nPAN:             " + PAN 
								+ "\nPIN Block:       " + PINBlk
								+ "\nPAD:             " + Padding
								+ "\nFormat:          " + cmbFrmt.getSelectionModel().getSelectedItem()
								+ "\n----------------------------------------"
								+ "\nDecoded PIN:     " + PIN );
					}
				}
			}
		});

		txtPan.requestFocus();
	}

	private boolean validData() {
		String output = "";

		if (!(txtOutput.getText() == null || txtOutput.getText().trim().length() == 0)) {
			output = txtOutput.getText();
		}

		if (!formats.contains(cmbFrmt.getSelectionModel().getSelectedItem())) {
			txtOutput.setText(output + "\n****** Error: Please select the PIN Block Format.");
			return false;
		}

		if (!(txtPan.getText() == null || txtPan.getText().trim().length() == 0)
				&& !(StringUtils.isNumeric(txtPan.getText()))) {
			txtOutput.setText(output + "\n****** Error: PAN should be numeric.");
			return false;
		}

		if (!(txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)
				&& !(StringUtils.isNumeric(txtPIN.getText()))) {
			txtOutput.setText(output + "\n****** Error: PIN should be numeric.");
			return false;
		}

		if (!(txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)
				&& !(Utility.IsHex(txtPINBlock.getText()))) {
			txtOutput.setText(output + "\n****** Error: PIN Block should be hex.");
			return false;
		}

		if (!(txtPad.getText() == null || txtPad.getText().trim().length() == 0)
				&& !(Utility.IsHex(txtPad.getText()))) {
			txtOutput.setText(output + "\n****** Error: Padding should be hex.");
			return false;
		}

		return true;
	}

	private boolean checkData(boolean pinToPinBlock) {
		String output = "";
		boolean result = true;

		if (!(txtOutput.getText() == null || txtOutput.getText().trim().length() == 0)) {
			output = txtOutput.getText();
		}

		switch (cmbFrmt.getSelectionModel().getSelectedItem()) {
		case PinUtility.PinBlockFormat_AnsiX98:
			txtPad.clear();
			if (txtPan.getText() == null || txtPan.getText().trim().length() == 0)
				result = false;
			if ((!pinToPinBlock) && (txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)) {
				result = false;
			} else if ((pinToPinBlock) && (txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)) {
				result = false;
			}

			if (!result) {
				txtOutput.setText(output + "\n****** Error: Required Field Missing."
						+ "\n              Format:         " + cmbFrmt.getSelectionModel().getSelectedItem()
						+ "\n              Following Field(s) are Mandatory:" + "\n              * PAN "
						+ "\n              * PIN/PIN Block");
				return false;
			}
			break;

		case PinUtility.PinBlockFormat_ISO_1:
			txtPan.clear();
			txtPad.clear();
			if ((!pinToPinBlock) && (txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)) {
				result = false;
			} else if ((pinToPinBlock) && (txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)) {
				result = false;
			}

			if (!result) {
				txtOutput.setText(output + "\n****** Error: Required Field Missing."
						+ "\n              Format:         " + cmbFrmt.getSelectionModel().getSelectedItem()
						+ "\n              Following Field(s) are Mandatory:" + "\n              * PIN/PIN Block");
				return false;
			}
			break;

		case PinUtility.PinBlockFormat_ISO_3:
			txtPad.clear();
			if (txtPan.getText() == null || txtPan.getText().trim().length() == 0)
				result = false;
			if ((!pinToPinBlock) && (txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)) {
				result = false;
			} else if ((pinToPinBlock) && (txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)) {
				result = false;
			}

			if (!result) {
				txtOutput.setText(output + "\n****** Error: Required Field Missing."
						+ "\n              Format:         " + cmbFrmt.getSelectionModel().getSelectedItem()
						+ "\n              Following Field(s) are Mandatory:" + "\n              * PAN "
						+ "\n              * PIN/PIN Block");
				return false;
			}
			break;

		case PinUtility.PinBlockFormat_Docutel:
			txtPan.clear();
			if (txtPad.getText() == null || txtPad.getText().trim().length() == 0)
				result = false;
			if ((!pinToPinBlock) && (txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)) {
				result = false;
			} else if ((pinToPinBlock) && (txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)) {
				result = false;
			}

			if (!result) {
				txtOutput.setText(output + "\n****** Error: Required Field Missing."
						+ "\n              Format:         " + cmbFrmt.getSelectionModel().getSelectedItem()
						+ "\n              Following Field(s) are Mandatory:" + "\n              * Padding "
						+ "\n              * PIN/PIN Block");
				return false;
			}
			break;

		case PinUtility.PinBlockFormat_Diebold:
			txtPan.clear();
			txtPad.clear();
			if ((!pinToPinBlock) && (txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)) {
				result = false;
			} else if ((pinToPinBlock) && (txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)) {
				result = false;
			}

			if (!result) {
				txtOutput.setText(output + "\n****** Error: Required Field Missing."
						+ "\n              Format:         " + cmbFrmt.getSelectionModel().getSelectedItem()
						+ "\n              Following Field(s) are Mandatory:" + "\n              * PIN/PIN Block");
				return false;
			}
			break;

		case PinUtility.PinBlockFormat_Plus:
			txtPad.clear();
			if (txtPan.getText() == null || txtPan.getText().trim().length() == 0)
				result = false;
			if ((!pinToPinBlock) && (txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)) {
				result = false;
			} else if ((pinToPinBlock) && (txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)) {
				result = false;
			}

			if (!result) {
				txtOutput.setText(output + "\n****** Error: Required Field Missing."
						+ "\n              Format:         " + cmbFrmt.getSelectionModel().getSelectedItem()
						+ "\n              Following Field(s) are Mandatory:" + "\n              * PAN "
						+ "\n              * PIN/PIN Block");
				return false;
			}
			break;

		case PinUtility.PinBlockFormat_IBM_4704:
			txtPan.clear();
			txtPad.clear();
			if ((!pinToPinBlock) && (txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)) {
				result = false;
			} else if ((pinToPinBlock) && (txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)) {
				result = false;
			}

			if (!result) {
				txtOutput.setText(output + "\n****** Error: Required Field Missing."
						+ "\n              Format:         " + cmbFrmt.getSelectionModel().getSelectedItem()
						+ "\n              Following Field(s) are Mandatory:" + "\n              * PIN/PIN Block");
				return false;
			}
			break;

		case PinUtility.PinBlockFormat_IBM_3621:
			txtPan.clear();
			if (txtPad.getText() == null || txtPad.getText().trim().length() == 0)
				result = false;
			if ((!pinToPinBlock) && (txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)) {
				result = false;
			} else if ((pinToPinBlock) && (txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)) {
				result = false;
			}

			if (!result) {
				txtOutput.setText(output + "\n****** Error: Required Field Missing."
						+ "\n              Format:         " + cmbFrmt.getSelectionModel().getSelectedItem()
						+ "\n              Following Field(s) are Mandatory:" + "\n              * Padding "
						+ "\n              * PIN/PIN Block");
				return false;
			}
			break;

		case PinUtility.PinBlockFormat_IBM_3624:
			txtPan.clear();
			txtPad.clear();
			if ((!pinToPinBlock) && (txtPINBlock.getText() == null || txtPINBlock.getText().trim().length() == 0)) {
				result = false;
			} else if ((pinToPinBlock) && (txtPIN.getText() == null || txtPIN.getText().trim().length() == 0)) {
				result = false;
			}

			if (!result) {
				txtOutput.setText(output + "\n****** Error: Required Field Missing."
						+ "\n              Format:         " + cmbFrmt.getSelectionModel().getSelectedItem()
						+ "\n              Following Field(s) are Mandatory:" + "\n              * PIN/PIN Block");
				return false;
			}
			break;

		default:

			break;
		}
		return true;
	}

}
