package gmailcheck.app;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javafx.scene.media.AudioClip;

public class App {
	public static void main(String[] args) {
		int contador = 0;

		if (args.length < 3) {
			System.err.println("Args: email password poll_delay(ms)");
		} else {

			try {

				// //New javamail Session
				Properties props = System.getProperties();
				Session session = Session.getInstance(props);
				// Imap store
				Store store = session.getStore("imaps");
				// Connect with assigned user and password
				store.connect("imap.gmail.com", args[0], args[1]);
				// Open inbox for read-only
				Folder folder = store.getFolder("inbox");
				folder.open(Folder.READ_ONLY);
				// Set Mail Counter
				contador = folder.getMessageCount();
				System.out.println("\n\n\nChecking Mail every " + args[2]
						+ " ms...\n");
				while (true) {
					// Check if Message Count has increased
					if (contador < folder.getMessageCount()) {
						// Get embedded sound and play
						App ap = new App();
						AudioClip clip = new AudioClip(ap.getClass()
								.getResource("notify.wav").toString());
						clip.play();
						// Update Mail Counter
						contador = folder.getMessageCount();
						// Show subject/sender of new message and wait for enter
						// key
						// stroke.
						String from = ((InternetAddress) folder.getMessage(
								contador).getFrom()[0]).getAddress();
						System.out.println("New Message : "
								+ folder.getMessage(contador).getSentDate()
										.toString() + "\nSubject: "
								+ folder.getMessage(contador).getSubject()
								+ "\nFrom: " + from
								+ "\n\nPress enter to dismiss...");
						System.in.read();
						System.out.println("\n\n\nChecking Mail every "
								+ args[2] + " ms...\n");
					}
					// Sleep for assigned delay
					Thread.sleep(Integer.parseInt(args[2]));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
