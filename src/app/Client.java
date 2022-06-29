package app;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import entity.Room;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Client {

	private Socket socket;
	private ObjectOutputStream out;
	private DataInputStream in;
	private JFrame frame;
	private JTextField txtPathFile;
	private JLabel lblResult;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
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
	public Client() {
		new Thread(() -> {
			try {
				socket = new Socket("localhost", 9999);
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());

			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}).start();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Tắt máy và khởi động lại"));
		panel_2.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnShutDown = new JButton("Tắt máy");
		panel.add(btnShutDown);
		
		JButton btnRestart = new JButton("Khởi động lại");
		panel.add(btnRestart);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "X\u00F3a file", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtPathFile = new JTextField();
		panel_1.add(txtPathFile);
		txtPathFile.setColumns(30);
		
		JButton btnDelete = new JButton("Xóa file");
		panel_1.add(btnDelete);
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		
		lblResult = new JLabel("");
		panel_3.add(lblResult);
		
		btnShutDown.addActionListener(v -> {
			int answer = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn tắt máy ?");
			if(answer == 0) {
				try {
					System.out.println("sending...");
					out.writeUTF("shutdown");
					out.flush();
					
					handleResult();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnRestart.addActionListener(v -> {
			int answer = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn khởi động lại máy ?");
			if(answer == 0) {
				try {
					System.out.println("sending...");
					out.writeUTF("restart");
					out.flush();
					
					handleResult();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnDelete.addActionListener(v -> {
			int answer = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn xóa file ?");
			if(answer == 0) {
				try {
					String path = txtPathFile.getText();
					
					System.out.println("sending...");
					out.writeUTF("delete");
					out.writeUTF(path);
					out.flush();
					
					handleResult();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void handleResult() throws IOException {
		String rs = in.readUTF();
		System.out.println(rs);
		lblResult.setText(rs);
	}

}
