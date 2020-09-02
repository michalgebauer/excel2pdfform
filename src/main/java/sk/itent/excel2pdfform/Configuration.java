package sk.itent.excel2pdfform;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Configuration {
    private static final String SPLIT_CHARACTER = "=";
    private static final String PDF_TEMPLATE_KEY = "excel2pdfform.template";
    private static final String EXCEL_DATA_KEY = "excel2pdfform.data";
    private static final String RESULT_DATA_KEY = "excel2pdfform.result";

    private String pdfTemplatePath = "files/template.pdf";
    private String excelPath = "files/data.xlsx";
    private String outputPdfPath = "files/result.pdf";
    private Map<String, String> cellFieldReferenceMap;

    public Configuration(String configFilePath) {
        cellFieldReferenceMap = new HashMap<>();
        try (
                FileInputStream fis = new FileInputStream(configFilePath);
                Scanner sc = new Scanner(fis)) {
            int lineNumber = 0;
            while (sc.hasNextLine()) {
                lineNumber++;
                String line = sc.nextLine();
                if (line == null || line.trim().equals("")) {
                    continue;
                }
                String[] configurationLine = line.split(SPLIT_CHARACTER);
                if (configurationLine.length != 2) {
                    System.out.println("Nespravny zaznam v " + configFilePath + ", riadok " + lineNumber + ", hodnota: " + line);
                    continue;
                }
                String key = configurationLine[0];
                String value = configurationLine[1];
                if (PDF_TEMPLATE_KEY.equals(key)) {
                    this.pdfTemplatePath = value;
                } else if (EXCEL_DATA_KEY.equals(key)) {
                    this.excelPath = value;
                } else if (RESULT_DATA_KEY.equals(key)) {
                    this.outputPdfPath = key;
                } else {
                    cellFieldReferenceMap.put(key, value);
                }
            }
        } catch (IOException e) {
            System.out.println("Problem pri nacitavani nastaveni v " + configFilePath + ": " + e.getMessage());
        }
    }

    public String getPdfTemplatePath() {
        return pdfTemplatePath;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public String getOutputPdfPath() {
        return outputPdfPath;
    }

    public Map<String, String> getCellFieldReferenceMap() {
        return cellFieldReferenceMap;
    }
}
