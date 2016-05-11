/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.binding.jemmalocalbinding;

import java.util.Set;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.Sets;

/**
 * The {@link JemmaLocalBindingBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Sandro Tassone - Initial contribution
 */
public class ApplianceConstants {

    public static final String BINDING_ID = "jemmalocalbinding";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_FAKE_COLOR_CONTROL = new ThingTypeUID(BINDING_ID, "fake_color_control");
    public final static ThingTypeUID THING_FAKE_DOOR_LOCK = new ThingTypeUID(BINDING_ID, "fake_door_lock");
    public final static ThingTypeUID THING_FAKE_FRIDGE = new ThingTypeUID(BINDING_ID, "fake_fridge");
    public final static ThingTypeUID THING_FAKE_MULTI_LEVEL_CONTROL_LIGHT = new ThingTypeUID(BINDING_ID,
            "fake_multi_level_control_light");
    public final static ThingTypeUID THING_FAKE_MULTI_LEVEL_SENSOR_THERMOSTAT = new ThingTypeUID(BINDING_ID,
            "fake_multi_level_sensor_thermostat");
    public final static ThingTypeUID THING_FAKE_ON_OFF = new ThingTypeUID(BINDING_ID, "fake_on_off");
    public final static ThingTypeUID THING_FAKE_OVEN = new ThingTypeUID(BINDING_ID, "fake_oven");
    public final static ThingTypeUID THING_FAKE_POWER_METER = new ThingTypeUID(BINDING_ID, "fake_power_meter");
    public final static ThingTypeUID THING_FAKE_WASHING_MACHINE = new ThingTypeUID(BINDING_ID, "fake_washing_machine");
    public final static ThingTypeUID THING_FAKE_WINDOW_COVERING = new ThingTypeUID(BINDING_ID, "fake_window_covering");

    public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Sets.newHashSet(THING_FAKE_ON_OFF);

    // public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Sets.newHashSet(THING_FAKE_COLOR_CONTROL,
    // THING_FAKE_DOOR_LOCK, THING_FAKE_FRIDGE, THING_FAKE_MULTI_LEVEL_CONTROL_LIGHT,
    // THING_FAKE_MULTI_LEVEL_SENSOR_THERMOSTAT, THING_FAKE_ON_OFF, THING_FAKE_OVEN, THING_FAKE_POWER_METER,
    // THING_FAKE_WASHING_MACHINE, THING_FAKE_WINDOW_COVERING);

    // List of all Channel ids

    public final static String CHANNEL_SET_HSB = "set_hsb_channel";
    public final static String CHANNEL_SET_OPEN_CLOSED = "open_closed_channel";
    public final static String CHANNEL_SET_CYCLE = "set_cycle_channel";
    public final static String CHANNEL_GET_FRIDGE_TEMPERATURE = "get_fridge_temperature_channel";
    public final static String CHANNEL_GET_FREEZER_TEMPERATURE = "get_freezer_temperature_channel";
    public final static String CHANNEL_SET_MULTI_LEVEL_CONTROL = "set_multi_level_control_channel";
    public final static String CHANNEL_GET_MULTI_LEVEL_SENSOR = "get_multi_level_sensor_channel";
    public final static String CHANNEL_SET_ON_OFF = "set_on_off_channel";
    public final static String CHANNEL_SET_TEMPERATURE = "set_temperature_channel";
    public final static String CHANNEL_SET_START_TIME = "set_start_time_channel";
    public final static String CHANNEL_GET_END_TIME = "get_end_time_channel";
    public final static String CHANNEL_GET_REMAINING_TIME = "get_remaining_time_channel";
    public final static String CHANNEL_INSTANT_CURRENT = "get_instant_current_channel";
    public final static String CHANNEL_TOTAL_CURRENT = "get_total_current_channel";
    public final static String CHANNEL_SET_WASHING_MACHINE_SPIN = "set_spin_channel";

}
