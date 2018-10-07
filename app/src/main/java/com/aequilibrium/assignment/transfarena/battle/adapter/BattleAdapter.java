package com.aequilibrium.assignment.transfarena.battle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.MathUtils;
import com.aequilibrium.assignment.transfarena.utils.TeamUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for battle list
 * Displays two rows of competitive teams
 * Shows Transformer's name, avatar and base battle params
 */
public class BattleAdapter extends RecyclerView.Adapter<BattleAdapter.ViewHolder> {

    private final Context context;
    private final List<Transformer> autobots;
    private final List<Transformer> decepticons;

    /**
     * Constructor
     *
     * @param autobots    list used for the left row
     * @param decepticons list used for the right row
     */
    public BattleAdapter(Context context, List<Transformer> autobots, List<Transformer> decepticons) {
        this.context = context;
        this.autobots = autobots;
        this.decepticons = decepticons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.battle_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setupParamsForTeam(holder.autobotsViewHolder, position < autobots.size() ? autobots.get(position) : null);
        setupParamsForTeam(holder.decepticonsViewHolder, position < decepticons.size() ? decepticons.get(position) : null);
    }

    private void setupParamsForTeam(TeamHolder holder, @Nullable Transformer transformer) {
        if (transformer != null) {
            Picasso.get().load(TeamUtils.getTransformerAvatar(transformer)).into(holder.avatar);
            holder.name.setText(transformer.getName());
            holder.strength.setText(context.getString(R.string.strength_shot, transformer.getStrength()));
            holder.courage.setText(context.getString(R.string.courage_shot, transformer.getCourage()));
            holder.skill.setText(context.getString(R.string.skill, transformer.getSkill()));
            holder.rating.setText(context.getString(R.string.rating, MathUtils.calculateOverallRating(transformer)));
        }
        holder.parent.setVisibility(transformer != null ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return autobots.size() > decepticons.size() ? autobots.size() : decepticons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.autobots_team)
        ConstraintLayout autobotsTeam;
        @BindView(R.id.decepticons_team)
        ConstraintLayout decepticonsTeam;
        private AutobotsViewHolder autobotsViewHolder;
        private DecepticonssViewHolder decepticonsViewHolder;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            autobotsViewHolder = new AutobotsViewHolder(autobotsTeam);
            decepticonsViewHolder = new DecepticonssViewHolder(decepticonsTeam);
        }
    }

    class TeamHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.strength)
        TextView strength;
        @BindView(R.id.courage)
        TextView courage;
        @BindView(R.id.skill)
        TextView skill;
        @BindView(R.id.rating)
        TextView rating;
        private View parent;

        TeamHolder(View view) {
            ButterKnife.bind(this, view);
            parent = view;
        }
    }

    private class AutobotsViewHolder extends TeamHolder {

        AutobotsViewHolder(View view) {
            super(view);
        }
    }

    private class DecepticonssViewHolder extends TeamHolder {

        DecepticonssViewHolder(View view) {
            super(view);
        }
    }
}
