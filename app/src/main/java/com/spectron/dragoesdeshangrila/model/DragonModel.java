package com.spectron.dragoesdeshangrila.model;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.spectron.dragoesdeshangrila.R;

public class DragonModel {

    private boolean selected;
    private boolean visible;
    private Integer id;
    private View view;

    public DragonModel(View view) {
        this.id = view.getId();
        this.view = view;
        this.visible = true;
    }

    public Integer getId() {
        return id;
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

    public void setVisible() {
        this.visible = true;
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
            this.toggleBackground(R.drawable.dragaoverdeselecionado);
        } else {
            this.toggleBackground(R.drawable.dragaoverde);
        }
    }

    private void toggleBackground(int id) {
        ((AppCompatImageView) view).setImageResource(id);
    }

}
