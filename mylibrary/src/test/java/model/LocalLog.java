package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalLog {

    File mFile;
    public LocalLog()
    {
        mFile = new File("sdcard/log.file");
        if (!mFile.exists())
        {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            mFile.delete();
        }
    }
    public void log_e(String text)
    {
        String message = "ERROR" +" "+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format( new Date()) + " "+text;
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(mFile,true));
            buf.append(message);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void log_i(String text)
    {
        String message = "INFO" +" "+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format( new Date()) + " "+text;
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(mFile,true));
            buf.append(message);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void log_d(String text)
    {
        String message = "DEBUG" +" "+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format( new Date()) + " "+text;
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(mFile,true));
            buf.append(message);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void log_w(String text)
    {
        String message = "WARNING" +" "+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format( new Date()) + " "+text;
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(mFile,true));
            buf.append(message);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
