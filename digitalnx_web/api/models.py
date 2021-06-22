from datetime import datetime
from sqlalchemy import Column, Integer, String, Enum, ForeignKey, CheckConstraint, Boolean, Date
from sqlalchemy.orm import relationship, backref
from sqlalchemy.ext.declarative import declarative_base
from werkzeug.security import generate_password_hash, check_password_hash
from .database import Base
from .enums import SensorType, SecurityMode, UILanguage

# TODO enforce one-row-only constraint to this ORM:
class Settings(Base):
    __tablename__ = 'settings'
    version = Column(String(30), unique=True, nullable=False, primary_key=True)
    securityMode = Column(Enum(SecurityMode), unique=True, nullable=False)
    UILanguage = Column(Enum(UILanguage), unique=True, nullable=False)

    def __init__(self, version, securityMode, UILanguage):
        self.version = version
        self.securityMode = securityMode
        self.UILanguage = UILanguage
    
    @classmethod
    def securityModeIsOn(cls):
        obj = Settings.query.all()[0]
        return obj.securityMode == SecurityMode.SECURITY

class User(Base):
    __tablename__ = 'users'
    id = Column(Integer, primary_key=True)
    username = Column(String(100), unique=True, nullable=False)
    password = Column(String(255), nullable=False)

    def __init__(self, username, password):
        self.username = username
        self.password = generate_password_hash(password)

    @classmethod
    def authenticate(cls, username, password):
        if username == None or password == None:
            return None
        
        user = cls.query.filter_by(username=username).first()
        if user == None or check_password_hash(user.password, password) == False:
            return None
        return user
    
    def __repr__(self):
        return dict(id=self.id, email=self.email)

class TCPRelayDevice(Base):
    __tablename__ = 'tcp_relay_devices'
    id = Column(Integer, primary_key=True)
    ip_address = Column(String, unique=True, nullable=False)
    device_name = Column(String(16))
    control_route = Column(String(16))
    status_route = Column(String(16))
    group_id = Column(Integer, ForeignKey('relay_groups.id'))
    group = relationship('RelayGroup', backref='relays') 
    
    #schedule_id = Column(Integer, ForeignKey('tcp_relay_schedule.id'))
    #schedule = relationship('TCPRelaySchedule', backref='schedules')
    
    def __init__(self, ip_address, device_name=None, control_route="RELAY", status_route="STATUS", controlled_by_scheduler=False):
        self.ip_address = ip_address
        self.device_name = device_name
        self.control_route = control_route
        self.status_route = status_route
        self.controlled_by_scheduler = controlled_by_scheduler

    def __str__(self):
        return '[Wireless Relay] {0} : {1}'.format(self.device_name, self.ip_address)
    
class RelayGroup(Base):
    __tablename__ = 'relay_groups'
    id = Column(Integer, primary_key=True)
    name = Column(String(16))

    def __init__(self, name=None):
        self.name = name

class TCPRelaySchedule(Base):
    __tablename__ = 'tcp_relay_schedules'
    id = Column(Integer, primary_key=True)
    relay_id = Column(Integer, ForeignKey('tcp_relay_devices.id'), unique=True)
    relay = relationship("TCPRelayDevice", backref=backref("tcp_relay_schedules", uselist=False))
    description = Column(String(100), nullable=True)
    startingTime = Column(Integer, nullable=False) # in epoch time
    period = Column(Integer, nullable=False)  # Time (in seconds) between each execution
    executionDuration = Column(Integer, nullable=False)
    enabled = Column(Boolean)

    def __init__(self, relay_id, startingTime, period, executionDuration, description="", enabled=True):
        self.relay_id = relay_id
        self.startingTime = startingTime
        self.period = period
        self.executionDuration = executionDuration
        self.description = description
        self.enabled = enabled

    @classmethod
    def upcomingExecutions(cls):
        rowObjects = cls.query.all()
        schedules = []
        for s in rowObjects:
            nextExec = (s.period + s.executionDuration) - ((datetime.utcnow().timestamp() - s.startingTime) % (s.period + s.executionDuration))
            schedules.append({'id': s.id,
                              'nextExecution': nextExec,
                              'period': s.period,
                              'executionDuration': s.executionDuration,
                              'relay_id': s.relay_id,
                              'enabled': s.enabled,
                              'description': s.description})

        return schedules

    @classmethod
    def controlledBySchedule(cls, relay_id):
        return len(TCPRelaySchedule.query.filter_by(relay_id=relay_id).all()) != 0

    @classmethod
    def get_scheduled_relay(cls, relay_id):
        s = TCPRelaySchedule.query.filter_by(relay_id=relay_id).all()[0]
        nextExec = (s.period + s.executionDuration) - ((datetime.utcnow().timestamp() - s.startingTime) % (s.period + s.executionDuration))
        return { 'id': s.id,
                 'nextExecution': nextExec,
                 'period': s.period,
                 'executionDuration': s.executionDuration,
                 'relay_id': s.relay_id,
                 'enabled': s.enabled,
                 'description': s.description }

    @classmethod
    def get_scheduled_relays_ids(cls):
        relay_ids = []
        rowObjects = cls.query.all()
        for s in rowObjects:
            relay_ids.append(s.relay_id) 

        return relay_ids

