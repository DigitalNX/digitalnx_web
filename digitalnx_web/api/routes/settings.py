from . import *

@api.route('/settings', methods=['GET'])
@token_required
def settings():
	settings = Settings.query.all()[0]
	output = {}
	output['ver'] = settings.version
	output['lang'] = settings.UILanguage.name
	output['sec_mode'] = settings.securityMode.name
	response = jsonify(output)

	response.headers.add('Access-Control-Allow-Origin', '*')
	return response

@api.route('/settings/change', methods=['POST'])
@token_required
def changeSettings():
	try:
		data = request.get_json()
		settings = Settings.query.all()[0]
		settings.securityMode = data['sec_mode']
		db_session.commit()
		return jsonify({'result': 200, 'message': 'Settings updated.'})
	except:
		return jsonify({'result': 400, 'message': 'Error updating the settings.'})