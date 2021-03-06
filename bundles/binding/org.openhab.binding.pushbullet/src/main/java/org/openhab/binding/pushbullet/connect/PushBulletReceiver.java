package org.openhab.binding.pushbullet.connect;

import org.openhab.core.library.types.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.iharder.jpushbullet2.Push;
import net.iharder.jpushbullet2.PushbulletEvent;
import net.iharder.jpushbullet2.PushbulletException;
import net.iharder.jpushbullet2.PushbulletListener;

public class PushBulletReceiver implements PushbulletListener {
	
	private static final Logger logger =
			LoggerFactory.getLogger(PushBulletReceiver.class);

	@Override
	public void pushReceived(PushbulletEvent pushEvent) {
        try {
			for(Push p: PushBulletConnector.getInstance().getPbClient().getPushes(1)){
				if(p.getTarget_device_iden().equals(PushBulletConnector.getInstance().getDeviceIden())){
					String[] input = p.getBody().trim().split(":");
					PushBulletConnector.getInstance().getBinding().postUpdate(input[0], new StringType(input[1]));
				} else {
					logger.debug("Message for other recipient");
				}
			}
		} catch (PushbulletException e) {
			logger.error("somethings wrong with the pushes or the connection");
			e.printStackTrace();
		}
		
	}

    @Override
    public void devicesChanged(PushbulletEvent pushEvent) {
        logger.debug("device changed" + pushEvent);
    }

	@Override
	public void websocketEstablished(PushbulletEvent arg0) {
		logger.info("websocket established");
		
	}

}
