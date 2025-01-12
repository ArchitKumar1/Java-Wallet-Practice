recreate_up:
	docker-compose up --build --force-recreate

up:
	make createFatJar
	docker-compose up

down:
	docker-compose down

restart:
	make down
	make up


restart_with_recreate:
	make down
	make recreate_up


access-container:
	docker exec -it d054031b1b67 /bin/sh


rebuild:
	./gradlew clean build  --warning-mode=all


createFatJar:
	./gradlew clean
	./gradlew task fatJar
