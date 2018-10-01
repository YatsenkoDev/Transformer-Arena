package com.aequilibrium.assignment.transfarena.preview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aequilibrium.assignment.transfarena.R;
import com.aequilibrium.assignment.transfarena.model.Transformer;
import com.aequilibrium.assignment.transfarena.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParameterListAdapter extends RecyclerView.Adapter<ParameterListAdapter.ViewHolder> {

    private final Context context;
    private Transformer transformer;
    private SparseIntArray parametersValues = new SparseIntArray();

    public ParameterListAdapter(Context context) {
        this.context = context;
    }

    public ParameterListAdapter(Context context, Transformer transformer) {
        this.context = context;
        this.transformer = transformer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.parameter_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.parameterName.setText(context.getString(Constants.PARAMETERS.get(position), holder.parameterSeekBar.getProgress() + 1));
        holder.parameterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
                holder.parameterName.setText(context.getString(Constants.PARAMETERS.get(holder.getAdapterPosition()), value + 1));
                parametersValues.put(holder.getAdapterPosition(), value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setupParameter(holder.parameterSeekBar, position);
    }

    @Override
    public int getItemCount() {
        return Constants.PARAMETERS.size();
    }

    public SparseIntArray getParametersValues() {
        return parametersValues;
    }

    private void setupParameter(SeekBar parameterSeekBar, int position) {
        switch (position) {
            case 0: {
                parameterSeekBar.setProgress(transformer != null ? transformer.getStrength() : 0);
                break;
            }
            case 1: {
                parameterSeekBar.setProgress(transformer != null ? transformer.getIntelligence() : 0);
                break;
            }
            case 2: {
                parameterSeekBar.setProgress(transformer != null ? transformer.getSpeed() : 0);
                break;
            }
            case 3: {
                parameterSeekBar.setProgress(transformer != null ? transformer.getEndurance() : 0);
                break;
            }
            case 4: {
                parameterSeekBar.setProgress(transformer != null ? transformer.getRank() : 0);
                break;
            }
            case 5: {
                parameterSeekBar.setProgress(transformer != null ? transformer.getCourage() : 0);
                break;
            }
            case 6: {
                parameterSeekBar.setProgress(transformer != null ? transformer.getFirepower() : 0);
                break;
            }
            case 7: {
                parameterSeekBar.setProgress(transformer != null ? transformer.getSkill() : 0);
                break;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.parameter_name)
        TextView parameterName;
        @BindView(R.id.parameter_seek_bar)
        SeekBar parameterSeekBar;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            parameterSeekBar.setMax(context.getResources().getInteger(R.integer.parmeter_max_value) - 1);
        }
    }
}