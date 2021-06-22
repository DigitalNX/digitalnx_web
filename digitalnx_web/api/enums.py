from enum import Enum

class SensorType(Enum):
    BOOLEAN = 1
    FLOATING_POINT = 2
    # Possibly more sensor types like color code etc

class InfoboxDataType(Enum):
    RAW_STRING = 1
    SENSOR = 2
    SOCIAL_MEDIA = 3

class SecurityMode(Enum):
    NORMAL = 1
    SECURITY = 2

class UILanguage(Enum):
    EN = 1
    FA = 2