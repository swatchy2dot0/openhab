/**
 * Copyright (c) 2010-2013, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.action.pushbullet.internal;

import net.iharder.jpushbullet2.Device;
import net.iharder.jpushbullet2.PushbulletException;

import org.openhab.binding.pushbullet.connect.PushBulletConnector;
import org.openhab.core.scriptengine.action.ActionDoc;
import org.openhab.core.scriptengine.action.ParamDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class contains the methods that are made available in scripts and rules for pushBullet.
 * 
 * @author exilhanseat
 * @since 0.1
 */
public class PushBullet {

	private static final Logger logger = LoggerFactory.getLogger(PushBullet.class);
	
	private static PushBulletConnector con = PushBulletConnector.getInstance();
	
	@ActionDoc(text = "Sends a text to the Default PushBullet device")
	public static boolean sendPushToDefaultDevice(
			@ParamDoc(name = "message", text = "The message to send to PushBullet") String message) {

		return sendPush(con.getDefaultRec(), message);
	}
	
	@ActionDoc(text = "Sends a text to the Specific PushBullet device")
	public static boolean sendPushToSpecificDevice(
			@ParamDoc(name = "deviceNickName", text = "The device to send the message to") String deviceNickName,
			@ParamDoc(name = "message", text = "The message to send to PushBullet") String message) {

		return sendPush(deviceNickName, message);
	}

	private static boolean sendPush(String deviceNickName, String message){
		try {
			if(con.getPbClient() != null){
				for(Device dev : con.getPbClient().getActiveDevices()){
					if(dev.getNickname().equals(deviceNickName)){
						con.getPbClient().sendNote(dev.getIden(),"openHAB",message);
						return true;
					}
				}
				logger.error("Device with nickname " + deviceNickName + " not found");
				return false;
			} else {
				return false;
			}
		} catch (PushbulletException e) {
			logger.error("Connection to PushBullet broken");
			logger.error(e.getStackTrace().toString());
			return false;
		}
	}

}
