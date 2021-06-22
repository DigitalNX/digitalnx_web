from . import *
from digitalnx_web.api.enums import SensorType, InfoboxDataType
from .sensors import sensor_data_float, sensor_data_boolean_from_float
import random

@api.route('/info_data')
@token_required
def info():
    output = {}
    output['title'] = 'Info Box'

    # info messages are registered in the ORM with a title and a unique id.
    # for a real-world app, just replace the dummy data with actual sensors data data etc 
    # and front-end would detect and work with them via their info_id
    output['info_id'] = '1'

    sensorGroups = SensorGroup.query.all()
    
    info_body = []

    for sensorGroup in sensorGroups:
        info_item = {}
        info_item['title'] = sensorGroup.name
        info_item['type'] = InfoboxDataType.SENSOR.name
        tmpContent = []
        for sensor in sensorGroup.sensors:
            tmp = {}
            tmp['device_name'] = sensor.device_name
            tmp['sensor_type'] = sensor.sensor_type.name
            if sensor.sensor_type == SensorType.BOOLEAN:
                tmp['value'] = sensor_data_boolean_from_float(sensor.ip_address, sensor.data_route, 10)
            elif sensor.sensor_type == SensorType.FLOATING_POINT:
                tmp['value'] = sensor_data_float(sensor.ip_address, sensor.data_route)

            tmpContent.append(tmp)

        info_item['content'] = tmpContent
        info_body.append(info_item)

    # TODO: Change content 'type' to SOCIAL_MEDIA
    info_item = {}
    info_item['title'] = 'Website Statistics'
    info_item['type'] = InfoboxDataType.RAW_STRING.name
    info_item['content'] = ['Today\'s visitors: 2', 'Total visitors: 4', 'Today\'s comments: 3', 'Total comments: 6']
    info_body.append(info_item)

    info_item = {}
    info_item['title'] = 'Social Network Statistics'
    info_item['type'] = InfoboxDataType.RAW_STRING.name
    info_item['content'] = ['Today\'s liked posts: 4', 'Total liked posts: 15', 'Today\'s comments: 5', 'Total comments: 4']
    info_body.append(info_item)

    output['items'] = info_body
    response = jsonify([output])
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response 
