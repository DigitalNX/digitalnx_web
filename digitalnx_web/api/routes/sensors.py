from . import *
from digitalnx_web.api.enums import SensorType, InfoboxDataType
import random

@api.route('/sensors/', methods=['GET'])
@token_required
def sensors():
    all_groups = SensorGroup.query.all()
    
    final_output = []
    for group in all_groups:
        group_output = []

        for sensor in group.sensors:
            sensor_output = {}
            sensor_output['id'] = sensor.id
            sensor_output['ip_address'] = sensor.ip_address
            sensor_output['device_name'] = sensor.device_name
            sensor_output['group_id'] = sensor.group_id
            sensor_output['sensor_type'] =  sensor.sensor_type.name

            if sensor.sensor_type == SensorType.BOOLEAN:
                sensor_output['value'] = sensor_data_boolean_from_float(sensor.ip_address, sensor.data_route, 10) 
            elif sensor.sensor_type == SensorType.FLOATING_POINT:
                sensor_output['value'] = sensor_data_float(sensor.ip_address, sensor.data_route) 

            group_output.append(sensor_output)
            del(sensor_output)

        final_output.append({'group_id': group.id, 'sensors': group_output, 'group_name': group.name})
        del(group_output)

    response = jsonify(final_output)
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

@api.route('/sensors/<int:group_id>', methods=['GET'])
@token_required
def get_sensors_by_group_id(group_id):
    sensor_group = SensorGroup.query.get(group_id)
    
    output = []
    for sensor in sensor_group.sensors:
        sensor_output = {}
        sensor_output['id'] = sensor.id
        sensor_output['ip_address'] = sensor.ip_address
        sensor_output['device_name'] = sensor.device_name
        sensor_output['group_id'] = sensor.group_id
        sensor_output['sensor_type'] =  sensor.sensor_type.name

        if sensor.sensor_type == SensorType.BOOLEAN:
            sensor_output['value'] = sensor_data_boolean_from_float(sensor.ip_address, sensor.data_route, 10) 
        elif sensor.sensor_type == SensorType.FLOATING_POINT:
            sensor_output['value'] = sensor_data_float(sensor.ip_address, sensor.data_route) 

        output.append(sensor_output)
        del(sensor_output)

    return jsonify({ 'group_id': output })

@api.route('/sensor/<int:sensor_id>/', methods=['GET'])
@token_required
def get_sensor_by_id(sensor_id):
    sensor = TCPSensor.query.get(sensor_id)
    try:
        sensor_output = {}
        sensor_output['id'] = sensor.id
        sensor_output['ip_address'] = sensor.ip_address
        sensor_output['device_name'] = sensor.device_name
        sensor_output['group_id'] = sensor.group_id
        sensor_output['sensor_type'] =  sensor.sensor_type.name

        if sensor.sensor_type == SensorType.BOOLEAN:
            sensor_output['value'] = sensor_data_boolean_from_float(sensor.ip_address, sensor.data_route, 10) 
        elif sensor.sensor_type == SensorType.FLOATING_POINT:
            sensor_output['value'] = sensor_data_float(sensor.ip_address, sensor.data_route) 

        response = jsonify(sensor_output)
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response

    except:
        response = jsonify({ 'result': 400, 'message': 'Error: Invalid host.'})
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response

@api.route('/sensor/<int:sensor_id>/data', methods=['GET'])
@token_required
def get_sensor_data(sensor_id):
    sensor = TCPSensor.query.get(sensor_id)
    try:
        if sensor.sensor_type == SensorType.BOOLEAN:
            sensor_data = sensor_data_boolean_from_float(sensor.ip_address, sensor.data_route, 10) 
        elif sensor.sensor_type == SensorType.FLOATING_POINT:
            sensor_data = sensor_data_float(sensor.ip_address, sensor.data_route) 

        response = jsonify({ 'value': sensor_data, 'sensor_type': sensor.sensor_type.name })
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response

    except:
        response = jsonify({ 'result': 400, 'message': 'Error: Invalid host.'})
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response

# TODO : IMPORTANT > Add more security checks.
@api.route('/sensor/receive/<int:sensor_id>/<value>', methods=['GET'])
def recieve_sensor_data(sensor_id, value):
    try:
        # remoteIP = request.remote_addr
        # if remoteIP in TCPSensor.getSensorIPs():
        #    # Proceed
        if sensor_id in TCPSensor.getSensorIDs():
            sensorObj = TCPSensor.query.get(sensor_id)
            if sensorObj.affected_by_security_mode and Settings.securityModeIsOn():
                print(value)
                return jsonify({'value': value, 'sec_mod': 'on'})
                # TODO : Alert action (to be written)
            else:
                raise Exception("Sensor not affected by security mode or security mode is off.")
        else:
            raise Exception("Not a valid sensor id.")

    except Exception as e:
        response = jsonify({ 'result': 400, 'message': str(e)})
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response

def sensor_data_float(ip_addr, data_route):
    try:
        # For demo purposes we use dummy data.
        # Use the following code for real sensors:
        #

        # response = getJSON('http://' + ip_addr + '/' + data_route, timeout=0.05)
        # return response
        return round(random.uniform(7, 8), 4)
    except:
        return "Error!"

def sensor_data_boolean_from_float(ip_addr, data_route, threshold):
    try:
        # For demo purposes we use dummy data.
        # Use the following code for real sensors:
        #
        # response = getJSON('http://' + ip_addr + '/' + data_route, timeout=0.05)
        # if response < threshold:
        #     return False
        # else:
        #     return True
        return True
    except:
        return "Error!"

def sensor_data_boolean(ip_addr, data_route):
    try:
        # For demo purposes we use dummy data.
        # Use the following code for real sensors:
        #
        # response = getJSON('http://' + ip_addr + '/' + data_route, timeout=0.05)
        # if response == 0:
        #     return False
        # else:
        #     return True
        return True
    except:
        return "Error!"