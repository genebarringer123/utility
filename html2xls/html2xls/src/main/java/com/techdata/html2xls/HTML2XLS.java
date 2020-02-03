/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techdata.html2xls;

import com.techdata.html2xls.model.LogLines;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
//import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Gene Barringer
 */
public class HTML2XLS {
    
    static Logger log = LogManager.getLogger(HTML2XLS.class);
    
    public static void main(String[] args) {
        Validate.isTrue(args.length == 2, "usage: supply url to fetch");
        String html_file = args[0];
        String xls_file = args[1];
        log.info("main:  start");
        //Turn the html file into just the text parts of the log
        ArrayList<LogLines> al = convertHTML2ArrayList(html_file);
        // next step is to send the arraylist to get turned in to an xls file
        createXLS(xls_file, al);
        log.info("main:  end");
    }

    private static ArrayList<LogLines> convertHTML2ArrayList(String html_file) {
        log.info("convertHTML2ArrayList:  start");
        ArrayList<LogLines> html_text = new ArrayList<LogLines>();
        File input = new File(html_file);
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        } catch (IOException ex) {
            log.error(ex.getLocalizedMessage());
        }
        
        // The third table element has the actual log lines in it.
        Element table = doc.select("table").get(3);
        //Get all the rows in the table
        Elements rows = table.select("tr");
        log.debug("convertHTML2ArrayList: number of lines: " + rows.size());
        
        //Work through each row and grab the cell data 
        //add each cell to the LogLines object
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");

            LogLines logLines = new LogLines();
            logLines.setDB_date_time(cols.get(0).text());
            logLines.setALM_Node_date_time(cols.get(1).text());
            logLines.setThread(cols.get(2).text());
            logLines.setRequest(cols.get(3).text());
            logLines.setLogin(cols.get(4).text());
            logLines.setIp(cols.get(5).text());
            logLines.setLevel(cols.get(6).text());
            logLines.setMethod(cols.get(7).text());
            logLines.setMessage(cols.get(8).text());

            html_text.add(logLines);    
        }
        if (log.isDebugEnabled()){
                log.debug("convertHTML2ArrayList - number of entries in html_text is " + html_text.size());
                LogLines.prettyPrint(html_text);
            }
        
        log.info("convertHTML2ArrayList:  end");
        return html_text;
    }

    private static void createXLS(String xls_file, ArrayList<LogLines> al) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("logfile");
        
        int rowCount = 0;
        for (LogLines ll : al) {
            
            Row row = sheet.createRow(++rowCount);
            
            int columnCount = 0;
            row.createCell(++columnCount).setCellValue(ll.getALM_Node_date_time());
            row.createCell(++columnCount).setCellValue(ll.getDB_date_time());
            row.createCell(++columnCount).setCellValue(ll.getThread());
            row.createCell(++columnCount).setCellValue(ll.getRequest());
            row.createCell(++columnCount).setCellValue(ll.getLogin());
            row.createCell(++columnCount).setCellValue(ll.getIp());
            row.createCell(++columnCount).setCellValue(ll.getLevel());
            row.createCell(++columnCount).setCellValue(ll.getMethod());
            row.createCell(++columnCount).setCellValue(ll.getMessage());
        }
        
                
        // Write the file to filesystem.
        try (FileOutputStream outputStream = new FileOutputStream(xls_file)) {
            workbook.write(outputStream);
        } catch (FileNotFoundException ex) {
            log.error(ex.getLocalizedMessage());
        } catch (IOException ex) {
            log.error(ex.getLocalizedMessage());
        }
    }

}
