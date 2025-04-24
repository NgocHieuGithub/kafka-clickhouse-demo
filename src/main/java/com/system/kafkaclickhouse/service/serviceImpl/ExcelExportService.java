package com.system.kafkaclickhouse.service.serviceImpl;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

@Service
public class ExcelExportService {

    private static final int PAGE_SIZE = 10000;    // Truy vấn 10,000 dòng mỗi lần
    private static final int MAX_ROW_PER_SHEET = 50000; // Giới hạn dòng mỗi sheet (50,000 dòng)

    public void exportDataToExcel() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "Hieu@123"
        );
        String query = "SELECT * FROM person LIMIT ?, ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Sử dụng SXSSFWorkbook để tiết kiệm bộ nhớ
        Workbook workbook = new SXSSFWorkbook(100);  // Giữ tối đa 100 dòng trong bộ nhớ cùng lúc
        Sheet currentSheet = workbook.createSheet("Batch 1");

        int sheetNumber = 1;
        int currentSheetRowCount = 0;
        int pageIndex = 0;

        // Mở FileOutputStream một lần duy nhất
        try (FileOutputStream fileOut = new FileOutputStream("exported_data.xlsx")) {

            while (true) {
                preparedStatement.setInt(1, pageIndex * PAGE_SIZE);
                preparedStatement.setInt(2, PAGE_SIZE);
                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) break;

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                do {
                    if (currentSheetRowCount == 0) {
                        // Tạo header nếu là dòng đầu của sheet
                        Row headerRow = currentSheet.createRow(currentSheetRowCount++);
                        for (int i = 1; i <= columnCount; i++) {
                            headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
                        }
                    }

                    if (currentSheetRowCount >= MAX_ROW_PER_SHEET) {
                        sheetNumber++;
                        currentSheet = workbook.createSheet("Batch " + sheetNumber);
                        currentSheetRowCount = 0;

                        Row headerRow = currentSheet.createRow(currentSheetRowCount++);
                        for (int i = 1; i <= columnCount; i++) {
                            headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
                        }
                    }

                    Row dataRow = currentSheet.createRow(currentSheetRowCount++);
                    for (int i = 1; i <= columnCount; i++) {
                        dataRow.createCell(i - 1).setCellValue(rs.getString(i));
                    }

                } while (rs.next());

                // Ghi vào file sau mỗi lần truy vấn dữ liệu
                workbook.write(fileOut);

                pageIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error while writing the Excel file.", e);
        } finally {
            workbook.close();
            connection.close();
        }
    }

}
