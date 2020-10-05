package com.aleksieienko.water.pipeline.system.parser;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.aleksieienko.water.pipeline.system.pipeline.Graph;
import com.aleksieienko.water.pipeline.system.pipeline.Question;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVParser implements Parser{
    @Override
    public Object parse(File file) throws IOException {
        try(CSVReader reader = new CSVReader(new FileReader(file), ';')) {
            if (reader.readNext().length == 2) {
                return parseQuestions(reader);
            } else {
                return parseGraph(reader);
            }
        }
    }

    @Override
    public void write(File file, List<Question> questions, Graph graph) throws IOException {
        try(CSVWriter writer = new CSVWriter(new FileWriter(file), ';','\0')) {
            writer.writeNext(new String[]{"ROUTE EXISTS", "MIN LENGTH"});

            for(Question question : questions){

                String[] record = new String[2];
                Integer result = graph.shortestRouteWeight(question.getPointAId(),question.getPointBId());

                if(result != null){

                    record[0] = String.valueOf(true).toUpperCase();
                    record[1] = result.toString();
                } else {

                    record[0] = String.valueOf(false).toUpperCase();
                }

                writer.writeNext(record);
            }
        }
    }

    private Graph parseGraph(CSVReader reader) throws IOException {
        Graph graph = new Graph();
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            graph.addEdge(Integer.parseInt(nextLine[0]),Integer.parseInt(nextLine[1]),Integer.parseInt(nextLine[2]));
        }
        return graph;
    }

    private List<Question> parseQuestions(CSVReader reader){
        CsvToBean csv = new CsvToBean();
        List<Question> list = csv.parse(setColumnMapping(),reader);
        return list;
    }

    private ColumnPositionMappingStrategy setColumnMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Question.class);
        String[] columns = new String[] {"pointAId", "pointBId"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
