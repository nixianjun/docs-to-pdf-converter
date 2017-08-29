package com.yeokhengmeng.docstopdfconverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;


/**
 * desc:
 * user:jason.ni
 * date:2017/8/28
 **/
public class PdfConverterUtils {
    protected final static Log logger = LogFactory.getLog(PdfConverterUtils.class);


    public static final String VERSION_STRING = "\nDocs to PDF Converter Version 1.7 (8 Dec 2013)\n\nThe MIT License (MIT)\nCopyright (c) 2013-2014 Yeo Kheng Meng";

    public static void converterData(String fileName, InputStream inStream, OutputStream outStream) {
        Converter converter = null;
        try {
            Boolean shouldShowMessages = false;
            if (fileName == null) {
                throw new IllegalArgumentException();
            }

            String lowerCaseInPath = fileName.toLowerCase();
            if (lowerCaseInPath.endsWith(".doc")) {
                converter = new DocToPDFConverter(inStream, outStream, shouldShowMessages, true);
            } else if (lowerCaseInPath.endsWith(".docx")) {
                converter = new DocxToPDFConverter(inStream, outStream, shouldShowMessages, true);
            } else if (lowerCaseInPath.endsWith(".ppt")) {
                converter = new PptToPDFConverter(inStream, outStream, shouldShowMessages, true);
            } else if (lowerCaseInPath.endsWith(".pptx")) {
                converter = new PptxToPDFConverter(inStream, outStream, shouldShowMessages, true);
            } else if (lowerCaseInPath.endsWith(".odt")) {
                converter = new OdtToPDF(inStream, outStream, shouldShowMessages, true);
            } else {
                converter = null;
            }


        } catch (Exception e) {
            logger.error("",e);
        }

        if (converter == null) {
            System.out.println("Unable to determine type of input file.");
        } else {
            try {
                converter.convert();
            } catch (Exception e) {
                logger.error("",e);
            }
        }
    }

    public enum DOC_TYPE {
        DOC,
        DOCX,
        PPT,
        PPTX,
        ODT
    }




    //From http://stackoverflow.com/questions/941272/how-do-i-trim-a-file-extension-from-a-string-in-java
    public static String changeExtensionToPDF(String originalPath) {

//		String separator = System.getProperty("file.separator");
        String filename = originalPath;

//		// Remove the path upto the filename.
//		int lastSeparatorIndex = originalPath.lastIndexOf(separator);
//		if (lastSeparatorIndex == -1) {
//			filename = originalPath;
//		} else {
//			filename = originalPath.substring(lastSeparatorIndex + 1);
//		}

        // Remove the extension.
        int extensionIndex = filename.lastIndexOf(".");

        String removedExtension;
        if (extensionIndex == -1) {
            removedExtension = filename;
        } else {
            removedExtension = filename.substring(0, extensionIndex);
        }
        String addPDFExtension = removedExtension + ".pdf";

        return addPDFExtension;
    }


    protected static InputStream getInFileStream(String inputFilePath) throws FileNotFoundException {
        File inFile = new File(inputFilePath);
        FileInputStream iStream = new FileInputStream(inFile);
        return iStream;
    }

    protected static OutputStream getOutFileStream(String outputFilePath) throws IOException {
        File outFile = new File(outputFilePath);

        try {
            //Make all directories up to specified
            outFile.getParentFile().mkdirs();
        } catch (NullPointerException e) {
            //Ignore error since it means not parent directories
        }

        outFile.createNewFile();
        FileOutputStream oStream = new FileOutputStream(outFile);
        return oStream;
    }





}
