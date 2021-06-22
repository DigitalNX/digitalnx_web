from . import *
from digitalnx_web.api.schedules import run_relay_schedule
from datetime import datetime

@api.route('/schedules/relay/all', methods=['GET'])
@token_required
def get_relay_schedules():
    schedules = TCPRelaySchedule.upcomingExecutions()
    return jsonify({ 'result': 200, 'schedules': schedules} )

@api.route('/schedules/relay/<int:relay_id>', methods=['GET'])
@token_required
def get_relay_schedule(relay_id):
    schedules = TCPRelaySchedule.upcomingExecutions()
    for s in schedules:
        if s['relay_id'] == relay_id:
            return jsonify({ 'result': 200, 'schedule': schedules })
    return jsonify({ 'result': 404, 'message': 'Schedule for the relay not found!' })

@api.route('/schedules/relay/<int:relay_id>/add', methods=['POST'])
@token_required
def add_schedule(relay_id):
    data = request.get_json()

    if len(TCPRelaySchedule.query.filter_by(relay_id=relay_id).all()) == 0:
        try:
            if data['startingTime'] == '':
                currentTimestamp = datetime.utcnow().timestamp()
            else:
                currentTimestamp = data['startingTime']
            newSchedule = TCPRelaySchedule(
                                        relay_id,
                                        int(currentTimestamp), 
                                        data['period'],
                                        data['execution_duration'],
                                        data['description'],
                                        data['enabled'])

            # ORM's signature
            # def __init__(self, relay_id, startingTime, period, executionDuration, description="", enabled=True)

            db_session.add(newSchedule)
            db_session.commit()
            # Running the scheduling process in the background:
            run_relay_schedule(relay_id)
            return jsonify({'result': 200, 'message': 'Schedule has been successfully added.'})
        except:
            return jsonify({'result': 400, 'message': 'Error adding schedule!'})
    else:
        try:
            if data['startingTime'] == '':
                currentTimestamp = datetime.utcnow().timestamp()
            else:
                currentTimestamp = data['startingTime']
            TCPRelaySchedule.query.filter_by(relay_id=relay_id).update(
                {
                    'relay_id': relay_id,
                    'startingTime': int(currentTimestamp), 
                    'period': data['period'],
                    'executionDuration': data['execution_duration'],
                    'description': data['description'],
                    'enabled': data['enabled']
                }
            )
            db_session.commit()
            run_relay_schedule(relay_id)
            return jsonify({'result': 200, 'message': 'Schedule has been successfully added.'})

        except:
            return jsonify({'result': 400, 'message': 'Error updating schedule!'})

@api.route('/schedules/relay/<int:relay_id>/remove', methods=['GET'])
@token_required
def remove_schedule(relay_id):
    try:
        schedule = TCPRelaySchedule.query.filter_by(relay_id=relay_id).first()
        db_session.delete(schedule)
        db_session.commit()
        return jsonify({'result': 200, 'message': 'Schedule has been successfully deleted.'})

    except:
        return jsonify({'result': 400, 'message': 'Error deleting schedule!'})