package view.recicle.table.kirill.sorokin.ru.tablerecicleview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TableAdapter tableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recicleView);
        tableAdapter = new TableAdapter();
        TableLayoutManager tableLayoutManager = new TableLayoutManager(tableAdapter);
        recyclerView.setLayoutManager(tableLayoutManager);
        recyclerView.setAdapter(tableAdapter);

        new AsyncTask<Void, Void, Void>() {

            private TableModel tableModel;

            @Override
            protected Void doInBackground(Void... voids) {
                int countColumn = 200;
                int countRow = 200;
                TableCell[][] table= new TableCell[countColumn][countRow];
                table[0][0] = new TableCell("empty");
                for (int i = 1; i < countRow; i++) {
                    table[0][i] =  new TableCell("row " + i);
                }
                for (int i = 1; i < countColumn; i++) {
                    table[i][0] =  new TableCell("column " + i);
                }
                for (int i = 1; i < countColumn; i++) {
                    for (int j = 1; j < countRow; j++) {
                        table[i][j] = new TableCell("cell " + i + "-" + j);
                    }
                }
                tableModel = new TableModel();
                tableModel.setColumns(table);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                tableAdapter.setModels(tableModel);
            }
        }.execute();

    }
}
