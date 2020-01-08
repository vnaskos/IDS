Setup Orion entities
====

In my example I have machines, which have sensors, which display temperature values. 
The temperature data for sensor 435282J are provided by a third party application (only entities are stored in orion mongo DB).

_Note: The ID's are randomly chosen!_

##What config.sh does:
* Create the __Machine__ entity 
* Create the __Sensor__ entities and their relationship with the machine
* Register data provider for sensor 435282J
* Subscribe generic-orion-consumer and sth-comet to temperature changes of Sensor 777434B

##Useful commands

* Get the sensor entry (display the temperature)
```console
curl -X GET \
    'http://localhost:1026/v2/entities/urn:ngsi-ld:Sensor:435282J/?type=Sensor&options=keyValues' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /'
```

* List the sensors of a machine
```console
curl -X GET \
    'http://localhost:1026/v2/entities/?q=refMachine==urn:ngsi-ld:Machine:machine-alpha&options=count&attrs=type&type=Sensor' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /'
```

* Display all registered providers
```console
curl -X GET \
    'http://localhost:1026/v2/registrations' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /'
```

* Delete a provider
```console
curl -iX DELETE \
    'http://localhost:1026/v2/registrations/5c8a68916f61cb10223d4bf3' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /'
```

* Update sensor
```console
curl -iX PATCH \
    'http://localhost:1026/v2/entities/urn:ngsi-ld:Sensor:435282J/attrs' \
    -H 'Content-Type: application/json' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /' \
    -d '
{
    "description": {
        "type": "Text",
        "value": "Temperature sensor updated"
    }
}'
```

* Update sensor value to 23 degrees
```console
curl -iX PUT \
    'http://localhost:1026/v2/entities/urn:ngsi-ld:Sensor:777434B/attrs/temperature/value' \
    -H 'Content-Type: text/plain' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /' \
    -d '23.0'
```

* Query sth-comet for raw historical temperature data of sensor 777434B
```console
curl -iX GET \
    'http://localhost:8666/STH/v1/contextEntities/type/Sensor/id/urn:ngsi-ld:Sensor:777434B/attributes/temperature?hLimit=3&hOffset=0&dateFrom=2019-01-01T00:00:00.000Z&dateTo=2019-06-31T23:59:59.999Z' \
    -H 'Content-Type: application/json' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /'
```

* Query sth-comet for aggregated historical temperature data of sensor 777434B (max temperature per day)
```console
curl -iX GET \
    'http://localhost:8666/STH/v1/contextEntities/type/Sensor/id/urn:ngsi-ld:Sensor:777434B/attributes/temperature?aggrMethod=max&aggrPeriod=day&dateFrom=2019-01-01T00:00:00.000Z&dateTo=2019-06-06T23:59:59.999Z' \
    -H 'Content-Type: application/json' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /'
```