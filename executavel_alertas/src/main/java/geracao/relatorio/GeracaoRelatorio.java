package geracao.relatorio;

// Realizar relatórios

import java.io.FileOutputStream;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class GeracaoRelatorio {
    public static void main(String[] args) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
            document.open();
            document.add(new Paragraph("Relatorio 100% foda"));
            System.out.println("PDF gerado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}


