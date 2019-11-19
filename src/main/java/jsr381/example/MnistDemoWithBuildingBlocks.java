package jsr381.example;

import jsr381.example.util.DataSetExamples;

import javax.visrec.ml.ClassificationException;
import javax.visrec.ml.ClassifierCreationException;
import javax.visrec.ml.classification.ImageClassifier;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Hand written digit recognition using MNIST data set - image classification hello world.
 *
 * @author Zoran Sevarac <zoran.sevarac@deepnetts.com>
 */
public class MnistDemoWithBuildingBlocks {

    public static void main(String[] args) throws IOException, ClassifierCreationException, ClassificationException {
        // Download the dataset and calculate how much time it took
        long start = System.currentTimeMillis();
        DataSetExamples.MnistDataSet dataSet = DataSetExamples.getMnistDataSet();
        System.out.println(String.format("Took %d milliseconds to download the MNIST dataset", System.currentTimeMillis() - start));

        // Configuration to train the model
        ImageClassifier classifier = ImageClassifier.builder()
                .imageHeight(28)
                .imageWidth(28)
                .labelsFile(dataSet.getLabelsFile())
                .trainingsFile(dataSet.getTrainingFile())
                .networkArchitecture(dataSet.getNetworkArchitectureFile())
                .modelFile(new File("mnist.dnet"))
                .maxError(1.4f)
                .maxEpochs(100)
                .learningRate(0.01f)
                .build();

        // Get input imgae from resources and use the classifier.
        URL input = MnistDemoWithBuildingBlocks.class.getClassLoader().getResource("00060.png");
        if (input == null) {
            throw new IOException("Input file not found in resources");
        }
        Map<String, Float> results = classifier.classify(new File(input.getFile()));

        // Print the outcome
        System.out.println(results);
    }
}
