package view.recicle.table.kirill.sorokin.ru.tablerecicleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Kirill on 28.04.2017.
 */
public class TableAdapter extends RecyclerView.Adapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    private TableModel tableModel;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        switch (viewType) {
//            case TableModel.BODY: {
                return new ViewHolder(inflater.inflate(R.layout.element_table_body, parent, false));
//            }
//            case TableModel.HEADER: {
//                return new ViewHolder(inflater.inflate(R.layout.element_table_header, parent, false));
//            }
//            case TableModel.LEFT: {
//                return new ViewHolder(inflater.inflate(R.layout.element_table_left, parent, false));
//            }
//            default:
//            case TableModel.EMPTY: {
//                return new ViewHolder(inflater.inflate(R.layout.element_table_empty, parent, false));
//            }
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
//        switch (holder.getItemViewType()) {
//            case TableModel.BODY: {
//
//                break;
//            }
//            case TableModel.EMPTY: {
//
//                break;
//            }
//            case TableModel.HEADER: {
//
//                break;
//            }
//            case TableModel.LEFT: {
//
//                break;
//            }
//        }
        TableCell cell = tableModel.get(position);
        viewHolder.text.setText(cell.getText());
        if (position != holder.getAdapterPosition()) {
            Log.d("position : " + position + " getAdapterPosition : " + holder.getAdapterPosition());
        }
    }

//    @Override
//    public int getItemViewType(int position) {
//        return tableModel.getType(position);
//    }

    public Cell getCellAtPosition(int position) {
        return tableModel.getCellAtPosition(position);
    }

    public int getCountRow() {
        return tableModel.getCountRow();
    }

    public int getCountColumn() {
        return tableModel.getCountColumn();
    }

    public int getPosition(Cell cell) {
        return tableModel.getPosition(cell);
    }

    @Override
    public int getItemCount() {
        return tableModel == null ? 0 : tableModel.size();
    }

    public void setModels(TableModel tableModel) {
        this.tableModel = tableModel;
        notifyDataSetChanged();
    }
}
