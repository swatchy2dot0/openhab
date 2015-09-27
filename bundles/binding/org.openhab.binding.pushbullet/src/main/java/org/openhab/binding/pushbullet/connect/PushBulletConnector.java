package org.openhab.binding.pushbullet.connect;

import java.util.List;

import org.openhab.binding.pushbullet.internal.PushBulletBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.iharder.jpushbullet2.Device;
import net.iharder.jpushbullet2.PushbulletClient;
import net.iharder.jpushbullet2.PushbulletException;

public class PushBulletConnector {
	
	private static final Logger logger =
			LoggerFactory.getLogger(PushBulletConnector.class);
	
	private PushbulletClient pbClient;
	private PushBulletReceiver pbRec = new PushBulletReceiver();
	private String accessToken;
	private String deviceName;
	private PushBulletBinding binding;
	private String deviceIden;
	private String defaultRec;
	
	private static PushBulletConnector instance;
	
	private PushBulletConnector(){		
	}
	
	public static PushBulletConnector getInstance(){
		if(instance == null){
			instance = new PushBulletConnector();
		}
		return instance;
	}
	
	public void startPbClient(){
		if(pbClient == null){
			pbClient = new PushbulletClient(accessToken);
			createDevice();
			startListening();
		}
	}
	
	private void createDevice(){
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
	
	private void startListening(){
		pbClient.addPushbulletListener(pbRec);		
		logger.debug("starting websocket to listen for new pushes");
		pbClient.stopWebsocket();
		pbClient.startWebsocket();
	}
	
	public String getDefaultRec(){
		return defaultRec;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public PushBulletBinding getBinding() {
		return binding;
	}

	public void setBinding(PushBulletBinding binding) {
		this.binding = binding;
	}

	public String getDeviceIden() {
		return deviceIden;
	}

	public void setDeviceIden(String deviceIden) {
		this.deviceIden = deviceIden;
	}

	public void setDefaultRec(String defaultRec) {
		this.defaultRec = defaultRec;
	}

	public PushbulletClient getPbClient() {
		return pbClient;
	}

	public PushBulletReceiver getPbRec() {
		return pbRec;
	}

	public void setPbRec(PushBulletReceiver pbRec) {
		this.pbRec = pbRec;
	}
	
	
}
