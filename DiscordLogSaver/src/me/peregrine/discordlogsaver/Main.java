package me.peregrine.discordlogsaver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Main {

	public static void main(String[] args) {
		GUIHandler guiHandler = new GUIHandler();
		System.out.println("runed");

	}


	public static IDiscordClient createClient(String token, boolean login){
		ClientBuilder clientbuilder= new ClientBuilder();
		clientbuilder.withToken(token);
		try {
			if (login) {
				System.out.println("loged in");
				return clientbuilder.login();
			} else {
				System.out.println("builed");
				return clientbuilder.build();
			}
		} catch (DiscordException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void exportFile(List formatedmessages) {
		List<String> lines = new ArrayList<>();
		String br = System.getProperty("line.separator");
		try{
			File exportfile = new File("C:/Users/Hayabusa/Desktop/ex.txt");
			if(exportfile.exists() && exportfile.canWrite()){
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exportfile)));
				for(int i = 0; i < formatedmessages.size(); i++){
					lines.add(formatedmessages.get(i).toString());
					pw.println(br + lines.get(i) + br);
				}

				pw.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}


}
