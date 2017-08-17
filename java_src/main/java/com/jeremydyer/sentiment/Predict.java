package com.jeremydyer.sentiment;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * Created by jdyer on 3/18/16.
 */
public class Predict {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("Usage: " + Predict.class.getSimpleName() + " <modelPath>");
            System.exit(-1);
        }

        Predict predict = new Predict();
        predict.run(args[0]);
    }

    public void run(String modelPath) {

        System.out.println("Initializing Spark Streaming Context ...");
        SparkConf sc = new SparkConf()
                .setAppName(this.getClass().getName());

        JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(5000L));

        System.out.println("Initializing the Naive Bayes model...");
        NaiveBayesModel model = NaiveBayesModel.load(ssc.sparkContext().sc(), modelPath);

        //JavaRDD sentiments = model.predict(null);

        //sentiments
    }
}
