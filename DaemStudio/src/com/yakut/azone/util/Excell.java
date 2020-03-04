/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author yakut
 */
public class Excell {

    public Excell() {
    }

    public void exceleAktar(JTable table, File dosya) {
        try {
            int row = 0;
            int col = 0;
            Vector<String> header = new Vector<String>();
            col = table.getColumnCount();
            for (int k = 0; k < col; k++) {
                header.add(table.getColumnModel().getColumn(k).getHeaderValue().toString());
            }

            File file = dosya;//new File("rapor/rapor" + new SimpleDateFormat("-dd-MM-yyyy-HH_mm").format(new Date()) + ".xls");
            if (file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("rapor");
            HSSFRow rows;// = sheet.createRow(10);
            HSSFCell cell;// = row.createCell(5);
            rows = sheet.createRow(0);
            row++;
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setFillBackgroundColor(HSSFColor.RED.index);
            // cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//            cell.setCellStyle(cs);
            HSSFFont f = workbook.createFont();
            // Font 1'yi 12 point type, blue ve bold yap
            f.setFontHeightInPoints((short) 12);
            //    f.setColor(HSSFColor.RED.index);
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cs.setFont(f);

            for (int k = 0; k < header.size(); k++) {
                cell = rows.createCell(k);
                cell.setCellValue(header.get(k).toString());
                cell.setCellStyle(cs);
            }

            for (int m = 0; m < table.getRowCount(); m++) {
                rows = sheet.createRow(row);
                row++;
                int l = table.getColumnCount();
                for (int j = 0; j < l; j++) {
                    cell = rows.createCell(j);
                    Object o = table.getValueAt(m, j);
                    System.out.print("-" + o.toString());
                    if (o instanceof String) {
                        cell.setCellValue(o.toString());
                    } else if (o instanceof Integer) {
                        cell.setCellValue((Integer) o);
                    } else if (o instanceof Double) {
                        cell.setCellValue((Double) o);
                    } else if (o instanceof Long) {
                        cell.setCellValue((Long) o);
                    } else if (o instanceof Float) {
                        cell.setCellValue((Float) o);
                    } else if (o instanceof Date) {
                        cell.setCellValue((Date) o);
                    } else if (o instanceof String) {
                        cell.setCellValue(o.toString());
                    }
                    // cell.setCellValue("" + jTable3.getValueAt(m, j));
                }
                System.out.println("");
            }

            workbook.write(outputStream);
            outputStream.close();
//                                    Runtime runtime = Runtime.getRuntime();
//                                    Process p = runtime.exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exceleAktar(JTable table) {
        try {
            int row = 0;
            int col = 0;
            Vector<String> header = new Vector<String>();
            col = table.getColumnCount();
            for (int k = 0; k < col; k++) {
                header.add(table.getColumnModel().getColumn(k).getHeaderValue().toString());
            }
            File file = new File("./rapor/");
            try {
                if (file.exists()) {
                    file.createNewFile();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            file = new File("rapor/rapor" + new SimpleDateFormat("-dd-MM-yyyy-HH_mm").format(new Date()) + ".xls");
            if (file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("rapor");
            HSSFRow rows;// = sheet.createRow(10);
            HSSFCell cell;// = row.createCell(5);
            rows = sheet.createRow(0);
            row++;
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setFillBackgroundColor(HSSFColor.RED.index);
            // cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//            cell.setCellStyle(cs);
            HSSFFont f = workbook.createFont();
            // Font 1'yi 12 point type, blue ve bold yap
            f.setFontHeightInPoints((short) 12);
            //    f.setColor(HSSFColor.RED.index);
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cs.setFont(f);

            for (int k = 0; k < header.size(); k++) {
                cell = rows.createCell(k);
                cell.setCellValue(header.get(k).toString());
                cell.setCellStyle(cs);
            }

            for (int m = 0; m < table.getRowCount(); m++) {
                rows = sheet.createRow(row);
                row++;
                int l = table.getColumnCount();
                for (int j = 0; j < l; j++) {
                    cell = rows.createCell(j);
                    Object o = table.getValueAt(m, j);
                    System.out.print("-" + o.getClass().toString());
                    if (o instanceof String) {

                        cell.setCellValue(o.toString());
                    } else if (o instanceof Integer) {

                        cell.setCellValue((Integer) o);
                    } else if (o instanceof Double) {

                        cell.setCellValue((Double) o);
                    } else if (o instanceof Long) {

                        cell.setCellValue((Long) o);
                    } else if (o instanceof Float) {

                        cell.setCellValue((Float) o);
                    } else if (o instanceof Date) {

                        cell.setCellValue((Date) o);
                    } else if (o instanceof java.math.BigInteger) {

                        cell.setCellValue(((java.math.BigInteger) o).intValue());

                    } else {
                        cell.setCellValue(o.toString());
                    }

                    // cell.setCellValue("" + jTable3.getValueAt(m, j));
                }
                System.out.println("");
            }

            workbook.write(outputStream);
            outputStream.close();
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
