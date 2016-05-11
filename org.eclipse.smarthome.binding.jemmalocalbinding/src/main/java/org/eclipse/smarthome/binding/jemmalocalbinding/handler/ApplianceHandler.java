/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.binding.jemmalocalbinding.handler;

import static org.eclipse.smarthome.binding.jemmalocalbinding.ApplianceConstants.*;

import org.eclipse.smarthome.core.library.types.DateTimeType;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.HSBType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.eclipse.smarthome.core.library.types.PercentType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.energy_home.dal.functions.ColorControl;
import org.energy_home.dal.functions.DoorLock;
import org.energy_home.dal.functions.Fridge;
import org.energy_home.dal.functions.Oven;
import org.energy_home.dal.functions.WashingMachine;
import org.energy_home.dal.functions.data.TimeData;
import org.energy_home.jemma.osgi.dal.functions.fake.FakeColorControlFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.FakeDoorLockFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.FakeFridgeFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.FakeMultiLevelControlLightFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.FakeMultiLevelSensorThermostatFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.FakeOnOffFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.FakeOvenFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.FakePowerMeterFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.FakeWashingMachineFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.FakeWindowCoveringFunction;
import org.energy_home.jemma.osgi.dal.functions.fake.utils.FakeEventableFunction;
import org.osgi.service.dal.DeviceException;
import org.osgi.service.dal.functions.BooleanControl;
import org.osgi.service.dal.functions.MultiLevelControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ApplianceHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Sandro Tassone - Initial contribution
 */
