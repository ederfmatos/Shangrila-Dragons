package com.spectron.dragoesdeshangrila.model;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.spectron.dragoesdeshangrila.R;

public class DragonModel {

    private boolean selected;
    private boolean visible;
    private View view;

    public DragonModel(View view) {
        this.view = view;
        this.visible = true;
    }

    public Integer getId() {
        return view.getId();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        this.toggleImage();
    }

    public void toggleSelection() {
        this.setSelected(!isSelected());
    }

    public DragonModel setInvisible() {
        visible = false;
        view.setVisibility(View.INVISIBLE);
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    private void toggleImage() {
        if (isSelected()) {
            this.toggleBackground(R.drawable.dgselecionado);
        } else {
            this.toggleBackground(R.drawable.dg);
        }
    }

    private void toggleBackground(int id) {
        ((AppCompatImageView) view).setImageResource(id);
    }

}
