package com.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

public class MainView {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 774, 499);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel ipPane = new JPanel();
		tabbedPane.addTab("IP Scanner", null, ipPane, null);
		ipPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 358, 355);
		ipPane.add(scrollPane);
		
		JList list = new JList();
		list.setVisibleRowCount(-1);
		list.setValueIsAdjusting(true);
		list.setToolTipText("Double Click to Scan Ports");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		
		JLabel lblListOfIps = new JLabel("List of IPs of Connected Hosts");
		lblListOfIps.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblListOfIps);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setToolTipText("Scan Progress");
		progressBar.setStringPainted(true);
		progressBar.setBounds(6, 373, 746, 28);
		ipPane.add(progressBar);
		
		JLabel lblNewLabel = new JLabel("From IP :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel.setBounds(376, 39, 84, 28);
		ipPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(472, 39, 252, 28);
		ipPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblToIp = new JLabel("To IP :");
		lblToIp.setHorizontalAlignment(SwingConstants.TRAILING);
		lblToIp.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblToIp.setBounds(376, 79, 84, 28);
		ipPane.add(lblToIp);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(472, 79, 252, 28);
		ipPane.add(textField_1);
		
		JButton btnScan = new JButton("Scan");
		btnScan.setBounds(472, 159, 105, 37);
		ipPane.add(btnScan);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(619, 159, 105, 37);
		ipPane.add(btnStop);
		
		JLabel lblStaus = new JLabel("Staus :");
		lblStaus.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblStaus.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStaus.setBounds(376, 324, 149, 37);
		ipPane.add(lblStaus);
		
		JLabel lblWaiting = new JLabel("Waiting ...");
		lblWaiting.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		lblWaiting.setBounds(537, 324, 215, 37);
		ipPane.add(lblWaiting);
		
		JCheckBox chckbxScanForSpecific = new JCheckBox("Scan for Specific Port Number");
		
		chckbxScanForSpecific.setBounds(399, 119, 201, 28);
		ipPane.add(chckbxScanForSpecific);
		
		JLabel lblPingTimeout = new JLabel("Ping Timeout :");
		lblPingTimeout.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPingTimeout.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPingTimeout.setBounds(376, 208, 149, 37);
		ipPane.add(lblPingTimeout);
		
		JLabel lblNoOfThreads = new JLabel("No. of Threads :");
		lblNoOfThreads.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNoOfThreads.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNoOfThreads.setBounds(376, 255, 149, 37);
		ipPane.add(lblNoOfThreads);
		
		JLabel lblDefault_1 = new JLabel("Default ( 8 )");
		lblDefault_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblDefault_1.setBounds(537, 255, 187, 37);
		ipPane.add(lblDefault_1);
		
		JLabel lblDefault = new JLabel("Default (1000 ms)");
		lblDefault.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblDefault.setBounds(537, 208, 187, 37);
		ipPane.add(lblDefault);
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setToolTipText("i.e. - 21  \r\r\nor   21,22,80,  \r\r\nor   1-65535\r\r\n");
		textField_2.setBounds(612, 119, 112, 28);
		ipPane.add(textField_2);
		textField_2.setColumns(10);
		
		JPanel portPane = new JPanel();
		tabbedPane.addTab("Port Scanner", null, portPane, null);
		portPane.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 6, 358, 355);
		portPane.add(scrollPane_1);
		
		JList list_1 = new JList();
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_1.setViewportView(list_1);
		
		JLabel lblListOfOpen = new JLabel("List of Open Ports");
		lblListOfOpen.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblListOfOpen);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(472, 39, 252, 28);
		portPane.add(textField_3);
		
		JLabel lblTargetIp = new JLabel("Target IP :");
		lblTargetIp.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTargetIp.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblTargetIp.setBounds(376, 39, 84, 28);
		portPane.add(lblTargetIp);
		
		textField_4 = new JTextField();
		textField_4.setToolTipText("i.e. - 21  \r\r\nor   21,22,80,  \r\r\nor   1-65535\r\r\n");
		textField_4.setColumns(10);
		textField_4.setBounds(531, 79, 193, 28);
		portPane.add(textField_4);
		
		JButton button = new JButton("Stop");
		button.setBounds(619, 159, 105, 37);
		portPane.add(button);
		
		JButton button_1 = new JButton("Scan");
		button_1.setBounds(459, 159, 105, 37);
		portPane.add(button_1);
		
		JLabel label_1 = new JLabel("Waiting ...");
		label_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		label_1.setBounds(537, 324, 215, 37);
		portPane.add(label_1);
		
		JLabel label_2 = new JLabel("Staus :");
		label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		label_2.setFont(new Font("SansSerif", Font.BOLD, 18));
		label_2.setBounds(376, 324, 149, 37);
		portPane.add(label_2);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setToolTipText("Scan Progress");
		progressBar_1.setStringPainted(true);
		progressBar_1.setBounds(6, 373, 746, 28);
		portPane.add(progressBar_1);
		
		JLabel lblPortRange = new JLabel("Port Range / No.  :");
		lblPortRange.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPortRange.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPortRange.setBounds(376, 79, 143, 28);
		portPane.add(lblPortRange);
		
		JLabel lblDefault_2 = new JLabel("Default ( 16 )");
		lblDefault_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblDefault_2.setBounds(537, 275, 187, 37);
		portPane.add(lblDefault_2);
		
		JLabel label_3 = new JLabel("No. of Threads :");
		label_3.setHorizontalAlignment(SwingConstants.TRAILING);
		label_3.setFont(new Font("SansSerif", Font.BOLD, 16));
		label_3.setBounds(376, 275, 149, 37);
		portPane.add(label_3);
		
		JLabel label_4 = new JLabel("Ping Timeout :");
		label_4.setHorizontalAlignment(SwingConstants.TRAILING);
		label_4.setFont(new Font("SansSerif", Font.BOLD, 16));
		label_4.setBounds(376, 228, 149, 37);
		portPane.add(label_4);
		
		JLabel label_5 = new JLabel("Default (1000 ms)");
		label_5.setFont(new Font("SansSerif", Font.BOLD, 16));
		label_5.setBounds(537, 228, 187, 37);
		portPane.add(label_5);
		
		JLabel lblNewLabel_1 = new JLabel("Recommended Port Range is Below 1000 At a time");
		lblNewLabel_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setBounds(376, 119, 348, 28);
		portPane.add(lblNewLabel_1);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnFile.add(mntmSettings);
		//frame.add(new IPView());
	}
}
