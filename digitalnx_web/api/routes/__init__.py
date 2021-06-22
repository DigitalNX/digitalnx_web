import json
from flask import Blueprint, jsonify, make_response, request, current_app
from ..models import (
    Settings,
    DashboardLayout,
    TCPRelayDevice,
    TCPRelaySchedule,
    RelayGroup,
    TCPSensor,
    SensorGroup,
    WeatherWidget,
    Note,
    Info
)
from sqlalchemy.orm import load_only
from ..database import db_session
from ..utilities import getJSON, add_api_cache
from ..authentication import token_required
import time

api = Blueprint('api', __name__, template_folder='templates', static_folder='static',
                static_url_path='/site/static')

from . import layout
from . import relays
from . import sensors
from . import notes
from . import weather
from . import info
from . import schedules
from . import settings