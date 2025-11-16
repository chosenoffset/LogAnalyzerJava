package org.example;

import org.example.aggregator.Aggregator;
import org.example.log.TestStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    static void main() {
        List<TestStructure> list = new ArrayList<>();
        Aggregator aggregator = new Aggregator();
        try (Stream<String> fileStream = Files.lines(Path.of("./test.log"))) {
            fileStream.forEach(line -> {
                String[] parts = line.split(" ");

                if (parts.length < 7) {
                    System.out.println("Invalid line: " + line);
                    return;
                }

                TestStructure testStructure = new TestStructure();
                testStructure.timestamp = parts[0];
                testStructure.level = parts[1];
                testStructure.source = parts[2];
                testStructure.verb = parts[3];
                testStructure.path = parts[4];
                testStructure.responseCode = parts[5];
                testStructure.duration = parts[6];
                if (parts.length == 8) {
                    testStructure.message = parts[7];
                }

                list.add(testStructure);
            }) ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        aggregator.AddAggregator("LOG_LINES", list.size());

        list.forEach(structure -> aggregator.AddAggregator(structure.level));
        list.forEach(structure -> aggregator.Increment(structure.level));

        list.forEach(structure -> aggregator.AddAggregator(structure.responseCode));
        list.forEach(structure -> aggregator.Increment(structure.responseCode));

        System.out.println(aggregator.PrintCounts());
    }
}
