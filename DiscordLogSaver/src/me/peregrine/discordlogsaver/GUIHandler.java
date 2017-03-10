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

	public static GUIHandler frame;
	public GUIHandler(String title){
		setTitle(title);
	}
	JTextField dirfield = new JTextField(0);
	JButton dirbutton = new JButton("参照");
	JButton runbutton = new JButton("実行");

	private JTextField tokenfield = new JTextField(0);
	private String token;

	private String exportdir;
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


		tokenfield.setColumns(10);
		tokenfield.setSize(150, 20);
		tokenfield.setLocation(30, 40);


		dirlabel.setSize(200,30);
		dirlabel.setLocation(30, 70);

		dirfield.setSize(150, 20);
		dirfield.setLocation(30, 110);

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
		frame.add(tokenfield);
		frame.add(dirlabel);
		frame.add(dirfield);
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
						dirfield.setText(file.getPath());
					}
		}

		if (o == runbutton) {

			setToken(tokenfield.getText());
			System.out.println(getToken());
			IDiscordClient client = Main.createClient(getToken(), true);
			EventDispatcher dispatcher = client.getDispatcher();
			dispatcher.registerListener(new EventHandler());
			this.setExportdir(dirfield.getText());

		}



	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExportdir() {
		return exportdir;
	}

	public void setExportdir(String setdir) {
		this.exportdir = setdir;
	}


}
