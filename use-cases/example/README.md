# Basic Provider - Consumer example

This example is based on the default [Trusted Connector example](https://github.com/industrial-data-space/trusted-connector/tree/develop/examples/example-idscp).

## Scenario

Factory sensor data are stored in an influx database, which have to be transferred to a third party for analysis/processing utilizing the IDS ecosystem.

1. A ```data-provider``` Data-App is polling the influx database on a fixed interval.
2. The extracted data are published on an internal mqtt topic.
3. A Trusted Connector instance (provider) is listening for data on the mqtt topic.
4. When data arrive the provider send them to a predefined Trusted Connector (consumer) through the IDS protocol.
5. The consumer receives the data and publish them on an internal mqtt topic.
6. Data-Apps which are subscribed the mqtt topic are able to receive, analyse and process the data.
* (For the sake of the example consumer Data-App is displaying the received data on http://localhost:8081/debug endpoint)

## How to Run

```docker-compose -f docker-compose-consumer.yaml -f docker-compose-provider.yaml -f docker-compose-ttp.yaml up```

## Useful Endpoints

* ```http://localhost:8282``` - provider Trusted Connector user interface
* ```http://localhost:8181``` - consumer Trusted Connector user interface
* ```http://localhost:8082/provider/start/influx``` - start the data-provider Data-App polling
* ```http://localhost:8081``` - display the received data

## Sample Data Production

Running the following command, you can create the pre-configured database
```
curl -i -XPOST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE influx_database"
```

Additionally running the ```./utils/influx-fake.sh``` bash script you can produce sample/fake data.
