#!/bin/bash

echo "Creating dummy influx database '[name:dummysensordata]'"
curl -i -XPOST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE dummysensordata"

echo "Create 'Machine [id:machine-alpha]' entity"
curl -iX POST \
    'http://localhost:1026/v2/entities' \
    -H 'Content-Type: application/json' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /' \
    -d '
{
    "id": "urn:ngsi-ld:Machine:machine-alpha",
    "type": "Machine",
    "name": {
        "type": "Text",
        "value": "machine-alpha"
    }
}'

echo "Create 'Sensor [id:435282J]' entity"
curl -iX POST \
    'http://localhost:1026/v2/entities' \
    -H 'Content-Type: application/json' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /' \
    -d '
{
    "id": "urn:ngsi-ld:Sensor:435282J",
    "type": "Sensor",
    "description": {
        "type": "Text",
        "value": "Temperature sensor"
    },
    "refMachine": {
        "type": "Relationship",
        "value": "urn:ngsi-ld:Machine:machine-alpha"
    }
}'

echo "Create 'Sensor [id:777434B]' entity"
curl -iX POST \
    'http://localhost:1026/v2/entities' \
    -H 'Content-Type: application/json' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /' \
    -d '
{
    "id": "urn:ngsi-ld:Sensor:777434B",
    "type": "Sensor",
    "description": {
        "type": "Text",
        "value": "Temperature sensor"
    },
    "temperature": {
        "type": "Number",
        "value": 0.0
    },
    "refMachine": {
        "type": "Relationship",
        "value": "urn:ngsi-ld:Machine:machine-alpha"
    }
}'

echo "Register data provider for 'Sensor [id:435282J]'"
curl -iX POST \
    'http://localhost:1026/v2/registrations' \
    -H 'Content-Type: application/json' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /' \
    -d '
{
    "description": "Dummy Temperature Values",
    "dataProvided": {
        "entities": [
            {
                "id": "urn:ngsi-ld:Sensor:435282J",
                "type": "Sensor"
            }
        ],
        "attrs": [
            "temperature"
        ]
    },
    "provider": {
        "http": {
            "url": "http://influx-orion-adpater:8081/v1/temperature"
        },
        "legacyForwarding": true
    }
}'

echo "Subscribe generic-orion-consumer to temperature changes of 'Sensor [id:777434B]'"
curl -iX POST \
    'localhost:1026/v2/subscriptions' \
    -H 'Content-Type: application/json' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /' \
    -d '
{
    "description": "A subscription to get info about Sensor 777434B",
    "subject": {
        "entities": [
            {
                "id": "urn:ngsi-ld:Sensor:777434B",
                "type": "Sensor"
            }
        ],
        "condition": {
            "attrs": [
                "temperature"
            ]
        }
    },
    "notification": {
        "http": {
            "url": "http://generic-orion-consumer:8082/v1/subscription/temperature/changed"
        },
        "attrs": [
            "temperature"
        ],
        "attrsFormat": "legacy"
    },
    "throttling": 5
}'

echo "Subscribe sth-comet to temperature changes of 'Sensor [id:777434B]'"
curl -iX POST \
    'localhost:1026/v2/subscriptions' \
    -H 'Content-Type: application/json' \
    -H 'FIWARE-Service: default' \
    -H 'FIWARE-ServicePath: /' \
    -d '
{
    "description": "A subscription to get info about Sensor 777434B",
    "subject": {
        "entities": [
            {
                "id": "urn:ngsi-ld:Sensor:777434B",
                "type": "Sensor"
            }
        ],
        "condition": {
            "attrs": [
                "temperature"
            ]
        }
    },
    "notification": {
        "http": {
            "url": "http://fiware-sth-comet:8666/notify"
        },
        "attrs": [
            "temperature"
        ],
        "attrsFormat": "legacy"
    },
    "throttling": 5
}'