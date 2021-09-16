install_server:
	cd backend && ./mvnw install

install_client:
	cd client && npm install && cd ..

install:
	install_server install_client init

run:
	./start.sh
