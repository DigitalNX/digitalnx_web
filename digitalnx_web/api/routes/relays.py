from . import *

@api.route('/relays/', methods=['GET'])
def relays():
    all_groups = RelayGroup.query.all()
     
    final_output = []
    scheduled_relays = TCPRelaySchedule.get_scheduled_relays_ids()
    for group in all_groups:
        group_output = []

        for relay in group.relays:
            relay_output = {}
            relay_output['id'] = relay.id
            relay_output['ip_address'] = relay.ip_address
            relay_output['device_name'] = relay.device_name
            relay_output['group_id'] = relay.group_id
            relay_output['status'] = relay_status(relay.ip_address, relay.status_route)

            if relay.id in scheduled_relays:
                relay_output['controlled_by_scheduler'] = True
            else:
                relay_output['controlled_by_scheduler'] = False

            group_output.append(relay_output)
            del(relay_output)

        final_output.append({'group_id': group.id, 'relays': group_output, 'group_name': group.name})
        del(group_output)

    response = jsonify(final_output)
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

@api.route('/relays/<int:group_id>', methods=['GET'])
@token_required
def get_relays(group_id):
    relay_group = RelayGroup.query.get(group_id)
    scheduled_relays = TCPRelaySchedule.get_scheduled_relays_ids()
    output = []
    for relay in relay_group.relays:
        relay_output = {}
        relay_output['id'] = relay.id
        relay_output['ip_address'] = relay.ip_address
        relay_output['device_name'] = relay.device_name
        relay_output['group_id'] = relay.group_id
        relay_output['status'] = relay_status(relay.ip_address, relay.status_route) 

        if relay.id in scheduled_relays:
            relay_output['controlled_by_scheduler'] = True
        else:
            relay_output['controlled_by_scheduler'] = False

        output.append(relay_output)

    return jsonify({ 'group_id': output })

@api.route('/relay/<int:relay_id>', methods=['GET'])
@token_required
def get_relay_by_id(relay_id):
    relay = TCPRelayDevice.query.get(relay_id)
    scheduled_relays = TCPRelaySchedule.get_scheduled_relays_ids()

    output = {}
    output['id'] = relay.id
    output['ip_address'] = relay.ip_address
    output['device_name'] = relay.device_name
    output['group_id'] = relay.group_id
    output['status'] = relay_status(relay.ip_address, relay.status_route) 

    if relay.id in scheduled_relays:
        relay_output['controlled_by_scheduler'] = True
    else:
        relay_output['controlled_by_scheduler'] = False

    return jsonify({ 'relay': output })

@api.route('/relay/<int:relay_id>/<string:action>', methods=['GET'])
@token_required
def relay_control(relay_id, action):
    relay = TCPRelayDevice.query.get(relay_id)
    try:
        if action != 'on' or action != 'off':
            response = jsonify({ 'result': 400, 'message': 'Error: Invalid action command.' })
            response.headers.add('Access-Control-Allow-Origin', '*')
            return response

        relay_control_(relay_id, action)

        response = jsonify({ 'result': 200, 'message': 'Done.'})
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response

    except:
        response = jsonify({ 'result': 400, 'message': 'Error: Invalid host.'})
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response

def relay_control_(relay_id, action):
    try:
        relay = TCPRelayDevice.query.get(relay_id)
        if action == 'on':
            getJSON('http://' + relay.ip_address + '/' + relay.control_route + '=ON')
        elif action == 'off':
            getJSON('http://' + relay.ip_address + '/' + relay.control_route + '=OFF')
    except Exception as e:
        print(e)

def relay_status(ip_addr, status_route):
    try:
        response = getJSON('http://' + ip_addr + '/' + status_route, timeout=0.05)
        if response['state'] == 'on':
            return False
        else:
            return True
    except:
        return False