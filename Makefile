start:
	@echo Starting database container...
	@docker-compose up -d
	@echo Database up!

stop:
	@echo Stopping database container...
	@docker-compose down
	@echo All done!

test:
	@echo Running tests...
	@mvn clean verify
	@echo All tests passed!

testc:
	@echo Running tests...
	@mvn clean verify
	@echo All tests passed!
	@echo Opening test report...
	@xdg-open target/site/jacoco/index.html || open target/site/jacoco/index.html || start target/site/jacoco/index.html