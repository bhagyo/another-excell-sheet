package com.example.bhagyo.excell;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {

    String[][] str;
    String zz=null;
    String extra=null;
    int row,col;
    private ListView listView;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readQuestion();
        listView=findViewById(R.id.list_item);
        searchView=findViewById(R.id.search);
        String[] allQues = new String[row];
        for(int i=0;i<row;i++) {
            allQues[i] = (str[i][5]);
        }
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, R.layout.view_style,R.id.textview,allQues);
        listView.setAdapter(arrayAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
    private void readQuestion() {
        try{
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("question.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);
            row = sheet.getRows();
            col = sheet.getColumns();
            str = new String[row][col];
            for(int i=0;i<row;i++){
                zz=null;
                for(int j=0;j<col;j++) {
                    Cell cell = sheet.getCell(j, i);
                    extra= (cell.getContents());
                    if(j==0){
                        zz = extra+"?";
                    }
                    else if (j == 5) {
                        zz = zz + " Answer: " + extra;
                    } else {
                        zz = zz + " " + extra;
                    }
                    str[i][j]=zz;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        /*
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++)
                Log.d("total","whole: "+str[i][j]);
        }*/
    }
}
