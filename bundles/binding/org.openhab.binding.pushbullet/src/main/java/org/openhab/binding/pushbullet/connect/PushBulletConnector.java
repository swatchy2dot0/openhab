package org.openhab.binding.pushbullet.connect;

import java.util.Dictionary;
import java.util.List;

import org.openhab.binding.pushbullet.internal.PushBulletBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.iharder.jpushbullet2.AbstractPushbulletListener;
import net.iharder.jpushbullet2.Device;
import net.iharder.jpushbullet2.Push;
import net.iharder.jpushbullet2.PushbulletClient;
import net.iharder.jpushbullet2.PushbulletEvent;
import net.iharder.jpushbullet2.PushbulletException;
import net.iharder.jpushbullet2.PushbulletListener;

public class PushBulletConnector {
	
	private static final Logger logger =
			LoggerFactory.getLogger(PushBulletConnector.class);
	
	public static final String MESSAGE_KEY_ACCESS_TOKEN = "accesstoken";
	public static final String DEVICE_NAME = "devicename";
	public static final String DEFAULT_RECEIVER = "defaultreceiver";
	
	private static PushbulletClient pbClient;
	private static PushBulletReceiver pbRec = new PushBulletReceiver();
	private static String accessToken;
	private static String deviceName;
	public static PushBulletBinding binding;
	public static String deviceIden;
	public static String defaultRec;
	
	private PushBulletConnector(){		
	}
	
	public static PushbulletClient getInstance(){
		if(pbClient == null){
			pbClient = new PushbulletClient(accessToken);
			createDevice();
			startListening();
		}
		return pbClient;
	}
	
	public static PushbulletClient getInstance(Dictionary<String, ?> config){
		if(pbClient == null){
			accessToken = (String) config.get(MESSAGE_KEY_ACCESS_TOKEN);
			deviceName = (String) config.get(DEVICE_NAME);
			defaultRec = (String) config.get(DEFAULT_RECEIVER);
			getInstance();
		}
		return pbClient;
	}
	
	private static void createDevice(){
		try {
			List<Device> knownDevices = pbClient.getActiveDevices();
			boolean inList = false;
			for(Device dev : knownDevices){
				if(dev.getNickname().equals(deviceName)){
					deviceIden = dev.getIden();
					inList = true;
					break;
				}
			}
			
			if(!inList){
				logger.info("Device with name " + deviceName + " is not known. Creating it.");
				pbClient.createDevice(deviceName);
			} else {
				logger.debug("Device with name " + deviceName + " is already known");
			}
		} catch (PushbulletException e) {
			logger.info("Could not load Device-List");
			e.getStackTrace();
		}
	}
	
	private static void startListening(){
		pbClient.addPushbulletListener(pbRec);		
		logger.debug("starting websocket to listen for new pushes");
		pbClient.stopWebsocket();
		pbClient.startWebsocket();
	}
	
	public static String getDefaultRec(){
		return defaultRec;
	}
	
	
}
