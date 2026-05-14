package com.example.demo.service.impl;

import com.example.demo.dto.avance.AvancePartidaResponse;
import com.example.demo.dto.registro.RegistroHorasResponse;
import com.example.demo.dto.rendimiento.RendimientoIndicadorResponse;
import com.example.demo.service.AvancePartidaService;
import com.example.demo.service.RegistroHorasService;
import com.example.demo.service.RendimientoService;
import com.example.demo.service.ReporteService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final RendimientoService  rendimientoService;
    private final RegistroHorasService registroHorasService;
    private final AvancePartidaService avancePartidaService;

    // ─── Colores corporativos ────────────────────────────────────────────────
    private static final Color COLOR_HEADER   = new Color(30, 58, 138);   // azul oscuro
    private static final Color COLOR_ALT      = new Color(241, 245, 249); // gris claro fila par
    private static final Color COLOR_VERDE    = new Color(34, 197, 94);
    private static final Color COLOR_AMARILLO = new Color(234, 179, 8);
    private static final Color COLOR_ROJO     = new Color(239, 68, 68);
    private static final Color COLOR_GRIS     = new Color(107, 114, 128);
    private static final Color COLOR_BLANCO   = Color.WHITE;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ════════════════════════════════════════════════════════════════════════
    //  1. Rendimiento por Trabajador
    // ════════════════════════════════════════════════════════════════════════
    @Override
    public byte[] generarRendimientoTrabajador(Integer idTrabajador, LocalDate fechaInicio, LocalDate fechaFin) {
        List<RendimientoIndicadorResponse> datos =
                rendimientoService.calcularPorTrabajador(idTrabajador, fechaInicio, fechaFin);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4.rotate(), 30, 30, 40, 30);
        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            // Encabezado
            String trabajadorNombre = datos.isEmpty() ? "ID " + idTrabajador
                    : datos.get(0).getNombreTrabajador();
            agregarEncabezado(doc, "Reporte de Rendimiento — " + trabajadorNombre,
                    fechaInicio, fechaFin);

            if (datos.isEmpty()) {
                doc.add(new Paragraph("Sin datos en el período seleccionado.", FontFactory.getFont(FontFactory.HELVETICA, 11)));
                doc.close();
                return baos.toByteArray();
            }

            // Tabla
            float[] anchos = {3f, 2f, 1.5f, 2f, 1.5f, 1.5f, 1.5f, 1.5f};
            PdfPTable tabla = crearTabla(anchos,
                    "Proyecto", "Cuadrilla", "Horas", "Avance", "Rend. Real", "Rend. Esp.", "Desviación", "Semáforo");

            boolean par = false;
            for (RendimientoIndicadorResponse r : datos) {
                Color bg = par ? COLOR_ALT : COLOR_BLANCO;

                addCelda(tabla, nvl(r.getNombreProyecto()),   bg, false);
                addCelda(tabla, nvl(r.getNombreCuadrilla()),  bg, false);
                addCelda(tabla, fmt(r.getTotalHorasTrabajadas(), "h"), bg, true);
                addCelda(tabla, fmtAvance(r.getTotalAvanceEjecutado(), r.getUnidadMedida()), bg, true);
                addCelda(tabla, fmt4(r.getRendimientoReal()),   bg, true);
                addCelda(tabla, fmt4(r.getRendimientoEsperado()), bg, true);
                addCelda(tabla, fmtDesv(r.getPorcentajeDesviacion()), bg, true);
                addCeldaSemaforo(tabla, r.getIndicadorSemaforo(), bg);

                par = !par;
            }
            doc.add(tabla);
            agregarPiePagina(doc);
        } catch (DocumentException e) {
            throw new RuntimeException("Error generando PDF de rendimiento", e);
        } finally {
            if (doc.isOpen()) doc.close();
        }
        return baos.toByteArray();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  2. Horas por Proyecto
    // ════════════════════════════════════════════════════════════════════════
    @Override
    public byte[] generarHorasProyecto(Integer idProyecto, LocalDate fechaInicio, LocalDate fechaFin) {
        List<RegistroHorasResponse> datos =
                registroHorasService.listarPorProyecto(idProyecto, fechaInicio, fechaFin);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4.rotate(), 30, 30, 40, 30);
        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            String proyectoNombre = datos.isEmpty() ? "Proyecto ID " + idProyecto
                    : datos.get(0).getNombreProyecto();
            agregarEncabezado(doc, "Reporte de Horas Trabajadas — " + proyectoNombre,
                    fechaInicio, fechaFin);

            if (datos.isEmpty()) {
                doc.add(new Paragraph("Sin registros en el período seleccionado.", FontFactory.getFont(FontFactory.HELVETICA, 11)));
                doc.close();
                return baos.toByteArray();
            }

            float[] anchos = {3f, 2f, 1.5f, 1.5f, 1.5f, 3f};
            PdfPTable tabla = crearTabla(anchos,
                    "Trabajador", "Cuadrilla", "Fecha", "Horas", "Tipo Período", "Observaciones");

            BigDecimal totalHoras = BigDecimal.ZERO;
            boolean par = false;
            for (RegistroHorasResponse r : datos) {
                Color bg = par ? COLOR_ALT : COLOR_BLANCO;
                addCelda(tabla, nvl(r.getNombreTrabajador()), bg, false);
                addCelda(tabla, nvl(r.getNombreCuadrilla()),  bg, false);
                addCelda(tabla, r.getFechaRegistro() != null ? r.getFechaRegistro().format(FMT) : "—", bg, true);
                addCelda(tabla, fmt(r.getHorasTrabajadas(), "h"), bg, true);
                addCelda(tabla, nvl(r.getTipoPeriodo()),          bg, true);
                addCelda(tabla, nvl(r.getObservaciones()),        bg, false);
                if (r.getHorasTrabajadas() != null) totalHoras = totalHoras.add(r.getHorasTrabajadas());
                par = !par;
            }

            // Fila total
            PdfPCell celdaTotalLabel = new PdfPCell(new Phrase("TOTAL",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, COLOR_BLANCO)));
            celdaTotalLabel.setColspan(3);
            celdaTotalLabel.setBackgroundColor(COLOR_HEADER);
            celdaTotalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celdaTotalLabel.setPadding(5);
            tabla.addCell(celdaTotalLabel);

            PdfPCell celdaTotalVal = new PdfPCell(new Phrase(
                    String.format("%.2f h", totalHoras.doubleValue()),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, COLOR_BLANCO)));
            celdaTotalVal.setBackgroundColor(COLOR_HEADER);
            celdaTotalVal.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaTotalVal.setPadding(5);
            tabla.addCell(celdaTotalVal);

            PdfPCell celdaBlanco = new PdfPCell(new Phrase(""));
            celdaBlanco.setColspan(2);
            celdaBlanco.setBackgroundColor(COLOR_HEADER);
            celdaBlanco.setPadding(5);
            tabla.addCell(celdaBlanco);

            doc.add(tabla);
            agregarPiePagina(doc);
        } catch (DocumentException e) {
            throw new RuntimeException("Error generando PDF de horas", e);
        } finally {
            if (doc.isOpen()) doc.close();
        }
        return baos.toByteArray();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  3. Avance de Obra por Proyecto
    // ════════════════════════════════════════════════════════════════════════
    @Override
    public byte[] generarAvanceObra(Integer idProyecto, LocalDate fechaInicio, LocalDate fechaFin) {
        List<AvancePartidaResponse> datos =
                avancePartidaService.listarPorProyecto(idProyecto, fechaInicio, fechaFin);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4.rotate(), 30, 30, 40, 30);
        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            String proyectoNombre = datos.isEmpty() ? "Proyecto ID " + idProyecto
                    : datos.get(0).getNombreProyecto();
            agregarEncabezado(doc, "Reporte de Avance de Obra — " + proyectoNombre,
                    fechaInicio, fechaFin);

            if (datos.isEmpty()) {
                doc.add(new Paragraph("Sin registros en el período seleccionado.", FontFactory.getFont(FontFactory.HELVETICA, 11)));
                doc.close();
                return baos.toByteArray();
            }

            float[] anchos = {2.5f, 2f, 1.5f, 2.5f, 1.5f, 1.5f, 1.5f, 1.5f};
            PdfPTable tabla = crearTabla(anchos,
                    "Partida", "Cuadrilla", "Fecha", "Actividad (Estándar)", "Cantidad Ej.", "Programado", "Unidad", "% Avance");

            boolean par = false;
            for (AvancePartidaResponse r : datos) {
                Color bg = par ? COLOR_ALT : COLOR_BLANCO;
                addCelda(tabla, nvl(r.getNombrePartida()),    bg, false);
                addCelda(tabla, nvl(r.getNombreCuadrilla()),  bg, false);
                addCelda(tabla, r.getFechaRegistro() != null ? r.getFechaRegistro().format(FMT) : "—", bg, true);
                addCelda(tabla, nvl(r.getNombreActividad()),  bg, false);
                addCelda(tabla, fmtBd(r.getCantidadEjecutada()),  bg, true);
                addCelda(tabla, fmtBd(r.getCantidadProgramada()), bg, true);
                addCelda(tabla, nvl(r.getUnidadMedida()),    bg, true);
                addCelda(tabla, fmtPorcentaje(r.getCantidadEjecutada(), r.getCantidadProgramada()), bg, true);
                par = !par;
            }

            doc.add(tabla);
            agregarPiePagina(doc);
        } catch (DocumentException e) {
            throw new RuntimeException("Error generando PDF de avance de obra", e);
        } finally {
            if (doc.isOpen()) doc.close();
        }
        return baos.toByteArray();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  Helpers PDF
    // ════════════════════════════════════════════════════════════════════════

    private void agregarEncabezado(Document doc, String titulo,
                                   LocalDate inicio, LocalDate fin) throws DocumentException {
        Font fTitulo  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, COLOR_HEADER);
        Font fSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, COLOR_GRIS);

        Paragraph pTitulo = new Paragraph(titulo, fTitulo);
        pTitulo.setSpacingAfter(4);
        doc.add(pTitulo);

        String periodo = "Período: " + inicio.format(FMT) + " — " + fin.format(FMT)
                + "   |   Generado: " + LocalDate.now().format(FMT);
        Paragraph pPeriodo = new Paragraph(periodo, fSubtitulo);
        pPeriodo.setSpacingAfter(12);
        doc.add(pPeriodo);

        // línea separadora
        LineSeparator sep = new LineSeparator(1.5f, 100, COLOR_HEADER, Element.ALIGN_CENTER, -2);
        doc.add(sep);
        doc.add(Chunk.NEWLINE);
    }

    private PdfPTable crearTabla(float[] anchos, String... cabeceras) throws DocumentException {
        PdfPTable tabla = new PdfPTable(anchos.length);
        tabla.setWidthPercentage(100);
        tabla.setWidths(anchos);

        Font fCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, COLOR_BLANCO);
        for (String cab : cabeceras) {
            PdfPCell celda = new PdfPCell(new Phrase(cab, fCabecera));
            celda.setBackgroundColor(COLOR_HEADER);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(6);
            celda.setBorderColor(COLOR_BLANCO);
            tabla.addCell(celda);
        }
        return tabla;
    }

    private void addCelda(PdfPTable tabla, String texto, Color bg, boolean centrado) {
        Font f = FontFactory.getFont(FontFactory.HELVETICA, 8);
        PdfPCell celda = new PdfPCell(new Phrase(texto != null ? texto : "—", f));
        celda.setBackgroundColor(bg);
        celda.setHorizontalAlignment(centrado ? Element.ALIGN_CENTER : Element.ALIGN_LEFT);
        celda.setPaddingTop(4);
        celda.setPaddingBottom(4);
        celda.setPaddingLeft(5);
        celda.setPaddingRight(5);
        celda.setBorderColor(new Color(226, 232, 240));
        tabla.addCell(celda);
    }

    private void addCeldaSemaforo(PdfPTable tabla, String semaforo, Color bg) {
        String texto;
        Color colorTexto;
        switch (semaforo != null ? semaforo : "SIN_ESTANDAR") {
            case "VERDE":    texto = "● VERDE";    colorTexto = COLOR_VERDE;    break;
            case "AMARILLO": texto = "● AMARILLO"; colorTexto = COLOR_AMARILLO; break;
            case "ROJO":     texto = "● ROJO";     colorTexto = COLOR_ROJO;     break;
            default:         texto = "● S/D";      colorTexto = COLOR_GRIS;     break;
        }
        Font f = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, colorTexto);
        PdfPCell celda = new PdfPCell(new Phrase(texto, f));
        celda.setBackgroundColor(bg);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPaddingTop(4);
        celda.setPaddingBottom(4);
        celda.setBorderColor(new Color(226, 232, 240));
        tabla.addCell(celda);
    }

    private void agregarPiePagina(Document doc) throws DocumentException {
        Font f = FontFactory.getFont(FontFactory.HELVETICA, 8, COLOR_GRIS);
        Paragraph pie = new Paragraph("INDECSA — Sistema de Capital Humano — Documento generado automáticamente", f);
        pie.setAlignment(Element.ALIGN_CENTER);
        pie.setSpacingBefore(16);
        doc.add(pie);
    }

    // ─── Formateo de valores ────────────────────────────────────────────────
    private String nvl(String s)     { return s != null && !s.isBlank() ? s : "—"; }

    private String fmt(BigDecimal v, String sufijo) {
        return v != null ? String.format("%.2f %s", v.doubleValue(), sufijo) : "—";
    }

    private String fmt4(BigDecimal v) {
        return v != null ? String.format("%.4f", v.doubleValue()) : "—";
    }

    private String fmtBd(BigDecimal v) {
        return v != null ? String.format("%.2f", v.doubleValue()) : "—";
    }

    private String fmtAvance(BigDecimal cantidad, String unidad) {
        if (cantidad == null) return "—";
        return String.format("%.2f %s", cantidad.doubleValue(), unidad != null ? unidad : "");
    }

    private String fmtDesv(BigDecimal d) {
        return d != null ? String.format("%+.1f%%", d.doubleValue()) : "—";
    }

    private String fmtPorcentaje(BigDecimal ejecutado, BigDecimal programado) {
        if (ejecutado == null || programado == null || programado.compareTo(BigDecimal.ZERO) == 0)
            return "—";
        double pct = ejecutado.doubleValue() / programado.doubleValue() * 100.0;
        return String.format("%.1f%%", pct);
    }
}
