package chylex.javacheck.report;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;
import org.lwjgl.Sys;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.util.Map;

public final class JavaCheckerReporter implements IFMLCallHook{
	public static JavaVersion minVersion = null;
	
	@Override
	public Void call() throws Exception{
		if (!SystemUtils.isJavaVersionAtLeast(minVersion)){
			FMLRelaunchLog.severe(getConsoleReport());

			JEditorPane ep = new JEditorPane("text/html", "<html>"+getWindowReport()+"</html>");
			ep.addHyperlinkListener(new HyperlinkListener()
			{
				@Override
				public void hyperlinkUpdate(HyperlinkEvent e)
				{
					if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
						Sys.openURL(e.getURL().toString());
				}
			});
			ep.setEditable(false);
			JOptionPane.showMessageDialog(null, ep, "Outdated Java", JOptionPane.ERROR_MESSAGE);

			throw new OutdatedJavaException();
		}
		
		return null;
	}

	@Override
	public void injectData(Map<String,Object> data){}
	
	static String getConsoleReport(){
		return new StringBuilder().append("\n")
		.append("\n!! DO NOT REPORT !!\n\n")
		.append("Pixelmon requires Java "+minVersion.toString()+" or newer, you are using ").append(SystemUtils.JAVA_VERSION).append(".\n")
		.append("If you are running Windows you can\n")
		.append("visit https://java.com/download/ for the latest version.\n")
		.append("Please, uninstall the old version first to prevent further issues.")
		.append("\n")
		.append("If you are running OSX Lion (10.7) or newer, Please use the following launcher\n")
		.append("http://launcher.mojang.com/download/Minecraft-staging.dmg")
		.append("\n\n!! DO NOT REPORT !!\n")
		.toString();
	}
	
	static String getWindowReport(){
		return new StringBuilder()
		.append("Pixelmon requires Java "+minVersion.toString()+" or newer, you are using ").append(SystemUtils.JAVA_VERSION).append(".<br>")
		.append("If you are running Windows you can<br>")
		.append("visit <span style=\"color:red\"><a href=\"https://java.com/download\">https://java.com/download/</a></span> for the latest version.<br>")
		.append("Please, uninstall the old version first to prevent further issues.")
		.append("<br>")
		.append("If you are running OSX Lion (10.7) or newer, Please use the following launcher<br>")
		.append("<span style=\"color:red\"><a href=\"http://launcher.mojang.com/download/Minecraft-staging.dmg\">http://launcher.mojang.com/download/Minecraft-staging.dmg</a></span>")
		.toString();
	}
}
