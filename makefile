install_server:
	pip3 install flask flask-cors sqlalchemy pyjwt

install_client:
	cd digitalnx_web/client && npm install && cd ../..

init:
	python3 generate_token.py
	python3 config-setup.py
	python3 test/db_populate.py

install: install_server install_client init

run:
	python3 run.py

generate_secret_key:
	python3 generate_token.py