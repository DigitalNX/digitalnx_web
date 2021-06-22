import os,sys,inspect
current_dir = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
parent_dir = os.path.dirname(current_dir)
sys.path.insert(0, parent_dir) 

from datetime import datetime
from digitalnx_web.api.database import init_db
from digitalnx_web.api.database import db_session
from digitalnx_web.api import models
from digitalnx_web.api.enums import SensorType, SecurityMode, UILanguage

from digitalnx_web.api.models import (
    Settings,
    RelayGroup,
    TCPRelayDevice,
    TCPRelaySchedule,
    SensorGroup,
    TCPSensor,
    Note,
    WeatherWidget,
    DashboardLayout,
    User,
    Info,
)

# Add admin account
user = User('admin', 'password')
db_session.add(user)

# Add weather widget
weather_widget = WeatherWidget(location='US, New York')
db_session.add(weather_widget)

# Add relays
group = RelayGroup('Bedroom Relays')
for i in range(1,6):
    relay = TCPRelayDevice(ip_address='192.168.1.6{0}'.format(str(i)), device_name='Relay {0}'.format(str(i)))
    group.relays.append(relay)
    db_session.add(relay)

relay = TCPRelayDevice(ip_address='192.168.1.70'.format(str(i)), device_name='Relay 6')
group.relays.append(relay)
db_session.add(relay)

db_session.add(group)
db_session.commit()

# Add relay schedule
currentTimestamp = datetime.utcnow().timestamp()
relaySchedule = TCPRelaySchedule(relay.id, int(currentTimestamp)+2, 10, 5)
db_session.add(relaySchedule)

# Add sensors
sensorGroup = SensorGroup('Security Info')
sensor1 = TCPSensor(id=1, ip_address='192.168.1.106', sensor_type=SensorType.BOOLEAN, device_name="Garage Door", affected_by_security_mode=True)
sensor2 = TCPSensor(id=2, ip_address='192.168.1.107', sensor_type=SensorType.BOOLEAN, device_name="Front Door", affected_by_security_mode=True)
sensorGroup.sensors.append(sensor1)
sensorGroup.sensors.append(sensor2)

db_session.add(sensor1)
db_session.add(sensor2)
db_session.add(sensorGroup)

sensorGroup = SensorGroup('Garden Sensors')
sensor1 = TCPSensor(id=3, ip_address='192.168.1.110', sensor_type=SensorType.FLOATING_POINT, device_name="Soil Moisture", affected_by_security_mode=False)
sensor2 = TCPSensor(id=4, ip_address='192.168.1.111', sensor_type=SensorType.FLOATING_POINT, device_name="Light Level", affected_by_security_mode=False)
sensorGroup.sensors.append(sensor1)
sensorGroup.sensors.append(sensor2)

db_session.add(sensor1)
db_session.add(sensor2)
db_session.add(sensorGroup)

# Add notes
note1 = Note(title="Note 2 (long)", content="""Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.""")
note2 = Note(title="Note 1 (short)", content="This is an example of a short note.")

db_session.add(note1)
db_session.add(note2)

# Add infobox
info_box = Info('Info Box')
db_session.add(info_box)

# Add items to dashboard layout
dashboard_item1 = DashboardLayout(item='WeatherWidget', weather_widget_id=1)
dashboard_item2 = DashboardLayout(item='Info', info_id=1)
dashboard_item3 = DashboardLayout(item='RelayGroup', relay_group_id=1)
dashboard_item4 = DashboardLayout(item='Note', note_id=1)
dashboard_item5 = DashboardLayout(item='Note', note_id=2)
db_session.add(dashboard_item1)
db_session.add(dashboard_item2)
db_session.add(dashboard_item3)
db_session.add(dashboard_item4)
db_session.add(dashboard_item5)

db_session.commit()