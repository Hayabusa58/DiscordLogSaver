package me.peregrine.discordlogsaver;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Main {

	public static GUIHandler guiHandler;

	public static boolean iscompleted;
	public static boolean isalreadyexist = false;

	public static void main(String[] args) {
		guiHandler = new GUIHandler("DiscordLogSaver");
		guiHandler.createFrame(guiHandler);
		guiHandler.setVisible(true);
		System.out.println("runed");

	}


	public static IDiscordClient createClient(String token, boolean login){
		ClientBuilder clientbuilder = new ClientBuilder();
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




}
