package com.aequilibrium.assignment.transfarena.preview.adapter;

import android.annotation.SuppressLint;
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

    private final int minimalValue;
    private final Context context;
    private Transformer transformer;
    private SparseIntArray parametersValues = new SparseIntArray();
    private boolean elementsEnabled = true;


    public ParameterListAdapter(Context context, Transformer transformer) {
        this.context = context;
        this.transformer = transformer;
        minimalValue = context.getResources().getInteger(R.integer.parameter_min_value);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.parameter_element, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.parameterName.setText(context.getString(Constants.PARAMETERS.get(position), holder.parameterSeekBar.getProgress() + 1));
        holder.parameterName.setTextColor(context.getResources().getColor(elementsEnabled ? R.color.black : R.color.grey));
        holder.parameterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
                holder.parameterName.setText(context.getString(Constants.PARAMETERS.get(holder.getAdapterPosition()), value + 1));
                parametersValues.put(holder.getAdapterPosition(), value + 1);
                if (!elementsEnabled) {
                    setupParameter(seekBar, holder.getAdapterPosition());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        holder.parameterSeekBar.setMax(context.getResources().getInteger(R.integer.parameter_max_value) - 1);
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
                parameterSeekBar.setProgress((transformer != null ? transformer.getStrength() : minimalValue) - 1);
                break;
            }
            case 1: {
                parameterSeekBar.setProgress((transformer != null ? transformer.getIntelligence() : minimalValue) - 1);
                break;
            }
            case 2: {
                parameterSeekBar.setProgress((transformer != null ? transformer.getSpeed() : minimalValue) - 1);
                break;
            }
            case 3: {
                parameterSeekBar.setProgress((transformer != null ? transformer.getEndurance() : minimalValue) - 1);
                break;
            }
            case 4: {
                parameterSeekBar.setProgress((transformer != null ? transformer.getRank() : minimalValue) - 1);
                break;
            }
            case 5: {
                parameterSeekBar.setProgress((transformer != null ? transformer.getCourage() : minimalValue) - 1);
                break;
            }
            case 6: {
                parameterSeekBar.setProgress((transformer != null ? transformer.getFirepower() : minimalValue) - 1);
                break;
            }
            case 7: {
                parameterSeekBar.setProgress((transformer != null ? transformer.getSkill() : minimalValue) - 1);
                break;
            }
        }
    }

    public void setElementsEnabled(boolean enabled) {
        this.elementsEnabled = enabled;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.parameter_name)
        TextView parameterName;
        @BindView(R.id.parameter_seek_bar)
        SeekBar parameterSeekBar;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            parameterSeekBar.setProgress(getItemCount());
        }
    }
}
