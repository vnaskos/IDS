while true; do
    curl -i -XPOST 'http://localhost:8086/write?db=influx_database' --data-binary 'temperature,host=server01,region=us-west value=0.64'
    sleep 1
done
