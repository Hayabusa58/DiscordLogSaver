package me.peregrine.discordlogsaver;

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

public class EvenHandler {

	public String formatedmessagewithfile;
	public String formatedmessage;

	List<String> formatedmessages = new ArrayList<String>();
	@EventSubscriber
	public void onReady(ReadyEvent event) throws MissingPermissionsException, RateLimitException, DiscordException{
		System.out.println("onready!!");
		IChannel general = event.getClient().getChannelByID("288973141072543745");
		general.sendMessage("onready");
	}

	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) throws MissingPermissionsException, RateLimitException, DiscordException{
		IMessage message = event.getMessage();
		IChannel chennel = message.getChannel();

		String br = System.getProperty("line.separator");
		//System.out.println(message);
		if (message.getContent().equals("/log")) {
			//chennel.sendMessage("logcommand");
			//System.out.println(chennel.getMessages().size());

			for (int i = 0;  i < chennel.getMessages().size(); i++ ) {
				//System.out.println("e");
				List<IMessage> messages = chennel.getMessages().reverse();

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
		}
		if (message.getContent().equals("/debug")) {
			System.out.println("debug command ran");
				System.out.println(formatedmessages);
				Main.exportFile(formatedmessages);
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
}
