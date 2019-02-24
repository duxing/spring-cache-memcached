# spring-cache-memcached-example
Example project for using memcached as spring cache storage

## Requirement
 - `docker`
 - `make`

## Instructions

### Build-time
 - Compile with `gradle`: `make docker_gradle_build`
 - Build docker image: `make docker_build_web`

### Runtime
 - Launch service: `make docker_up`
 - Terminate service: `make docker_stop`
 - Tail logs: `make docker_logs` (use `Ctrl` + `C` to stop tailing)
 - Access service (using `ping` API as an example): `curl -s -X 'GET' localhost:8080/app/ping`
