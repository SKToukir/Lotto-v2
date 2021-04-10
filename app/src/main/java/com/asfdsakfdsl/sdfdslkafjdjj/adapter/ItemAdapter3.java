package com.asfdsakfdsl.sdfdslkafjdjj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asfdsakfdsl.sdfdslkafjdjj.R;

public class ItemAdapter3 extends RecyclerView.Adapter<ItemAdapter3.ItemViewHolder> {

    private Context context;

    public ItemAdapter3(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.txtItem.setText("หวยยี่กี วีไอพี รอบที่ "+String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return 88;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtItem;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txtDynamic);
        }
    }
}
