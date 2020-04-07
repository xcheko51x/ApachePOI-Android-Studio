package com.xcheko51x.apachepoi_ejemplo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnGuardarExcel, btLeerExcel;
    TextView tvDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGuardarExcel = findViewById(R.id.btnGuardarExcel);
        btLeerExcel = findViewById(R.id.btnLeerExcel);
        tvDatos = findViewById(R.id.tvDatos);

        btnGuardarExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });

        btLeerExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leer();
            }
        });
    }

    public void guardar() {
        Workbook wb = new HSSFWorkbook();
        Cell cell = null;
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet sheet = null;
        sheet = wb.createSheet("Lista de usuarios");

        Row row = null;

        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("USUARIO");
        cell.setCellStyle(cellStyle);

        sheet.createRow(1);
        cell = row.createCell(1);
        cell.setCellValue("NOMBRE");
        cell.setCellStyle(cellStyle);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("xcheko51x");

        cell = row.createCell(1);
        cell.setCellValue("Sergio Peralta");

        File file = new File(getExternalFilesDir(null),"Relacion_Usuarios.xls");
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void leer() {
        File file = new File(this.getExternalFilesDir(null), "Relacion_Usuarios.xls");
        FileInputStream inputStream = null;

        String datos = "";

        try {
            inputStream = new FileInputStream(file);

            POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);

            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);

            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row>  rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIterator.next();

                    datos = datos+" - "+cell.toString();

                }
                datos = datos+"\n";
            }

            tvDatos.setText(datos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
