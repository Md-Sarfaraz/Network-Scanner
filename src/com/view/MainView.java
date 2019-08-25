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
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;

import com.controller.IPScanner;
import com.controller.ScanController;
import com.pref.Preference;
import com.threads.IPListener;
import com.threads.ScannerException;
import com.util.ViewUtil;

import javax.swing.event.ChangeEvent;
import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainView {

	private JFrame frmNetworkScanner;
	private JTextField fromIP;
	private JTextField toIP;
	private JTextField specPort;
	private JTextField textField_3;
	private JTextField textField_4;
	private DefaultListModel<String> ipModel, portModel;
	private JLabel lblPing, lblThreads;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if (info.getName().equalsIgnoreCase("nimbus")) {
							UIManager.setLookAndFeel(info.getClassName());
						}
					}
					MainView window = new MainView();
					window.frmNetworkScanner.setVisible(true);
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
		ipModel = new DefaultListModel<String>();
		portModel = new DefaultListModel<String>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNetworkScanner = new JFrame();
		frmNetworkScanner.setTitle("Network Scanner");
		frmNetworkScanner.setSize(774, 499);
		frmNetworkScanner.setLocationRelativeTo(null);
		frmNetworkScanner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNetworkScanner.getContentPane().setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmNetworkScanner.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel ipPane = new JPanel();
		tabbedPane.addTab("IP Scanner", null, ipPane, null);
		ipPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 358, 355);
		ipPane.add(scrollPane);

		JList ipList = new JList();
		ipList.setVisibleRowCount(-1);
		ipList.setValueIsAdjusting(true);
		ipList.setToolTipText("Double Click to Scan Ports");
		ipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(ipList);
		ipList.setModel(ipModel);

		JLabel lblListOfIps = new JLabel("List of IPs of Connected Hosts");
		lblListOfIps.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblListOfIps);

		JProgressBar ipProg = new JProgressBar();
		ipProg.setToolTipText("Scan Progress");
		ipProg.setStringPainted(true);
		ipProg.setBounds(6, 373, 746, 28);
		ipPane.add(ipProg);

		JLabel lblNewLabel = new JLabel("From IP :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel.setBounds(376, 20, 84, 28);
		ipPane.add(lblNewLabel);

		fromIP = new JTextField();
		fromIP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtil.validInputIP(e);
			}
		});

		fromIP.setHorizontalAlignment(SwingConstants.CENTER);
		fromIP.setText("192.168.0.1");
		fromIP.setFont(new Font("SansSerif", Font.BOLD, 18));
		fromIP.setBounds(472, 18, 252, 37);
		ipPane.add(fromIP);
		fromIP.setColumns(10);

		JLabel lblToIp = new JLabel("To IP :");
		lblToIp.setHorizontalAlignment(SwingConstants.TRAILING);
		lblToIp.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblToIp.setBounds(376, 69, 84, 28);
		ipPane.add(lblToIp);

		toIP = new JTextField();
		toIP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtil.validInputIP(e);
			}
		});
		toIP.setHorizontalAlignment(SwingConstants.CENTER);
		toIP.setText("192.168.0.40");
		toIP.setFont(new Font("SansSerif", Font.BOLD, 18));
		toIP.setColumns(10);
		toIP.setBounds(472, 67, 252, 37);
		ipPane.add(toIP);

		JButton btnScan = new JButton("Scan");
		btnScan.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnScan.setEnabled(false);
				ScanController controller = new ScanController();
				ipModel.clear();
				ipProg.setValue(0);
				try {
					controller.setIPRange(fromIP.getText(), toIP.getText());
					ipProg.setMaximum(controller.getTotalhost());
					lblStatus.setText("Scanning ...");
					controller.scanIP(new IPListener() {

						@Override
						public void onSleep(String host, int progress) {
							// TODO Auto-generated method stub
							ipProg.setValue(progress);
						}

						@Override
						public void onAlive(String host, int progress) {
							// TODO Auto-generated method stub
							ipProg.setValue(progress + 1);
							ipModel.addElement(host);
							try {
								Thread.sleep(125L);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void isComplete(boolean done) {
							// TODO Auto-generated method stub
							btnScan.setEnabled(done);
							if (done) {
								ipProg.setValue(controller.getTotalhost());
								lblStatus.setText("Scan Complete");
							}
						}
					});
				} catch (ScannerException e1) {
					btnScan.setEnabled(true);
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(btnScan, e1.getMessage());
				}
			}
		});
		btnScan.setBounds(472, 159, 105, 37);
		ipPane.add(btnScan);

		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(619, 159, 105, 37);
		ipPane.add(btnStop);
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!btnScan.isEnabled()) {
					// TODO Auto-generated method stub
					IPScanner.stop();
					btnScan.setEnabled(true);
					lblStatus.setText("Scan Stopped");
				}
			}
		});

		JLabel lblStaus = new JLabel("Staus :");
		lblStaus.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblStaus.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStaus.setBounds(376, 324, 149, 37);
		ipPane.add(lblStaus);

		lblStatus = new JLabel("Waiting ...");
		lblStatus.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		lblStatus.setBounds(537, 324, 215, 37);
		ipPane.add(lblStatus);

		JCheckBox chckbxScanForSpecific = new JCheckBox("Scan for Specific Port Number");
		chckbxScanForSpecific.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				specPort.setEnabled(e.getStateChange() == 1);
			}
		});

		chckbxScanForSpecific.setBounds(386, 119, 194, 28);
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

		lblThreads = new JLabel();
		lblThreads.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblThreads.setBounds(537, 255, 187, 37);
		ipPane.add(lblThreads);
		lblThreads.setText("Default ( " + Preference.IP_THREADS + " )");

		lblPing = new JLabel();
		lblPing.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPing.setBounds(537, 208, 187, 37);
		ipPane.add(lblPing);
		lblPing.setText("Default (" + Preference.IP_TIMEOUT + ")");

		specPort = new JTextField();
		specPort.setEnabled(false);
		specPort.setHorizontalAlignment(SwingConstants.CENTER);
		specPort.setText("78-595");
		specPort.setFont(new Font("SansSerif", Font.BOLD, 16));
		specPort.setToolTipText("i.e. - 21  \r\r\nor   21,22,80,  \r\r\nor   1-65535\r\r\n");
		specPort.setBounds(592, 119, 132, 28);
		ipPane.add(specPort);
		specPort.setColumns(10);

		JPanel portPane = new JPanel();
		tabbedPane.addTab("Port Scanner", null, portPane, null);
		portPane.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 6, 358, 355);
		portPane.add(scrollPane_1);

		JList list_1 = new JList();
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
		frmNetworkScanner.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Settings dialog = new Settings();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception es) {
					es.printStackTrace();
				}
			}
		});
		mnFile.add(mntmSettings);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		// frame.add(new IPView());
	}
}
