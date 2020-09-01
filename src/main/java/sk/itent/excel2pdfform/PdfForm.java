package sk.itent.excel2pdfform;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PdfForm {
    private final Configuration configuration;

    public PdfForm(Configuration configuration) {
        this.configuration = configuration;
    }

    void readDataAndFillForm() {
        try (
                Workbook workbook = WorkbookFactory.create(new File(configuration.getExcelPath()));
                PdfReader reader = new PdfReader(configuration.getPdfTemplatePath());
                PdfDocument document = new PdfDocument(reader, new PdfWriter(configuration.getOutputPdfPath()))) {
            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(document, false);
            Map<String, PdfFormField> formFieldsMap = acroForm.getFormFields();
            printAvailableFormFields(formFieldsMap);
            fillForm(workbook, formFieldsMap);
        } catch (IOException e) {
            System.out.println("Problem pri nacitavani suborov: " + e.getMessage());
        }
    }

    private void printAvailableFormFields(Map<String, PdfFormField> formFieldsMap) {
        System.out.println("Formularove polia v " + configuration.getPdfTemplatePath());
        formFieldsMap.forEach(
                (fldName, pdfFormField) -> System.out.println(fldName + ": " + formFieldsMap.get(fldName).getValueAsString()));
    }

    private void fillForm(Workbook workbook, Map<String, PdfFormField> formFieldsMap) {
        DataFormatter dataFormatter = new DataFormatter();
        configuration.getCellFieldReferenceMap().forEach((key, value) -> {
            CellReference ref = new CellReference(value);
            Sheet sheet = ref.getSheetName() != null ? workbook.getSheet(ref.getSheetName()) : workbook.getSheetAt(0);
            Row row = sheet.getRow(ref.getRow());
            if (row != null) {
                Cell cell = row.getCell(ref.getCol());
                formFieldsMap.get(key).setValue(dataFormatter.formatCellValue(cell));
            }
        });
    }
}
