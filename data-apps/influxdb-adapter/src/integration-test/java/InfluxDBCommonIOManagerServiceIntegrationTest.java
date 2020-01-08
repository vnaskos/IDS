import com.vnaskos.ids.adapter.influxdb.*;
import com.vnaskos.ids.adapter.influxdb.service.InfluxDBCommonIOManagerService;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.vnaskos.ids.adapter.influxdb.service.InfluxDBCommonIOManagerService.NO_PASSWORD;
import static com.vnaskos.ids.adapter.influxdb.service.InfluxDBCommonIOManagerService.NO_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

public class InfluxDBCommonIOManagerServiceIntegrationTest {

    private static final String INFLUX_DB_URL = "http://localhost:8086";
    private static final String TEST_DB_NAME = "INFLUX_ADAPTER_INTEGRATION_TEST_DB";
    private static final String TEST_MEASUREMENT_NAME = "integration_test_measurement";
    private static final int ONE_RESULT = 1;
    private static final Field A_FIELD = new Field("test_field", "a_value");
    private static final Field ANOTHER_FIELD = new Field("another_test_field", "a_value");
    private static final Tag A_TAG = new Tag("test_tag", "a_tag");
    private static final Tag ANOTHER_TAG = new Tag("another_test_tag", "a_tag");
    private static final Tags NO_TAGS = new Tags();

    private final InfluxDBCommonIOManagerService readWriteManagerService = new InfluxDBCommonIOManagerService();

    private InfluxDB internalInfluxDB;

    @Before
    public void setUp() throws InterruptedException {
        this.internalInfluxDB = connectToInfluxDB();
        this.internalInfluxDB.query(new Query("CREATE DATABASE " + TEST_DB_NAME));
    }

    @After
    public void cleanup(){
        this.internalInfluxDB.query(new Query("DROP DATABASE " + TEST_DB_NAME));
    }

    @Test
    public void searchingForAMeasurementThatDoesNotExistInTheDatabaseShouldReturnAnEmptyResult() {
        readWriteManagerService.connect(
                INFLUX_DB_URL, NO_USERNAME, NO_PASSWORD);

        List<TemplatePoint> results = readWriteManagerService.readLatestMeasurements(
                ONE_RESULT, TEST_DB_NAME, TEST_MEASUREMENT_NAME);

        assertThat(results).isEmpty();
    }

    @Test
    public void readAMeasurementWithFieldsAndTagsFromTheDatabase() {
        readWriteManagerService.connect(
                INFLUX_DB_URL, NO_USERNAME, NO_PASSWORD);

        writeAMeasurementWithFieldsAndTagsToTheDatabase();

        List<TemplatePoint> results = readWriteManagerService.readLatestMeasurements(
                ONE_RESULT, TEST_DB_NAME, TEST_MEASUREMENT_NAME);

        assertThat(results).hasSize(ONE_RESULT);
        TemplatePoint resultPoint = results.get(0);

        assertThat(resultPoint.getMeasurementName()).isEqualTo(TEST_MEASUREMENT_NAME);
        assertThat(resultPoint.getFields()).contains(A_FIELD, ANOTHER_FIELD);
        assertThat(resultPoint.getTags()).contains(A_TAG, ANOTHER_TAG);
        assertThat(resultPoint.getTime()).isLessThan(System.currentTimeMillis());
    }

    @Test
    public void readAMeasurementWithoutTagsFromTheDatabase() {
        readWriteManagerService.connect(
                INFLUX_DB_URL, NO_USERNAME, NO_PASSWORD);

        writeAMeasurementWithoutTagsToTheDatabase();

        List<TemplatePoint> results = readWriteManagerService.readLatestMeasurements(
                ONE_RESULT, TEST_DB_NAME, TEST_MEASUREMENT_NAME);

        assertThat(results).hasSize(ONE_RESULT);
        TemplatePoint resultPoint = results.get(0);

        assertThat(resultPoint.getMeasurementName()).isEqualTo(TEST_MEASUREMENT_NAME);
        assertThat(resultPoint.getFields()).containsExactly(A_FIELD);
        assertThat(resultPoint.getTags()).isEmpty();
        assertThat(resultPoint.getTime()).isLessThan(System.currentTimeMillis());
    }

    private InfluxDB connectToInfluxDB() throws InterruptedException {
        InfluxDB influxDB = InfluxDBFactory.connect(INFLUX_DB_URL);

        boolean influxDBstarted = false;
        do {
            Pong response;
            try {
                response = influxDB.ping();
                if (response.isGood()) {
                    influxDBstarted = true;
                }
            } catch (Exception e) {
                // NOOP intentional
                e.printStackTrace();
            }
            Thread.sleep(100L);
        } while (!influxDBstarted);

        return influxDB;
    }

    private void writeAMeasurementWithFieldsAndTagsToTheDatabase() {
        final Fields fields = Fields.of(A_FIELD, ANOTHER_FIELD);
        final Tags tags = Tags.of(A_TAG, ANOTHER_TAG);

        final TemplatePoint aMeasurement = new TemplatePoint(
                TEST_MEASUREMENT_NAME,
                System.currentTimeMillis(),
                fields, tags);

        readWriteManagerService.write(TEST_DB_NAME, aMeasurement);
    }

    private void writeAMeasurementWithoutTagsToTheDatabase() {
        final Fields fields = Fields.of(A_FIELD);

        final TemplatePoint aMeasurementWithoutFieldsOrTags = new TemplatePoint(
                TEST_MEASUREMENT_NAME,
                System.currentTimeMillis(),
                fields, NO_TAGS);

        readWriteManagerService.write(TEST_DB_NAME, aMeasurementWithoutFieldsOrTags);
    }
}
