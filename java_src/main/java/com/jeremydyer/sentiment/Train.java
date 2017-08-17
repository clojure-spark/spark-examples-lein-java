package com.jeremydyer.sentiment;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.io.IOException;
import java.util.List;

/**
 * Created by jdyer on 3/18/16.
 */
public class Train {

    private int numFeatures = 1000;
    private HashingTF tf = new HashingTF(numFeatures);

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Usage : " + Train.class.getSimpleName() + " <training file>");
            System.exit(-1);
        }

        Train train = new Train();
        train.run(args[0]);

    }

    public void run(String trainingDataPath) {
        SparkConf sparkConf = new SparkConf()
                .setAppName("Sentiment Analysis Trainer")
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        //Load the required Data
        Broadcast<String[]> stopWords = sc.broadcast(loadStopWords("src/main/resources/stopwords.txt"));
        System.out.println("Loading Training Data file : " + trainingDataPath);
        JavaRDD<String> allData = sc.textFile(trainingDataPath);

        System.out.println("\n\n*********** Training **************\n\n");

//        //Prep the raw data for training
//        JavaRDD<LabeledPoint> training_labeled = allData.map(new Function<String, Object>() {
//            public Object call(String v1) throws Exception {
//                return null;
//            }
//        });
//
//        NaiveBayes.train(training_labeled, 1.0);
//
//        System.out.println("\n\n*********** Testing **************\n\n");
//
//        sc.stop();
//        System.out.println("\n\n********* Stopped Spark Context succesfully, exiting ********");
    }

//    private String[] toLabels(String line) {
//        String[] parts = StringUtils.split(line, ",");
//
//    }

    private void featurize(String line) {
        //tf.transform
    }

    /**
     * Loads the stopwords using the the training
     * @param path
     */
    private String[] loadStopWords(String path) {
        try {
            List<String> li =  IOUtils.readLines(Train.class.getResourceAsStream(path));
            String[] lines = new String[li.size()];
            for (int i = 0; i < li.size(); i++) {
                lines[i] = li.get(i);
            }
            return lines;
        } catch (IOException ioe) {
            System.err.println("Unable to find file at path: " + path);
            return null;
        }
    }
}
