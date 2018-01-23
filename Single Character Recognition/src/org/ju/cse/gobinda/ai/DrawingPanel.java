package org.ju.cse.gobinda.ai;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static int ROW = 16;
	private static int COL = 16;
	private static int ROW_LENGTH = 304;
	private static int COL_LENGTH = 304;

	private int[] values = new int[ROW * COL];

	public DrawingPanel() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int perBoxWidth = ROW_LENGTH / ROW;
		int perBoxHeight = COL_LENGTH / COL;

		for (int i = 0, pos = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++, pos++) {
				g.setColor(Color.BLACK);
				g.drawLine(0, i * perBoxWidth, ROW_LENGTH, i * perBoxWidth);
				g.drawLine(j * perBoxHeight, 0, j * perBoxHeight, COL_LENGTH);
				g.setColor(getColorAccordingToValues(values[pos]));
				g.fillRect(j * perBoxHeight, i * perBoxWidth, perBoxWidth, perBoxHeight);
			}
		}

	}

	private Color getColorAccordingToValues(int i) {
		if (i == 0)
			return Color.WHITE;
		return Color.BLACK;
	}

	public int[] getValues() {
		return values;
	}

	public String getValuesAsString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			sb.append(String.valueOf(values[i]));
		}
		return sb.toString();
	}

	public void setValues(int[] values) {
		this.values = values;
		repaint();
	}

	public static int getROW() {
		return ROW;
	}

	public static int getCOL() {
		return COL;
	}

	public static int getROW_LENGTH() {
		return ROW_LENGTH;
	}

	public static int getCOL_LENGTH() {
		return COL_LENGTH;
	}

}
