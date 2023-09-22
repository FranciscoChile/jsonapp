package com.sonatafy.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sonatafy.exception.JsonException;
import com.sonatafy.json.JsonTool;
import com.sonatafy.model.ChangeType;
import com.sonatafy.model.ListUpdate;
import com.sonatafy.model.PropertyUpdate;


public class FileReaderUtil {
    
    private Logger logger = LoggerFactory.getLogger(FileReaderUtil.class);

    public void processExampleJson() throws JsonException, IOException {

        try { 

            JsonTool diffTool = new JsonTool();            
            InputStream is = getFileAsIOStream("properties.txt");
            
            try (InputStreamReader isr = new InputStreamReader(is); 
                BufferedReader br = new BufferedReader(isr);) 
            {
                String line;
                while ((line = br.readLine()) != null) {                    
                    List<ChangeType> list = diffTool.parserJson(line);

                    for (ChangeType a : list) {

                        if (a instanceof PropertyUpdate) {
                            StringBuilder b = new StringBuilder("Property ");
                            b.append(((PropertyUpdate)a).getProperty())
                            .append(" was updated from ")
                            .append(((PropertyUpdate)a).getPrevious())
                            .append(" to ")
                            .append(((PropertyUpdate)a).getCurrent());
                            String m = b.toString();
                            logger.info(m);
                        } 

                        if (a instanceof ListUpdate) {
                            StringBuilder b = new StringBuilder("Property ");
                            b.append(((ListUpdate)a).getProperty())
                            .append(" was updated from ")
                            .append(((ListUpdate)a).getAdded())
                            .append(" to ")
                            .append(((ListUpdate)a).getRemoved());
                            String m = b.toString();
                            logger.info(m);
                        } 

                    }
                }
                is.close();
            }
            
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());            
            e.printStackTrace();
        }

    }


    private InputStream getFileAsIOStream(final String fileName) 
    {
        InputStream ioStream = FileReaderUtil.class
            .getClassLoader()
            .getResourceAsStream(fileName);
        
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

}
