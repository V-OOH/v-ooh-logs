package com.sptech.school.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FMT_COMPLETO     = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter FMT_DATA         = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FMT_NOME_ARQUIVO = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter FMT_S3 =           DateTimeFormatter.ofPattern("dd_MM_yyyy");

    private DateUtil() {}

    public static String formatar(LocalDateTime dt) {
        return dt != null ? dt.format(FMT_COMPLETO) : "";
    }

    public static String hojeString() {
        return LocalDate.now().format(FMT_DATA);
    }

    public static String timestampArquivo() {
        return LocalDateTime.now().format(FMT_NOME_ARQUIVO);
    }

    public static String hojeS3() {
        return LocalDate.now().format(FMT_S3);
    }
}
