package com.fed.file.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fed.file.exception.FileHandlingException;

public class FileUtility {

    public static File openFile(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = FileUtility.getInputStreamFor(fileName);
            return  openFile(inputStream);
        } finally {
            FileUtility.closeInputStream(inputStream);
        }
    }
    
    public static File openFile(InputStream inputStream) {
        OutputStream outStream = null;
        try {
            File temporaryFile = File.createTempFile("PREFIX", "SUFFIX");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            outStream = new FileOutputStream(temporaryFile);
            outStream.write(buffer);
            return temporaryFile;
        } catch (IOException e) {
            throw new FileHandlingException(e);
        } finally {
            FileUtility.closeOutputStream(outStream);
        }
    }
    
    public static void closeInputStream(InputStream inStream) {
        try {
            if (inStream != null) {
                inStream.close();
            }
        } catch (IOException e) {
            throw new FileHandlingException(e);
        }
    }

    public static void closeOutputStream(OutputStream outStream) {
        try {
            if (outStream != null) {
                outStream.close();
            }
        } catch (IOException e) {
            throw new FileHandlingException(e);
        }
    }

    public static InputStream getInputStreamFor(String fileName) {
        InputStream resourcePropStream = null;
        try {
            resourcePropStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        } catch (Exception e) {
        }
        return resourcePropStream;
    }

}
