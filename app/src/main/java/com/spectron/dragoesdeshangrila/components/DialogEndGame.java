package com.spectron.dragoesdeshangrila.components;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.spectron.dragoesdeshangrila.R;

import java.util.Objects;

public class DialogEndGame extends Dialog {

    private Activity context;

    public DialogEndGame(@NonNull Context context) {
        super(context);
        this.context = (Activity) context;
    }

    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_end_game);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        findViewById(R.id.buttonRestart).setOnClickListener(view -> restart());
        findViewById(R.id.buttonClose).setOnClickListener(view -> close());
        TextView endMessage = findViewById(R.id.textViewEndMessage);
        endMessage.setText(message);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public DialogEndGame setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public void show() {
        super.show();
    }

    private void restart() {
        context.recreate();
    }

    private void close() {
        context.finish();
    }

}