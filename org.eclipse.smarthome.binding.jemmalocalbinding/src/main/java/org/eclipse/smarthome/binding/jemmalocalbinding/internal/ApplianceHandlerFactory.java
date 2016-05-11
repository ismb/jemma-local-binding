/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.binding.jemmalocalbinding.internal;

import static org.eclipse.smarthome.binding.jemmalocalbinding.ApplianceConstants.SUPPORTED_THING_TYPES_UIDS;

import org.eclipse.smarthome.binding.jemmalocalbinding.handler.ApplianceHandler;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;

/**
 * The {@link ApplianceHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Sandro Tassone - Initial contribution
 */
public class ApplianceHandlerFactory extends BaseThingHandlerFactory {

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {

        return new ApplianceHandler(thing);
    }
}
