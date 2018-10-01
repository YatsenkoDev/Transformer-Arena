package com.aequilibrium.assignment.transfarena.gallery.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.bus.RxBus;
import com.aequilibrium.assignment.transfarena.bus.event.TransformerSelectedEvent;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.MathUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.ViewHolder> {

    private final Context context;
    private final RxBus rxBus;
    private final List<Transformer> transformers;

    public GalleryListAdapter(Context context, RxBus rxBus, List<Transformer> transformers) {
        this.context = context;
        this.rxBus = rxBus;
        this.transformers = transformers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.gallery_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(context.getString(R.string.name_and_rank_combination, transformers.get(position).getName(), MathUtils.calculateOverallRating(transformers.get(position))));
        holder.itemView.setOnClickListener(v -> rxBus.post(new TransformerSelectedEvent(transformers.get(holder.getAdapterPosition()))));
    }

    @Override
    public int getItemCount() {
        return transformers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
