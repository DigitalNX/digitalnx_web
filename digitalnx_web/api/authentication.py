from flask import jsonify, request, Blueprint, current_app
from .database import db_session
from .models import User
from datetime import datetime
import jwt

auth = Blueprint('auth', __name__, template_folder='templates', static_folder='static',
                static_url_path='/site/static')

def token_required(func):
    def wrap(*args, **kwargs):
        token = request.headers.get('token')
        if token == None:
            return jsonify({ 'result': 'Token is missing!'})
        try:
            data = jwt.decode(token, current_app.config['SECRET_KEY'])
            user = User.query.filter_by(username=data['username']).first()
            if user == None:
                return jsonify({
                    'result': 'Error: User not found!',
                    'authenticated': False
                })
            return func(*args, **kwargs)
        except jwt.ExpiredSignatureError:
            return jsonify({
                'result': 'Error: Token is expired!',
                'authenticated': False
            })
        except jwt.InvalidTokenError:
            return jsonify({
                'result': 'Error: Invalid token response!',
                'authenticated': False
            })
            
    wrap.__name__ = func.__name__
    return wrap

@auth.route('/register', methods=['POST'])
@token_required
def register():
    data = request.get_json()
    user = User(data['email'], data['password'])
    db_session.add(user)
    db_session.commit()
    return jsonify(user.to_dict())

@auth.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    try:
        user = User.authenticate(data['username'], data['password'])
        if user == None:
            return jsonify({ 'result': 401, 'message': 'Error: Invalid credentials', 'authenticated': False }), 401
        else:
            token = jwt.encode(
                {'username': data['username']},
                current_app.config['SECRET_KEY'])
            # TODO : grab version value from a global variable
            return jsonify({ 'result': 200, 'token': token.decode('UTF-8'), 'username': data['username'], 'version': 0.1 }), 200
    except:
        return jsonify({ 'result': 401, 'message': 'Invalid login request!'}), 401
