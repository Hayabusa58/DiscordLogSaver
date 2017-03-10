package me.peregrine.discordlogsaver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class EventHandler {

	public String formatedmessagewithfile;
	public String formatedmessage;

	private static IMessage message;
	private static IChannel channel;

	private static boolean isalreadyexit = false;

	List<String> formatedmessages = new ArrayList<String>();
	@EventSubscriber
	public void onReady(ReadyEvent event) throws MissingPermissionsException, RateLimitException, DiscordException{
		System.out.println("onready!!");
		IChannel general = event.getClient().getChannelByID("288973141072543745");
		general.sendMessage("onready");
	}

	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) throws MissingPermissionsException, RateLimitException, DiscordException{
		message = event.getMessage();
		channel = message.getChannel();
		String br = System.getProperty("line.separator");
		//System.out.println(message);
		if (message.getContent().equals("/log")) {
			//chennel.sendMessage("logcommand");
			//System.out.println(chennel.getMessages().size());
			for (int i = 0;  i < channel.getMessages().size(); i++ ) {
				//System.out.println("e");
				List<IMessage> messages = channel.getMessages().reverse();
				if(messages.get(i).getAttachments().isEmpty() == false ){
					formatedmessagewithfile =  messages.get(i).getAuthor().getName() + ":" + fomatTimestamp(messages.get(i).getTimestamp()) + br
											   + "file: " + messages.get(i).getAttachments().get(0).getUrl() + br + messages.get(i).getContent();
					System.out.println(formatedmessagewithfile);
					formatedmessages.add(formatedmessagewithfile);
				} else {
					formatedmessage =  messages.get(i).getAuthor().getName() + ":" + fomatTimestamp(messages.get(i).getTimestamp()) + br +  messages.get(i).getFormattedContent();
					System.out.println(formatedmessage);
					formatedmessages.add(formatedmessage);
				}
			}
			channel.sendMessage("Temporary log is saved. If you want to save localdir, please use \"/save\". :thinking:");
		}
		if (message.getContent().equals("/save")) {
			System.out.println("save command ran");
				if (formatedmessages.isEmpty() == false) {
					exportFile(formatedmessages, Main.guiHandler.getExportdir(), channel.getName());
					if (Main.iscompleted = true) {
						formatedmessages.removeAll(formatedmessages);
						channel.sendMessage("Saving logs completed! :heart_eyes:");
					}
				}else {
					channel.sendMessage("Before running this command, please use \"/log\". :triumph:");
				}
				//System.out.println(formatedmessages);

		}
		if (message.getContent().equals("/rewrite")) {
			if (isalreadyexit) {
				//上書きおっけー
			}
		}else if (message.getContent().equals("/newfile")) {
			if (isalreadyexit) {
				//上書きしないで新しいの作って
			}
		}
	}

	/*For fomat timestamp*/
	public static String fomatTimestamp(LocalDateTime timestamp) {
		if(timestamp != null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			String formatta = timestamp.format(formatter);
			return formatta;
		}
		return null;
	}

	public static void exportFile(List formatedmessages, String exportdir, String channelname) throws MissingPermissionsException, RateLimitException, DiscordException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = LocalDateTime.now().format(formatter);
		String filaname = exportdir + "\\" + channelname + "_" + date + ".txt";
		System.out.println(filaname);
		System.out.println(exportdir);
		List<String> lines = new ArrayList<>();
		String br = System.getProperty("line.separator");
		try{
			File exportfile = new File(filaname);
			if (exportfile.exists()) {
				//既に同名ファイルが存在した場合
				isalreadyexit = true;
				channel.sendMessage("Log file is already exist.If you want to rewrite, use \"rewrite\", if not, use \"newfile\". :kissing:");
			}else {
				exportfile.createNewFile();
				if(exportfile.exists() && exportfile.canWrite()){
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exportfile)));
					for(int i = 0; i < formatedmessages.size(); i++){
						lines.add(formatedmessages.get(i).toString());
						pw.println(br + lines.get(i) + br);
					}
					pw.close();
					Main.iscompleted = true;
				}
			}
		}catch(IOException e){
			channel.sendMessage("Somthing error happedn.I couldn't save logs... :disappointed_relieved:");
			e.printStackTrace();
			Main.iscompleted = false;
		}
	}
}
