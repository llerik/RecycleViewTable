package view.recicle.table.kirill.sorokin.ru.tablerecicleview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Kirill on 30.04.2017.
 */
public class TableDecoratorHeader extends RecyclerView.ItemDecoration {

    private RecyclerView.Adapter adapter;

    public TableDecoratorHeader(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

}
