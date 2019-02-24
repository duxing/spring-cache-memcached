CUR_DIR := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
REPO_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))
DIR_NAME := $(shell basename $(REPO_DIR))
SERVICE_NAME := application-web

DOCKER_COMPOSE = ENVIRONMENT=$(ENVIRONMENT) \
 docker-compose -f ./docker-compose.yml

GRADLE := docker run --rm -it -u root -v $(CUR_DIR):/app -w /app gradle:jdk8-alpine gradle

.PHONY: docker_gradle_build
docker_gradle_build:
	$(GRADLE) -q build

.PHONY: docker_build_web
docker_build_web:
	$(DOCKER_COMPOSE) build --force-rm --no-cache $(SERVICE_NAME)

.PHONY: docker_up
docker_up:
	$(DOCKER_COMPOSE) up -d --remove-orphans

.PHONY: docker_down
docker_down:
	$(DOCKER_COMPOSE) down --remove-orphans --volumes

.PHONY: docker_logs
docker_logs:
	@docker logs -f $(shell docker ps -q -f "name=$(DIR_NAME)_$(SERVICE_NAME)")
