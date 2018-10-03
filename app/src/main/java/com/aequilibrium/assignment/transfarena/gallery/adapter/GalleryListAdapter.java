package com.aequilibrium.assignment.transfarena.gallery.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.bus.RxBus;
import com.aequilibrium.assignment.transfarena.bus.event.TransformerSelectedEvent;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.TeamUtils;
import com.squareup.picasso.Picasso;

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
        Picasso.get().load(TeamUtils.getTransformerAvatar(transformers.get(position))).into(holder.icon);
        holder.name.setText(transformers.get(position).getName());

        holder.strength.setText(context.getString(R.string.strength_shot, transformers.get(position).getStrength()));
        holder.intelligence.setText(context.getString(R.string.intelligence_shot, transformers.get(position).getIntelligence()));
        holder.speed.setText(context.getString(R.string.speed_shot, transformers.get(position).getSpeed()));
        holder.endurance.setText(context.getString(R.string.endurance_shot, transformers.get(position).getEndurance()));
        holder.firepower.setText(context.getString(R.string.firepower_shot, transformers.get(position).getFirepower()));
        holder.rank.setText(context.getString(R.string.rank, transformers.get(position).getRank()));
        holder.courage.setText(context.getString(R.string.courage, transformers.get(position).getCourage()));
        holder.skill.setText(context.getString(R.string.skill, transformers.get(position).getSkill()));

        holder.itemView.setOnClickListener(v -> rxBus.post(new TransformerSelectedEvent(transformers.get(holder.getAdapterPosition()))));
    }

    @Override
    public int getItemCount() {
        return transformers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.strength)
        TextView strength;
        @BindView(R.id.intelligence)
        TextView intelligence;
        @BindView(R.id.speed)
        TextView speed;
        @BindView(R.id.endurance)
        TextView endurance;
        @BindView(R.id.rank)
        TextView rank;
        @BindView(R.id.courage)
        TextView courage;
        @BindView(R.id.firepower)
        TextView firepower;
        @BindView(R.id.skill)
        TextView skill;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
