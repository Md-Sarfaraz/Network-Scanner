package com.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.SystemColor;

public class Configuration extends JDialog {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Configuration dialog = new Configuration();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Configuration() {
		setBounds(100, 100, 455, 357);
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblSetThreadsFor = new JLabel("<  Set Threads For Scaning  >");
			lblSetThreadsFor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSetThreadsFor.setHorizontalAlignment(SwingConstants.CENTER);
			lblSetThreadsFor.setBounds(114, 75, 218, 32);
			panel.add(lblSetThreadsFor);
			
			JLabel lblSetTimeoutFor = new JLabel("<  Set Timeout of Request  >");
			lblSetTimeoutFor.setHorizontalAlignment(SwingConstants.CENTER);
			lblSetTimeoutFor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSetTimeoutFor.setBounds(114, 167, 218, 32);
			panel.add(lblSetTimeoutFor);
			
			textField = new JTextField();
			textField.setHorizontalAlignment(SwingConstants.CENTER);
			textField.setFont(new Font("Tahoma", Font.BOLD, 14));
			textField.setBounds(20, 79, 84, 32);
			panel.add(textField);
			textField.setColumns(10);
			
			textField_1 = new JTextField();
			textField_1.setHorizontalAlignment(SwingConstants.CENTER);
			textField_1.setFont(new Font("Tahoma", Font.BOLD, 14));
			textField_1.setColumns(10);
			textField_1.setBounds(342, 75, 84, 32);
			panel.add(textField_1);
			
			textField_2 = new JTextField();
			textField_2.setHorizontalAlignment(SwingConstants.CENTER);
			textField_2.setFont(new Font("Tahoma", Font.BOLD, 14));
			textField_2.setColumns(10);
			textField_2.setBounds(342, 167, 84, 32);
			panel.add(textField_2);
			
			textField_3 = new JTextField();
			textField_3.setHorizontalAlignment(SwingConstants.CENTER);
			textField_3.setFont(new Font("Tahoma", Font.BOLD, 14));
			textField_3.setColumns(10);
			textField_3.setBounds(20, 167, 84, 32);
			panel.add(textField_3);
			
			JLabel lblIpScanSettings = new JLabel("IP Scan Settings");
			lblIpScanSettings.setHorizontalAlignment(SwingConstants.CENTER);
			lblIpScanSettings.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
			lblIpScanSettings.setBounds(10, 11, 204, 57);
			panel.add(lblIpScanSettings);
			
			JLabel lblPortScanSettings = new JLabel("Port Scan Settings");
			lblPortScanSettings.setHorizontalAlignment(SwingConstants.CENTER);
			lblPortScanSettings.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
			lblPortScanSettings.setBounds(224, 11, 205, 57);
			panel.add(lblPortScanSettings);
			
			JButton btnSave = new JButton("Save");
			btnSave.setBackground(SystemColor.control);
			btnSave.setForeground(new Color(0, 0, 0));
			btnSave.setBounds(20, 275, 154, 32);
			panel.add(btnSave);
			
			JButton btnCancel = new JButton("Cancel");
			btnCancel.setBounds(280, 275, 146, 32);
			panel.add(btnCancel);
			
			JLabel lblThreadsMustBe = new JLabel("Threads Must Be Between 4 to 64");
			lblThreadsMustBe.setForeground(new Color(204, 51, 51));
			lblThreadsMustBe.setHorizontalAlignment(SwingConstants.CENTER);
			lblThreadsMustBe.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
			lblThreadsMustBe.setBounds(20, 118, 406, 32);
			panel.add(lblThreadsMustBe);
			
			JLabel lblTimeoutMustBe = new JLabel("Timeout Must Be Between 100 to 3000 miliseconds");
			lblTimeoutMustBe.setForeground(new Color(204, 51, 51));
			lblTimeoutMustBe.setHorizontalAlignment(SwingConstants.CENTER);
			lblTimeoutMustBe.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
			lblTimeoutMustBe.setBounds(20, 212, 406, 32);
			panel.add(lblTimeoutMustBe);
			
			JSeparator separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(224, 7, 2, 73);
			panel.add(separator);
		}
	}
}
