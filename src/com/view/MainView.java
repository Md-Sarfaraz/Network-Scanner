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
import com.controller.PortScanner;
import com.controller.ScanController;
import com.pref.Persist;
import com.pref.Preference;
import com.threads.IPListener;
import com.threads.PortListener;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainView {

	private JFrame frmNetworkScanner;
	private JTextField fromIP;
	private JTextField toIP;
	private JTextField specPort;
	private JTextField targetIP;
	private JTextField portrange;
	private DefaultListModel<String> ipModel, portModel;
	private JLabel lblPingIP, lblThreadsIP;
	private JLabel lblStatusIP;
	private ScanController controller;
	private JProgressBar portProg;
	private JLabel lblStatusPort;
	private JButton btnScanPort;
	private Preference pref = null;

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

		controller = new ScanController();
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
		frmNetworkScanner.setResizable(false);
		frmNetworkScanner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNetworkScanner.getContentPane().setLayout(new BorderLayout(0, 0));
		try {
			pref = Persist.loadPreferences();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmNetworkScanner.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel ipPane = new JPanel();
		tabbedPane.addTab("IP Scanner", null, ipPane, null);
		ipPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 358, 355);
		ipPane.add(scrollPane);

		JList<String> ipList = new JList<String>();
		ipList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("mousePressed");
					targetIP.setText(ipList.getSelectedValue());
					tabbedPane.setSelectedIndex(1);
				}
			}
		});
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

				ipModel.clear();
				ipProg.setValue(0);
				try {
					controller.setIPRange(fromIP.getText(), toIP.getText());
					ipProg.setMaximum(controller.getTotalhost());
					lblStatusIP.setText("Scanning ...");
					controller.scanIP(new IPListener() {

						@Override
						public void onSleep(String host, int progress) {
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
							btnScan.setEnabled(done);
							if (done) {
								ipProg.setValue(controller.getTotalhost());
								lblStatusIP.setText("Scan Complete");
							}
						}
					});
				} catch (ScannerException e1) {
					btnScan.setEnabled(true);
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
					lblStatusIP.setText("Scan Stopped");
				}
			}
		});

		JLabel lblStaus = new JLabel("Staus :");
		lblStaus.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblStaus.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStaus.setBounds(376, 324, 149, 37);
		ipPane.add(lblStaus);

		lblStatusIP = new JLabel("Waiting ...");
		lblStatusIP.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		lblStatusIP.setBounds(537, 324, 215, 37);
		ipPane.add(lblStatusIP);

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

		lblThreadsIP = new JLabel();
		lblThreadsIP.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblThreadsIP.setBounds(537, 255, 187, 37);
		ipPane.add(lblThreadsIP);
		lblThreadsIP.setText(pref.getIP_THREADS()+"");

		lblPingIP = new JLabel();
		lblPingIP.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPingIP.setBounds(537, 208, 187, 37);
		ipPane.add(lblPingIP);
		lblPingIP.setText(pref.getIP_TIMEOUT()+"");

		specPort = new JTextField();
		specPort.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtil.validInputPort(e);
			}
		});
		specPort.setEnabled(false);
		specPort.setHorizontalAlignment(SwingConstants.CENTER);
		specPort.setText("78-595");
		specPort.setFont(new Font("SansSerif", Font.BOLD, 16));
		specPort.setToolTipText("ex - 21  or   21,22,80,  or   1-65535");
		specPort.setBounds(592, 119, 132, 28);
		ipPane.add(specPort);
		specPort.setColumns(10);

		JPanel portPane = new JPanel();
		tabbedPane.addTab("Port Scanner", null, portPane, null);
		portPane.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 6, 358, 355);
		portPane.add(scrollPane_1);

		JList portList = new JList();
		scrollPane_1.setViewportView(portList);
		portList.setModel(portModel);

		JLabel lblListOfOpen = new JLabel("List of Open Ports");
		lblListOfOpen.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblListOfOpen);

		targetIP = new JTextField();
		targetIP.setFont(new Font("SansSerif", Font.BOLD, 18));
		targetIP.setColumns(10);
		targetIP.setBounds(472, 39, 131, 28);
		portPane.add(targetIP);

		JLabel lblTargetIp = new JLabel("Target IP :");
		lblTargetIp.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTargetIp.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblTargetIp.setBounds(376, 39, 84, 28);
		portPane.add(lblTargetIp);

		portrange = new JTextField();
		portrange.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewUtil.validInputPort(e);
			}
		});
		portrange.setHorizontalAlignment(SwingConstants.CENTER);
		portrange.setFont(new Font("SansSerif", Font.BOLD, 18));
		portrange.setToolTipText("i.e. - 21  \r\r\nor   21,22,80,  \r\r\nor   1-65535\r\r\n");
		portrange.setColumns(10);
		portrange.setBounds(615, 39, 112, 28);
		portPane.add(portrange);

		JButton btnStopPort = new JButton("Stop");
		btnStopPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PortScanner.stop();
				btnScanPort.setEnabled(true);
				lblStatusPort.setText("Scan Stopped");

			}
		});
		btnStopPort.setBounds(619, 159, 105, 37);
		portPane.add(btnStopPort);

		btnScanPort = new JButton("Scan");
		btnScanPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnScanPort.setEnabled(false);
				portModel.clear();
				portProg.setValue(0);
				try {
					controller.setPortRange(targetIP.getText(), portrange.getText());
					portProg.setMaximum(controller.getTotalPorts());
					portProg.setStringPainted(true);
					
					portProg.setBackground(Color.GREEN);
					lblStatusPort.setText("Scanning ..");
					controller.scanPort(new PortListener() {

						@Override
						public void onOpen(int port, int progress) {
							portProg.setValue(progress + 1);
							portModel.addElement(String.valueOf(port));
							try {
								Thread.sleep(125L);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onClose(int port, int progress) {
							portProg.setValue(progress + 1);
						}

						@Override
						public void isComplete(boolean done) {
							btnScanPort.setEnabled(done);
							if (done) {
								portProg.setValue(controller.getTotalPorts());
								lblStatusPort.setText("Scan Complete");
								portProg.setForeground(Color.black);
								portProg.setBackground(Color.GRAY);
							}
						}
					});
				} catch (ScannerException e1) {
					btnScanPort.setEnabled(true);
					JOptionPane.showMessageDialog(btnScan, e1.getMessage());
				}
			}
		});
		btnScanPort.setBounds(459, 159, 105, 37);
		portPane.add(btnScanPort);

		lblStatusPort = new JLabel("Waiting ...");
		lblStatusPort.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		lblStatusPort.setBounds(537, 324, 215, 37);
		portPane.add(lblStatusPort);

		JLabel label_2 = new JLabel("Staus :");
		label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		label_2.setFont(new Font("SansSerif", Font.BOLD, 18));
		label_2.setBounds(376, 324, 149, 37);
		portPane.add(label_2);

		portProg = new JProgressBar();
		portProg.setToolTipText("Scan Progress");
		portProg.setStringPainted(true);
		portProg.setBounds(6, 373, 746, 28);
		portPane.add(portProg);

		JLabel lblThreadsPort = new JLabel();
		lblThreadsPort.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblThreadsPort.setBounds(537, 275, 187, 37);
		lblThreadsPort.setText(pref.getPORT_THREADS()+"");
		portPane.add(lblThreadsPort);

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

		JLabel lblPingPort = new JLabel();
		lblPingPort.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPingPort.setBounds(537, 228, 187, 37);
		lblPingPort.setText(pref.getPORT_TIMEOUT()+"");
		portPane.add(lblPingPort);

		JLabel lblNewLabel_1 = new JLabel("Recommended Port Range is Below 1000 At a time");
		lblNewLabel_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setBounds(376, 79, 348, 28);
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
