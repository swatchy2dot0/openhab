/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.pushbullet.internal;

import java.util.Map.Entry;

import net.iharder.jpushbullet2.Device;
import net.iharder.jpushbullet2.PushbulletException;

import org.openhab.binding.pushbullet.PushBulletBindingProvider;
import org.openhab.binding.pushbullet.connect.PushBulletConnector;
import org.openhab.core.binding.BindingConfig;
import org.openhab.core.items.Item;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is responsible for parsing the binding configuration.
 * 
 * @author exilhanseat
 * @since 0.1
 */
public class PushBulletGenericBindingProvider extends AbstractGenericBindingProvider implements PushBulletBindingProvider {

	private static final Logger logger =
			LoggerFactory.getLogger(PushBulletGenericBindingProvider.class);
	/**
	 * {@inheritDoc}
	 */
	public String getBindingType() {
		return "pushbullet";
	}

	@Override
	public String getItemName(String item) {
		for (Entry<String, BindingConfig> entry : bindingConfigs.entrySet()) {
			
			PushBulletBindingConfig cfg = (PushBulletBindingConfig) entry.getValue();
			if(item.equals(cfg.item)) {
				return entry.getKey();
			}
		}		

		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processBindingConfiguration(String context, Item item, String bindingConfig) throws BindingConfigParseException {
		super.processBindingConfiguration(context, item, bindingConfig);

		logger.debug("Process binding cfg for {} with settings {} [Context:{}]",
				item.getName(), bindingConfig, context);
		
		PushBulletBindingConfig config = new PushBulletBindingConfig();
		
			String[] configParts = bindingConfig.split(":");
			if (configParts.length > 2) {
				throw new BindingConfigParseException("pushbullet binding configuration must not contain more than one part");
			}
			
			if(configParts[0].trim().equals("item")) {
				config.item = configParts[1];
			}

		addBindingConfig(item, config);		
	}
	
	
	/**
	 * This is a helper class holding binding specific configuration details
	 * 
	 * @author exilhanseat
	 * @since 0.1
	 */
	class PushBulletBindingConfig implements BindingConfig {
		public String item;
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
	}
	
}
