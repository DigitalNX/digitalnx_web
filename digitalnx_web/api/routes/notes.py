from . import *

@api.route('/notes/')
@token_required
def get_all_notes():
    notes = Note.query.all()

    output = []
    for note in notes:
        note_output = {}
        note_output['id'] = note.id
        note_output['content'] = note.content
        note_output['title'] = note.title
        note_output['color'] = note.color
        output.append(note_output)
        del note_output

    response = jsonify(output)
    response.headers.add('Access-Control-Allow-Origin', '*')

    return response

@api.route('/note/<int:note_id>')
@token_required
def get_note(note_id):
    note = Note.query.get(note_id)
    
    output = []
    note_output = {}
    note_output['id'] = note.id
    note_output['content'] = note.content
    note_output['title'] = note.title
    note_output['color'] = note.color
    output.append(note_output)
    response = jsonify(output)
    response.headers.add('Access-Control-Allow-Origin', '*')

    return response

@api.route('/note/new', methods=['POST'])
@token_required
def new_note():
    data = request.get_json()
    note = Note(title=data['title'], content=data['content'])
    db_session.add(note)
    db_session.commit()

    response = jsonify({'result': 200, 'message': 'Note added.'})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

@api.route('/note/<int:note_id>/update', methods=['POST'])
@token_required
def update_note(note_id):
    data = request.get_json()
    note = Note.query.get(note_id)
    note.title = data['title']
    note.content = data['content']
    db_session.commit()

    response = jsonify({'result': 200, 'message': 'Note updated.'})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

@api.route('/note/<int:note_id>/remove', methods=['GET'])
@token_required
def remove_note(note_id):
    response = []
    try:
        note = Note.query.get(note_id)
        db_session.delete(note)
        db_session.commit()
        response = jsonify({'result': 200, 'message': 'Note successfully removed.'})

        layout_items = DashboardLayout.query.all()
        for item in layout_items:
            if item.item == 'Note' and item.note_id == note_id:
                itemToDelete = DashboardLayout.query.get(item.id)
                db_session.delete(itemToDelete)
                db_session.commit()

    except:
        response = jsonify({'result': 400, 'message': 'Error removing note.'})
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response, 400
    
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response, 200