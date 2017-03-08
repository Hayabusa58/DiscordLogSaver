package me.peregrine.discordlogsaver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

public class GUIHandler extends JFrame implements ActionListener{

	public GUIHandler(){
		JFrame frame = new JFrame("DiscordLogsaver");
		createFrame(frame);
	}
	JTextField dirtext = new JTextField(0);
	JButton dirbutton = new JButton("参照");
	JButton runbutton = new JButton("実行");
	
	private JTextField tokentext = new JTextField(0);
	private String token;
	
	public void createFrame(JFrame frame) {
		
		frame.setLocationRelativeTo(null);
		frame.setBounds(500, 300, 200, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);


		JLabel tokenlabel = new JLabel("アクセストークン");

		JLabel dirlabel = new JLabel("出力ディレクトリ");

		tokenlabel.setSize(200, 30);
		tokenlabel.setLocation(30, 10);

		
		tokentext.setColumns(10);
		tokentext.setSize(150, 20);
		tokentext.setLocation(30, 40);
		
		
		dirlabel.setSize(200,30);
		dirlabel.setLocation(30, 70);
		
		dirtext.setSize(150, 20);
		dirtext.setLocation(30, 110);
		
		dirbutton.setSize(80, 25);
		dirbutton.setLocation(100, 140);
		dirbutton.addActionListener(this);
		
		runbutton.setSize(160, 50);
		runbutton.setLocation(20, 180);
		runbutton.addActionListener(this);
		/*
		tokenlabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tokentext.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		*/
		//frame.add(tokenlabel, BorderLayout.CENTER);
		frame.add(tokenlabel);
		frame.add(tokentext);
		frame.add(dirlabel);
		frame.add(dirtext);
		frame.add(dirbutton);
		frame.add(runbutton);
		//frame.add(tokentext, BorderLayout.CENTER);
		
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o == dirbutton){
			String dir = "C:\\";
			JFileChooser fileChooser = new JFileChooser(dir);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int selected = fileChooser.showSaveDialog(this);
					if (selected == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						dirtext.setText(file.getPath());
					}
		}
		
		if (o == runbutton) {
			
			setToken("");
			System.out.println(getToken());
			IDiscordClient client = Main.createClient(getToken(), true);
			EventDispatcher dispatcher = client.getDispatcher();
			dispatcher.registerListener(new EvenHandler());
			
			
		}
		
		
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
