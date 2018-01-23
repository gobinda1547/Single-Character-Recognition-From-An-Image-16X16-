package org.ju.cse.gobinda.ai;

import org.ju.cse.gobinda.ai.dao.Dao;

public class Main {

	public static void main(String[] args) {
		//Dao.deleteDatabase();
		Dao.createTable();
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

}
