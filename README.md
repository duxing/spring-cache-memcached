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

## Example

### Using spring cache
 - Build and run application with commands above
 - Run `curl -s -X 'GET' localhost:8080/app/data/<dataId>` to create a cache entry
 - Run the same query again (before the expiration window) to verify response is loaded from cache (see logs from `make docker_logs`)
 - Query `memcached` to see the cache. With the default configuration and using `1234` as the `dataId`: `echo get cache0:1234 | nc localhost 11211`
