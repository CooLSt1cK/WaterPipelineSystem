package com.aleksieienko.water.pipeline.system.parser;

import com.aleksieienko.water.pipeline.system.pipeline.Graph;
import com.aleksieienko.water.pipeline.system.pipeline.Question;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Parser {
    Object parse(File file) throws IOException;
    void write(File file, List<Question> questions, Graph graph) throws IOException;
}
