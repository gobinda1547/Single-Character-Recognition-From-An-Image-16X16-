package org.ju.cse.gobinda.ai;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.ju.cse.gobinda.ai.dao.Dao;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField answerTextField;
	private DrawingPanel drawingPanel;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Character Recognition");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 329, 468);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		drawingPanel = new DrawingPanel();
		drawingPanel.setBorder(new LineBorder(Color.BLACK));
		drawingPanel.setBounds(10, 66, 304, 304);
		contentPane.add(drawingPanel);

		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image Only", "png");
				fileChooser.addChoosableFileFilter(imageFilter);
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFileFilter(imageFilter);
				fileChooser.setAcceptAllFileFilterUsed(false);

				int result = fileChooser.showOpenDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());

					try {
						File f = new File(selectedFile.getAbsolutePath());
						BufferedImage img = ImageIO.read(f);

						int width = img.getWidth();
						int height = img.getHeight();
						if (width < DrawingPanel.getCOL() || height < DrawingPanel.getROW()) {
							answerTextField.setText(" 16X16 -> " + String.format("%dX%d", width, height));
							return;
						}

						int[] v = new int[DrawingPanel.getROW() * DrawingPanel.getCOL()];
						for (int i = 0, pos = 0; i < DrawingPanel.getROW(); i++) {
							for (int j = 0; j < DrawingPanel.getCOL(); j++) {
								v[pos++] = (img.getRGB(i, j) == -1) ? 0 : 1;
							}
						}
						drawingPanel.setValues(v);

						String name = Dao.selectNameWhereDataIs(drawingPanel.getValuesAsString());
						answerTextField.setText(name);

					} catch (IOException e) {
						System.out.println(e);
					}
				}

			}
		});
		btnShow.setBounds(10, 384, 95, 44);
		contentPane.add(btnShow);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int[] v = drawingPanel.getValues();
				StringBuilder sb = new StringBuilder();
				boolean allZeroCheck = true;

				for (int i = 0; i < v.length; i++) {
					sb.append(String.valueOf(v[i]));
					allZeroCheck = (v[i] == 1) ? false : allZeroCheck;
				}
				String name = answerTextField.getText();
				if (name.length() == 0) {
					JOptionPane.showMessageDialog(null, "Enter a name pls!");
					return;
				}
				if (name.equals("Not Found!")) {
					JOptionPane.showMessageDialog(null, "Select another name pls!");
					return;
				}

				if (allZeroCheck) {
					JOptionPane.showMessageDialog(null, "Draw somthing then add!");
					return;
				}
				Dao.insertWithPreparedStatement(name, sb.toString());
				JOptionPane.showMessageDialog(null, "successfully trained!");
			}
		});
		btnAdd.setBounds(115, 384, 94, 44);
		contentPane.add(btnAdd);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] v = new int[16 * 16];
				Arrays.fill(v, 0);
				drawingPanel.setValues(v);
			}
		});
		btnClear.setBounds(219, 384, 95, 44);
		contentPane.add(btnClear);

		answerTextField = new JTextField();
		answerTextField.setBounds(10, 11, 246, 44);
		contentPane.add(answerTextField);
		answerTextField.setColumns(10);

		btnNewButton = new JButton("X");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				answerTextField.setText("");
			}
		});
		btnNewButton.setBounds(266, 11, 47, 44);
		contentPane.add(btnNewButton);
	}
}
