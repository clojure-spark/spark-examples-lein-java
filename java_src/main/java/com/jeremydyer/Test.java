package com.jeremydyer;

import org.apache.nifi.remote.Transaction;
import org.apache.nifi.remote.TransferDirection;
import org.apache.nifi.remote.client.SiteToSiteClient;
import org.apache.nifi.remote.client.SiteToSiteClientConfig;


import java.io.IOException;
import java.util.HashMap;

/**
 * Created by jdyer on 3/18/16.
 */
public class Test {

    private static SiteToSiteClient client;

    public static void main(String[] args) throws IOException {

        SiteToSiteClientConfig clientConfig = new SiteToSiteClient.Builder()
                .url("http://10.0.1.28:8080/nifi")
                .portName("Data From Spark")
                .buildConfig();

        client = new SiteToSiteClient.Builder().fromConfig(clientConfig).build();

        final Transaction transaction = client.createTransaction(TransferDirection.SEND);
        transaction.send("Jeremy Hello".getBytes(), new HashMap<String, String>());
        transaction.confirm();
        transaction.complete();

        System.out.println("Done testing");
    }
}