class TCPSensor(Base):
    __tablename__ = 'tcp_sensors'
    id = Column(Integer, primary_key=True, unique=True)
    ip_address = Column(String, unique=True, nullable=False)
    sensor_type = Column(Enum(SensorType), nullable=False)
    device_name = Column(String(16))
    data_route = Column(String(16))
    affected_by_security_mode = Column(Boolean, nullable=False)
    group_id = Column(Integer, ForeignKey('sensor_groups.id'))
    group = relationship('SensorGroup', backref='sensors') 

    def __init__(self, id, ip_address, sensor_type, device_name=None, data_route="DATA", affected_by_security_mode=False):
        self.id = id
        self.ip_address = ip_address
        self.sensor_type = sensor_type
        self.device_name = device_name
        self.data_route =  data_route
        self.affected_by_security_mode = affected_by_security_mode

    def __str__(self):
        return '[Sensor] {0} : {1}'.format(self.device_name, self.ip_address)

    @classmethod
    def getSensorIPs(cls):
        sensorObjs = TCPSensor.query.all()
        IPs = []
        for s in sensorObjs:
            IPs.append(s.ip_address)
        return IPs

    @classmethod
    def getSensorIDs(cls):
        sensorObjs = TCPSensor.query.all()
        IDs = []
        for s in sensorObjs:
            IDs.append(s.id)
        return IDs

class SensorGroup(Base):
    __tablename__ = 'sensor_groups'
    id = Column(Integer, primary_key=True)
    name = Column(String(16))

    def __init__(self, name=None):
        self.name = name

class WeatherWidget(Base):
    __tablename__ = 'weather_widgets'
    id = Column(Integer, primary_key=True)
    location = Column(String(16), nullable=False)

class DashboardLayout(Base):
    __tablename__ = 'dashboard_layout'
    __table_args__ = (
        CheckConstraint('item IN ("RelayGroup", "SensorGroup", "WeatherWidget", "Note", "Info") AND'
                        ' ((CAST(relay_group_id IS NOT NULL AS int) +'
                        ' CAST(sensor_group_id IS NOT NULL AS int)  +'
                        ' CAST(weather_widget_id IS NOT NULL AS int) +'
                        ' CAST(note_id IS NOT NULL AS int) +'
                        ' CAST(info_id IS NOT NULL AS int)) = 1)'),
    )
    id = Column(Integer, primary_key=True)
    item = Column('item', String(16), nullable=False)
    relay_group_id = Column('relay_group_id', ForeignKey('relay_groups.id', ondelete="CASCADE"))
    sensor_group_id = Column('sensor_group_id', ForeignKey('sensor_groups.id', onupdate="CASCADE"))
    weather_widget_id = Column('weather_widget_id', ForeignKey('weather_widgets.id', ondelete="CASCADE"))
    note_id = Column('note_id', ForeignKey('notes.id', onupdate="CASCADE"))
    info_id = Column('info_id', ForeignKey('info.id', onupdate="CASCADE"))

class Note(Base):
    __tablename__ = 'notes'
    id = Column(Integer, primary_key=True)
    content = Column(String(128), nullable=False)
    title = Column(String(16))
    color = Column(String(6))

    def __init__(self, content, title=None, color=None):
        self.content = content
        self.title = title
        self.color = color
    
    def __str__(self):
        return '[Note] {0}.'.format(self.title)

# This ORM acts merely as a class that 'groups' some info
# items with a title and a unique id (Same as GroupRelay class)
class Info(Base):
    __tablename__ = 'info'
    id = Column(Integer, primary_key=True)
    title = Column(String(25), nullable=False)

    def __init__(self, title=None):
        self.title = title