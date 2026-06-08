package com.sptech.school.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.sptech.school.dto.AlertaDooh;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfRelatorioService {

    private static final BaseColor COR_HEADER  = new BaseColor(27, 42, 74);
    private static final BaseColor COR_LINHA   = new BaseColor(242, 245, 250);
    private static final BaseColor COR_CRITICO = new BaseColor(220, 53, 69);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public void gerarRelatorio(String caminhoSaida, List<AlertaDooh> alertas) {
        try {
            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, new FileOutputStream(caminhoSaida));
            doc.open();

            adicionarCabecalho(doc, alertas.size());
            adicionarTabela(doc, alertas);
            adicionarRodape(doc);

            doc.close();
            System.out.println("[PDF] Relatório gerado: " + caminhoSaida);

        } catch (Exception e) {
            System.err.println("[PDF] Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private void adicionarCabecalho(Document doc, int total) throws DocumentException {
        Font fTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, COR_HEADER);
        Paragraph titulo = new Paragraph("Relatório de Alertas DOOH", fTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        doc.add(titulo);

        Font fSub = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY);
        Paragraph sub = new Paragraph(
                "Gerado em: " + LocalDateTime.now().format(FMT) + "   |   Total de alertas: " + total, fSub);
        sub.setAlignment(Element.ALIGN_CENTER);
        doc.add(sub);
        doc.add(new Paragraph("\n"));
    }

    private void adicionarTabela(Document doc, List<AlertaDooh> alertas) throws DocumentException {
        PdfPTable tabela = new PdfPTable(6);
        tabela.setWidthPercentage(100);
        tabela.setWidths(new float[]{1.0f, 1.2f, 1.5f, 1.2f, 0.9f, 3.5f});
        tabela.setSpacingBefore(5f);

        String[] colunas = {"Zona", "ID Display", "Componente", "Nível", "Valor %", "Mensagem"};
        Font fHeader = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE);
        for (String col : colunas) {
            PdfPCell cell = new PdfPCell(new Phrase(col, fHeader));
            cell.setBackgroundColor(COR_HEADER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(7);
            tabela.addCell(cell);
        }

        Font fData = new Font(Font.FontFamily.HELVETICA, 8);
        boolean par = false;

        for (AlertaDooh a : alertas) {
            BaseColor bg = par ? COR_LINHA : BaseColor.WHITE;
            String[] vals = {
                nvl(a.getZona()), nvl(a.getDisplayId()), nvl(a.getComponente()), nvl(a.getNivel()),
                a.getValor() != null ? String.format("%.2f", a.getValor()) : "—",
                nvl(a.getMensagem())
            };

            for (int i = 0; i < vals.length; i++) {
                Font fonte = fData;
                if (i == 3 && vals[i].toUpperCase().contains("CRITICO")) {
                    fonte = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, COR_CRITICO);
                }
                PdfPCell c = new PdfPCell(new Phrase(vals[i], fonte));
                c.setBackgroundColor(bg);
                c.setPadding(5);
                tabela.addCell(c);
            }
            par = !par;
        }
        doc.add(tabela);
    }

    private void adicionarRodape(Document doc) throws DocumentException {
        Font fRodape = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);
        doc.add(new Paragraph("\n"));
        Paragraph rodape = new Paragraph("Gerado automaticamente em " + LocalDateTime.now().format(FMT), fRodape);
        rodape.setAlignment(Element.ALIGN_CENTER);
        doc.add(rodape);
    }

    private String nvl(String v) {
        return v != null ? v : "—";
    }
}