public class ApplianceHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(ApplianceHandler.class);
    FakeEventableFunction fakeApplianceFunction = null;

    public ApplianceHandler(Thing thing) {
        super(thing);
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        fakeApplianceFunction = instantiateAppliance(thingTypeUID);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        try {
            if (channelUID.getId().equals(CHANNEL_SET_HSB)) {
                if (command instanceof HSBType) {
                    handleHSBCommand((HSBType) command);
                }
            } else if (channelUID.getId().equals(CHANNEL_SET_OPEN_CLOSED)) {
                if (command instanceof OpenClosedType) {
                    handleOpenClosedCommand((OpenClosedType) command);
                }
            } else if (channelUID.getId().equals(CHANNEL_SET_CYCLE)) {
                if (command instanceof StringType) {
                    handleSetCycleCommand((StringType) command);
                }
            } else if (channelUID.getId().equals(CHANNEL_SET_MULTI_LEVEL_CONTROL)) {
                if (command instanceof DecimalType) {
                    handleMultiLevelControlCommand((PercentType) command);
                }
            } else if (channelUID.getId().equals(CHANNEL_SET_ON_OFF)) {
                if (command instanceof OnOffType) {
                    handleOnOffCommand((OnOffType) command);
                }
            } else if (channelUID.getId().equals(CHANNEL_SET_TEMPERATURE)) {
                if (command instanceof DecimalType) {
                    handleSetTemperatureCommand((DecimalType) command);
                }
            } else if (channelUID.getId().equals(CHANNEL_SET_START_TIME)) {
                if (command instanceof DateTimeType) {
                    handleSetStartTimeCommand((DateTimeType) command);
                }
            } else if (channelUID.getId().equals(CHANNEL_SET_WASHING_MACHINE_SPIN)) {
                if (command instanceof DecimalType) {
                    handleSetWashingMachineSpinTypeCommand((DecimalType) command);
                }
            }
        } catch (UnsupportedOperationException | IllegalStateException | DeviceException e) {

        }

    }

    private void handleHSBCommand(HSBType command) throws DeviceException {
        handleHSB(command);
    }

    protected void handleInnerHSB(HSBType command) throws DeviceException {
        handleHSB(command);
        updateState(CHANNEL_SET_HSB, command);
    }

    private void handleHSB(HSBType command) throws DeviceException {
        ColorControl colorControl = null;
        if (fakeApplianceFunction instanceof ColorControl) {
            colorControl = (ColorControl) fakeApplianceFunction;
            colorControl.setHS(command.getHue().shortValue(), command.getSaturation().shortValue());
        }
    }

    private void handleOpenClosedCommand(OpenClosedType command) throws DeviceException {
        handleOpenClosed(command);
    }

    protected void handleInnerOpenClosed(OpenClosedType command) throws DeviceException {
        handleOpenClosed(command);
        updateState(CHANNEL_SET_OPEN_CLOSED, command);
    }

    private void handleOpenClosed(OpenClosedType command) throws DeviceException {
        DoorLock doorLock = null;
        if (fakeApplianceFunction instanceof DoorLock) {
            doorLock = (DoorLock) fakeApplianceFunction;
            switch (command) {
                case OPEN:
                    doorLock.open();
                    break;
                case CLOSED:
                    doorLock.close();
                    break;
            }
        }
    }

    private void handleSetCycleCommand(StringType command) throws DeviceException {
        handleSetCycle(command);
    }

    protected void handleInnerSetCycle(StringType command) throws DeviceException {
        handleSetCycle(command);
        updateState(CHANNEL_SET_CYCLE, command);
    }

    private void handleSetCycle(StringType command) throws DeviceException {
        String cycle = command.toString();
        if (fakeApplianceFunction instanceof Fridge) {
            Fridge fridge = (Fridge) fakeApplianceFunction;
            if (cycle.equals(Fridge.PROPERTY_ECOMODE)) {
                fridge.setEcoMode(true);
            } else if (cycle.equals(Fridge.PROPERTY_HOLIDAYMODE)) {
                fridge.setHolidayMode(true);
            } else if (cycle.equals(Fridge.PROPERTY_ICEPARTY)) {
                fridge.setIceParty(true);
            } else if (cycle.equals(Fridge.PROPERTY_SUPERCOOLMODE)) {
                fridge.setSuperCoolMode(true);
            } else if (cycle.equals(Fridge.PROPERTY_SUPERFREEZE)) {
                fridge.setSuperFreezeMode(true);
            }
        } else if (fakeApplianceFunction instanceof Oven) {
            Oven oven = (Oven) fakeApplianceFunction;
            try {
                oven.setCycle(Short.valueOf(cycle));
            } catch (NumberFormatException e) {
                logger.warn("NumberFormatException");
            }
        }
    }

    private void handleMultiLevelControlCommand(PercentType command)
            throws UnsupportedOperationException, IllegalStateException, DeviceException, IllegalArgumentException {
        handleMultiLevelControl(command);
    }

    protected void handleInnerMultiLevelControlCommand(PercentType command)
            throws UnsupportedOperationException, IllegalStateException, DeviceException, IllegalArgumentException {
        handleMultiLevelControl(command);
        updateState(CHANNEL_SET_MULTI_LEVEL_CONTROL, command);
    }

    private void handleMultiLevelControl(PercentType command)
            throws UnsupportedOperationException, IllegalStateException, DeviceException, IllegalArgumentException {
        MultiLevelControl multiLevelControl = null;

        if (fakeApplianceFunction instanceof MultiLevelControl) {
            multiLevelControl = (MultiLevelControl) fakeApplianceFunction;
            multiLevelControl.setData(command.toBigDecimal());

        }
    }

    private void handleOnOffCommand(OnOffType command)
            throws UnsupportedOperationException, IllegalStateException, DeviceException {
        handleOnOff(command);
    }

    protected void handleInnerOnOff(OnOffType command)
            throws UnsupportedOperationException, IllegalStateException, DeviceException {
        handleOnOff(command);
        updateState(CHANNEL_SET_ON_OFF, command);
    }

    private void handleOnOff(OnOffType command)
            throws UnsupportedOperationException, IllegalStateException, DeviceException {
        BooleanControl booleanControl = null;
        if (fakeApplianceFunction instanceof BooleanControl) {
            booleanControl = (BooleanControl) fakeApplianceFunction;

            switch (command) {
                case ON:
                    booleanControl.setTrue();
                    break;
                case OFF:
                    booleanControl.setFalse();
                    break;
            }
        }
    }

    private void handleSetTemperatureCommand(DecimalType command) throws DeviceException {
        handleSetTemperature(command);
    }

    protected void handleInnerSetTemperature(DecimalType command) throws DeviceException {
        handleSetTemperature(command);
        updateState(CHANNEL_SET_TEMPERATURE, command);
    }

    private void handleSetTemperature(DecimalType command) throws DeviceException {
        if (fakeApplianceFunction instanceof Oven) {
            Oven oven = (Oven) fakeApplianceFunction;
            oven.setTemperature(new Integer(command.intValue()));
        }
    }

    private void handleSetStartTimeCommand(DateTimeType command) throws DeviceException {
        handleSetStartTime(command);
    }

    protected void handleInnerSetStartTime(DateTimeType command) throws DeviceException {
        handleSetStartTime(command);
        updateState(CHANNEL_SET_START_TIME, command);
    }

    private void handleSetStartTime(DateTimeType command) throws DeviceException {
        if (fakeApplianceFunction instanceof Oven) {
            Oven oven = (Oven) fakeApplianceFunction;
            oven.setStartTime(new TimeData(command.getCalendar().getTime().getTime(), null));
        } else if (fakeApplianceFunction instanceof WashingMachine) {
            WashingMachine wMachine = (WashingMachine) fakeApplianceFunction;
            wMachine.setStartTime(new TimeData(command.getCalendar().getTime().getTime(), null));
        }
    }

    private void handleSetWashingMachineSpinTypeCommand(DecimalType command) throws DeviceException {
        handleSetWashingMachineSpinType(command);
    }

    protected void handleInnerSetWashingMachineSpinType(DecimalType command) throws DeviceException {
        handleSetWashingMachineSpinType(command);
        updateState(CHANNEL_SET_WASHING_MACHINE_SPIN, command);
    }

    private void handleSetWashingMachineSpinType(DecimalType command) throws DeviceException {
        if (fakeApplianceFunction instanceof WashingMachine) {
            WashingMachine washingMachine = (WashingMachine) fakeApplianceFunction;
            washingMachine.setSpin(command.shortValue());
        }
    }

    protected void handleInnerGetFridgeTemperature(DecimalType command) {
        updateState(CHANNEL_GET_FRIDGE_TEMPERATURE, command);
    }

    protected void handleInnerGetFreezerTemperature(DecimalType command) {
        updateState(CHANNEL_GET_FREEZER_TEMPERATURE, command);
    }

    protected void handleInnerGetEndTime(DateTimeType command) {
        updateState(CHANNEL_GET_END_TIME, command);
    }

    protected void handleInnerGetMultiLevelSensor(DecimalType command) {
        updateState(CHANNEL_GET_MULTI_LEVEL_SENSOR, command);
    }

    protected void handleInnerGetRemainingTime(DateTimeType command) {
        updateState(CHANNEL_GET_REMAINING_TIME, command);
    }

    @Override
    public void initialize() {
        // TODO: Initialize the thing. If done set status to ONLINE to indicate proper working.
        // Long running initialization should be done asynchronously in background.
        updateStatus(ThingStatus.ONLINE);

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    private FakeEventableFunction instantiateAppliance(ThingTypeUID thingTypeUID) {
        FakeEventableFunction fef = null;

        if (thingTypeUID.equals(THING_FAKE_COLOR_CONTROL)) {
            fef = new FakeColorControlFunction();
        } else if (thingTypeUID.equals(THING_FAKE_DOOR_LOCK)) {
            fef = new FakeDoorLockFunction();
        } else if (thingTypeUID.equals(THING_FAKE_FRIDGE)) {
            fef = new FakeFridgeFunction();
        } else if (thingTypeUID.equals(THING_FAKE_MULTI_LEVEL_CONTROL_LIGHT)) {
            fef = new FakeMultiLevelControlLightFunction();
        } else if (thingTypeUID.equals(THING_FAKE_MULTI_LEVEL_SENSOR_THERMOSTAT)) {
            fef = new FakeMultiLevelSensorThermostatFunction();
        } else if (thingTypeUID.equals(THING_FAKE_ON_OFF)) {
            fef = new FakeOnOffFunction();
        } else if (thingTypeUID.equals(THING_FAKE_OVEN)) {
            fef = new FakeOvenFunction();
        } else if (thingTypeUID.equals(THING_FAKE_POWER_METER)) {
            fef = new FakePowerMeterFunction();
        } else if (thingTypeUID.equals(THING_FAKE_WASHING_MACHINE)) {
            fef = new FakeWashingMachineFunction();
        } else if (thingTypeUID.equals(THING_FAKE_WINDOW_COVERING)) {
            fef = new FakeWindowCoveringFunction();
        }

        return fef;
    }
}
