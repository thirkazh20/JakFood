package com.thirafikaz.jakfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thirafikaz.jakfood.BuildConfig;
import com.thirafikaz.jakfood.Model.DataMakananItem;
import com.thirafikaz.jakfood.R;

import java.util.List;

import butterknife.OnItemClick;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    Context context;
    List<DataMakananItem> data;
    private OnFoodClick click;


    public FoodAdapter(Context context, List<DataMakananItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {
        holder.tvTitle.setText(data.get(position).getMakanan());
        holder.tvTanggal.setText(data.get(position).getInsertTime());
        String images = BuildConfig.BASE_URL + "uploads/" +data.get(position).getFotoMakanan();
        Picasso.get().load(images).into(holder.imgMakanan);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onItemClick(position);
            }
        });
    }

    public interface OnFoodClick {
        void onItemClick(int position);
    }
    public void setOnClickListener(OnFoodClick onFoodClick){
        click =  onFoodClick;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMakanan;
        TextView tvTitle;
        TextView tvTanggal;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMakanan = itemView.findViewById(R.id.item_images);
            tvTitle = itemView.findViewById(R.id.item_desk);
            tvTanggal = itemView.findViewById(R.id.item_time);
        }
    }
}
