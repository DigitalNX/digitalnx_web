from . import *

@api.route('/widgets/', methods=['GET'])
@token_required
def get_all_widgets():
    # The list bellow can be defnined globally on package level and the code will work dynamically
    widget_orms = [{'name': 'weather', 'orm': WeatherWidget},
                   {'name': 'relay', 'orm': RelayGroup},
                   {'name': 'sensor', 'orm': SensorGroup},
                   {'name': 'note', 'orm': Note},
                   {'name': 'info', 'orm': Info}]
    widgets = []
    for widget_orm in widget_orms:
        widgets.append({'name' : widget_orm['name'],
                        'objects': widget_orm['orm'].query.all()})

    output = []
    for widget in widgets:
        for obj in widget['objects']:
            output.append({'name': widget['name'], 'id':obj.id})

    response = jsonify(output)
    response.headers.add('Access-Control-Allow-Origin', '*')

    return response

# Same as widgets_orm list, this list can be defined on package level
registered_widgets = [{'name': 'relay', 'item': 'RelayGroup', 'id_name': 'relay_group_id'},
                      {'name': 'sensor', 'item': 'SensorGroup', 'id_name': 'sensor_group_id'},
                      {'name': 'weather', 'item': 'WeatherWidget', 'id_name': 'weather_widget_id'},
                      {'name': 'note', 'item': 'Note', 'id_name': 'note_id'},
                      {'name': 'info', 'item': 'Info', 'id_name': 'info_id'}]

@api.route('/layout/new', methods=['POST'])
@token_required
def new_layout_item():
    data = request.get_json()
    for widget in registered_widgets:
        if widget['name'] == data['name']:
            dashboard_item = DashboardLayout(item=widget['item'], **{widget['id_name']: data['id']})
            db_session.add(dashboard_item)
            db_session.commit()
            response = jsonify({'result': 200, 'message': 'Item added successfully.'})
        else:
            response = jsonify({'result': 400, 'message': 'Error: Request.'})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

@api.route('/layout/delete', methods=['POST'])
@token_required
def delete_layout_item():
    data = request.get_json()
    try:
        for widget in registered_widgets:
            if widget['name'] == data['name']:
                dashboard_item = DashboardLayout.query.filter_by(item=widget['item'], **{widget['id_name']: data['id']}).first()
                db_session.delete(dashboard_item)
                db_session.commit()
                response = jsonify({'result': 200, 'message': 'Item deleted successfully.'})
    except:
        response = jsonify({'result': 200, 'message': 'Error: Request.'})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

@api.route('/layout/', methods=['GET'])
@token_required
def get_layout_items():
    output = []
    widgets = sorted(DashboardLayout.query.all(), key=lambda item: item.id)
    for i in widgets:
        if i.item == 'RelayGroup':
            relay_output = {}
            relay = RelayGroup.query.get(i.relay_group_id)
            relay_output['name'] = 'relay'
            relay_output['id'] = relay.id
            output.append(relay_output)
            del(relay_output)

        if i.item == 'SensorGroup':
            sensor_output = {}
            sensor = SensorGroup.query.get(i.sensor_group_id)
            sensor_output['name'] = 'sensor'
            sensor_output['id'] = sensor.id
            output.append(sensor_output)
            del(sensor_output)

        elif i.item == 'WeatherWidget':
            weather_output = {}
            weather = WeatherWidget.query.get(i.weather_widget_id)
            weather_output['name'] = 'weather'
            weather_output['id'] = weather.id
            output.append(weather_output)
            del(weather_output)

        elif i.item == 'Note':
            note_output = {}
            note = Note.query.get(i.note_id)
            note_output['name'] = 'note'
            note_output['id'] = note.id
            output.append(note_output)
            del(note_output)

        elif i.item == 'Info':
            info_output = {}
            info = Info.query.get(i.info_id)
            info_output['name'] = 'info'
            info_output['id'] = info.id
            output.append(info_output)
            del(info_output)

    response = jsonify(output)
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response