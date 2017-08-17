package com.jeremydyer;

import org.apache.nifi.remote.Transaction;
import org.apache.nifi.remote.TransferDirection;
import org.apache.nifi.remote.client.SiteToSiteClient;
import org.apache.nifi.remote.client.SiteToSiteClientConfig;
import org.apache.nifi.spark.NiFiDataPacket;
import org.apache.nifi.spark.NiFiReceiver;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Created by jdyer on 3/15/16.
 */
public class Main {

    private static SiteToSiteClient dataToSparkClient = null;

    public static void main(String[] args) throws IOException {

        SiteToSiteClientConfig dataToSparkConfig = new SiteToSiteClient.Builder()
                .url("http://10.0.1.28:8080/nifi")
                .portName("Data From Spark")
                .buildConfig();

        dataToSparkClient = new SiteToSiteClient.Builder().fromConfig(dataToSparkConfig).build();

        final Transaction transaction = dataToSparkClient.createTransaction(TransferDirection.SEND);
        transaction.send("Jeremy Hello".getBytes(), new HashMap<String, String>());
        transaction.confirm();
        transaction.complete();


        SiteToSiteClientConfig dataFromSparkConfig = new SiteToSiteClient.Builder()
                .url("http://10.0.1.28:8080/nifi")
                .portName("Data For Spark")
                .buildConfig();

        SparkConf sparkConf = new SparkConf().setAppName("NiFi-Spark Streaming example");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, new Duration(10000L));
        JavaReceiverInputDStream packetStream = ssc.receiverStream(new NiFiReceiver(dataFromSparkConfig, StorageLevel.MEMORY_ONLY()));

        JavaDStream text = packetStream.map(new Function<NiFiDataPacket, String>() {
            public String call(final NiFiDataPacket dataPacket) throws Exception {
                return new String(dataPacket.getContent(), StandardCharsets.UTF_8);
            }
        });

        text.print();

        System.out.println("Spark Streaming successfully initialized");
        ssc.start();
        ssc.awaitTermination();
    }
}
