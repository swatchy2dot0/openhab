/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.pushbullet.internal;

import java.util.Dictionary;
import java.util.Map;

import net.iharder.jpushbullet2.PushbulletClient;

import org.apache.commons.lang.StringUtils;
import org.openhab.binding.pushbullet.PushBulletBindingProvider;
import org.openhab.binding.pushbullet.connect.PushBulletConnector;
import org.openhab.core.binding.AbstractActiveBinding;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
	

/**
 * Implement this class if you are going create an actively polling service
 * like querying a Website/Device.
 * 
 * @author exilhanseat
 * @since 0.1
 */
public class PushBulletBinding extends AbstractActiveBinding<PushBulletBindingProvider> implements ManagedService {

	private static final Logger logger = 
		LoggerFactory.getLogger(PushBulletBinding.class);
	
	public static final String MESSAGE_KEY_ACCESS_TOKEN = "accesstoken";
	public static final String DEVICE_NAME = "devicename";
	public static final String DEFAULT_RECEIVER = "defaultreceiver";
	PushBulletConnector connect = PushBulletConnector.getInstance();

	@Override
	public void updated(Dictionary<String, ?> config){
		logger.info("PushBullet: loading config");
		connect.setBinding(this);
		if(config != null){
			connect.setAccessToken((String) config.get(MESSAGE_KEY_ACCESS_TOKEN));
			connect.setDeviceName((String) config.get(DEVICE_NAME));
			connect.setDefaultRec((String) config.get(DEFAULT_RECEIVER));
			connect.startPbClient();
		} else {
			logger.info("Pushbullet is not configured");
		}
	}
	
	public void postUpdate(String item, State newState) {
		for (PushBulletBindingProvider provider : providers) {
			String itemName = provider.getItemName(item);
			if(StringUtils.isNotEmpty(itemName)) {
				eventPublisher.postUpdate(itemName, newState);
			}
		}
	}
	
	public void activate(final BundleContext bundleContext, final Map<String, Object> configuration) {
		
	}
	
	/**
	 * Called by the SCR when the configuration of a binding has been changed through the ConfigAdmin service.
	 * @param configuration Updated configuration properties
	 */
	public void modified(final Map<String, Object> configuration) {
		// update the internal configuration accordingly
	}
	
	
	public void deactivate(final int reason) {
		
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	protected long getRefreshInterval() {
		return 10;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	protected String getName() {
		return "PushBullet Refresh Service";
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void execute() {
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void internalReceiveCommand(String itemName, Command command) {
	
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void internalReceiveUpdate(String itemName, State newState) {
		
	}



}
