/**
 * Copyright (c) 2010-2013, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.action.pushbullet.internal;

import java.util.Dictionary;
import java.util.Map;

import org.openhab.binding.pushbullet.connect.PushBulletConnector;
import org.openhab.core.scriptengine.action.ActionService;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
	

/**
 * This class registers an OSGi service for the pushBullet action.
 * 
 * @author exilhanseat
 * @since 0.1
 */
public class PushBulletActionService implements ActionService, ManagedService {

	public void activate() {}

	public void deactivate() {}
	
	@Override
	public void updated(Dictionary<String, ?> config){
	}
	
	@Override
	public String getActionClassName() {
		return PushBullet.class.getCanonicalName();
	}

	@Override
	public Class<?> getActionClass() {
		return PushBullet.class;
	}
	
}
