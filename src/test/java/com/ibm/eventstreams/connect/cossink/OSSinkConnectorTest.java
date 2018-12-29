package com.ibm.eventstreams.connect.cossink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.sink.SinkTask;
import org.junit.Test;

public class OSSinkConnectorTest {

    // Test that the connector returns the version number encoded in its version
    // constant.
    @Test
    public void version() {
        OSSinkConnector sc = new OSSinkConnector();
        assertEquals(OSSinkConnector.VERSION, sc.version());
    }

    // Test that the connector returns a task class that implements SinkTask.
    @Test
    public void taskClass() {
        OSSinkConnector sc = new OSSinkConnector();
        Class<?> clazz = sc.taskClass();
        assertTrue(SinkTask.class.isAssignableFrom(clazz));
    }

    // Verify that the config passed in to the connector's start() method is
    // propagated to the task configs returned by taskConfigs()
    @Test
    public void configPropagated() {
        Map<String, String> config = new HashMap<>();
        config.put("test.key", "test.value");

        OSSinkConnector sc = new OSSinkConnector();
        sc.start(config);
        List<Map<String, String>> taskConfigs = sc.taskConfigs(1);

        assertEquals(1, taskConfigs.size());
        assertEquals(config, taskConfigs.get(0));
    }

    // taskConfigs should always return the requested number of config maps
    // entries in the list it returns.
    @Test
    public void taskConfigsReturnsRequestedNumberOfConfigs() {
        OSSinkConnector sc = new OSSinkConnector();
        sc.start(new HashMap<>());

        for (int i=0; i < 10; i++) {
            assertEquals(i, sc.taskConfigs(i).size());
        }
    }
}