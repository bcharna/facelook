package edu.cmu.cs214.hw9.gui;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.cmu.cs214.hw9.controllers.Client;
import edu.cmu.cs214.hw9.controllers.OverallController;

@SuppressWarnings("serial")
public class FacelookInitGUI extends JFrame {
	private OverallController o;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FacelookInitGUI frame = new FacelookInitGUI();
//					frame.setVisible(true);
//					frame.getController().init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FacelookInitGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.getContentPane().add(new LoginPanel(this));
		this.setVisible(true);
		o = new OverallController(this);
		o.init();
		
	}
	/*
	 * Use this method to swap between panels! Example in LoginPanel.java
	 */
	public void replace(JPanel j){
		this.getContentPane().removeAll();
		this.getContentPane().add(j);
		validate();
	}
	
	public OverallController getController(){
		return o;
	}
	
	public void windowClosed(WindowEvent e){
		System.out.println("hi");
	}
}
