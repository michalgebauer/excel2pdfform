package sk.itent.excel2pdfform;

public class Application {
    private static final String DEFAULT_CONFIGURATION_LOCATION = "files/nastavenia.txt";

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Configuration configuration = new Configuration(DEFAULT_CONFIGURATION_LOCATION);
        new PdfForm(configuration).readDataAndFillForm();
    }
}
