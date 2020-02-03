/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techdata.html2xls.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author CBARRING
 */
public class LogLines implements Serializable {
    
    static Logger log = LogManager.getLogger(LogLines.class);

    private String sampleProperty;
    private String DB_date_time = null;
    private String ALM_Node_date_time = null;
    private String thread = null;
    private String request = null;
    private String login = null;
    private String ip = null;
    private String level = null;
    private String method = null;
    private String message = null;
    
    //A POJO

    public String getSampleProperty() {
        return sampleProperty;
    }

    public void setSampleProperty(String sampleProperty) {
        this.sampleProperty = sampleProperty;
    }

    public String getDB_date_time() {
        return DB_date_time;
    }

    public void setDB_date_time(String DB_date_time) {
        this.DB_date_time = DB_date_time;
    }

    public String getALM_Node_date_time() {
        return ALM_Node_date_time;
    }

    public void setALM_Node_date_time(String ALM_Node_date_time) {
        this.ALM_Node_date_time = ALM_Node_date_time;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getDB_date_time());
        sb.append("\t");
        sb.append(this.getALM_Node_date_time());
        sb.append("\t");
        sb.append(this.getThread());
        sb.append("\t");
        sb.append(this.getRequest());
        sb.append("\t");
        sb.append(this.getLogin());
        sb.append("\t");
        sb.append(this.getIp());
        sb.append("\t");
        sb.append(this.getLevel());
        sb.append("\t");
        sb.append(this.getMethod());
        sb.append("\t");
        sb.append(this.getMessage());
        sb.append("\n");

        return sb.toString();
    }
    
    public static void prettyPrint(ArrayList lines) {
        log.info("prettyPrint:  start");
        log.debug("prettyPrint - number of entries in lines is " + lines.size());
        for (int i = 0; i < lines.size(); i++) {
            log.debug("prettyPrint - " + lines.get(i).toString());
//            System.out.println(lines.get(i).toString());
        }
        log.info("prettyPrint:  end");
    }
}
