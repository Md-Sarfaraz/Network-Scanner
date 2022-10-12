package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Settings extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField ipThreadtxt;
	private JTextField portThreadtxt;
	private JTextField portTimetxt;
	private JTextField ipTimetxt;

	/**
	 * Create the dialog.
	 */
	public Settings() {
		setTitle("Settings For IP & PORT");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(455, 357);
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			JLabel lblSetThreadsFor = new JLabel("<  Set Threads For Scanning  >");
			lblSetThreadsFor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSetThreadsFor.setHorizontalAlignment(SwingConstants.CENTER);
			lblSetThreadsFor.setBounds(114, 75, 218, 32);
			panel.add(lblSetThreadsFor);

			JLabel lblSetTimeoutFor = new JLabel("<  Set Timeout of Request  >");
			lblSetTimeoutFor.setHorizontalAlignment(SwingConstants.CENTER);
			lblSetTimeoutFor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSetTimeoutFor.setBounds(114, 167, 218, 32);
			panel.add(lblSetTimeoutFor);

			ipThreadtxt = new JTextField();
			ipThreadtxt.setHorizontalAlignment(SwingConstants.CENTER);
			ipThreadtxt.setFont(new Font("Tahoma", Font.BOLD, 14));
			ipThreadtxt.setBounds(20, 79, 84, 32);
			panel.add(ipThreadtxt);
			ipThreadtxt.setColumns(10);

			portThreadtxt = new JTextField();
			portThreadtxt.setHorizontalAlignment(SwingConstants.CENTER);
			portThreadtxt.setFont(new Font("Tahoma", Font.BOLD, 14));
			portThreadtxt.setColumns(10);
			portThreadtxt.setBounds(342, 75, 84, 32);
			panel.add(portThreadtxt);

			portTimetxt = new JTextField();
			portTimetxt.setHorizontalAlignment(SwingConstants.CENTER);
			portTimetxt.setFont(new Font("Tahoma", Font.BOLD, 14));
			portTimetxt.setColumns(10);
			portTimetxt.setBounds(342, 167, 84, 32);
			panel.add(portTimetxt);

			ipTimetxt = new JTextField();
			ipTimetxt.setHorizontalAlignment(SwingConstants.CENTER);
			ipTimetxt.setFont(new Font("Tahoma", Font.BOLD, 14));
			ipTimetxt.setColumns(10);
			ipTimetxt.setBounds(20, 167, 84, 32);
			panel.add(ipTimetxt);

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
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO

				}
			});
			btnSave.setBackground(new Color(0, 128, 0));
			btnSave.setForeground(new Color(255, 255, 255));
			btnSave.setBounds(20, 275, 154, 32);
			panel.add(btnSave);

			JButton btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancel.setForeground(new Color(255, 255, 255));
			btnCancel.setBackground(new Color(139, 0, 0));
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
