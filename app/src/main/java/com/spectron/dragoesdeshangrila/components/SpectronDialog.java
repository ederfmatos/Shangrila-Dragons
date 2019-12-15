package com.spectron.dragoesdeshangrila.components;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.spectron.dragoesdeshangrila.R;
import com.spectron.dragoesdeshangrila.enumerations.LevelEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SpectronDialog extends Dialog {

    private EditText firstInput;
    private EditText secondInput;
    private Activity context;
    private Spinner spinner;

    public SpectronDialog(Activity context) {
        super(context);
        this.context = context;
    }

    private String firstString;
    private String secondString;
    private String message;

    private Consumer<List<String>> onClick;
    private Consumer<LevelEnum> consumer;

    private List<LevelEnum> optionsSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sample_spectron_dialog);

        findViewById(R.id.buttonOk).setOnClickListener(this::onClickButton);

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        firstInput = findViewById(R.id.firstInput);
        secondInput = findViewById(R.id.secondInput);
        TextView messageTextView = findViewById(R.id.message);
        spinner = findViewById(R.id.spinner);

        firstInput.setHint(firstString);
        secondInput.setHint(secondString);
        messageTextView.setText(message);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        firstInput.addTextChangedListener(watcher);
        secondInput.addTextChangedListener(watcher);

        if (optionsSelect != null && !optionsSelect.isEmpty()) {
            firstInput.setVisibility(View.GONE);
            secondInput.setVisibility(View.GONE);

            final List<String> items = optionsSelect.stream().map(item -> context.getString(item.getName()).toUpperCase()).collect(Collectors.toList());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    context,
                    android.R.layout.simple_list_item_1,
                    items
            );

            spinner.setAdapter(adapter);
        } else {
            spinner.setVisibility(View.GONE);
        }
        validateFields();
    }

    public SpectronDialog setFirstTextHint(String message) {
        this.firstString = message;
        return this;
    }

    public SpectronDialog setSecondTextHint(String message) {
        this.secondString = message;
        return this;
    }

    public SpectronDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public SpectronDialog setOnClickListener(Consumer<List<String>> onClick) {
        this.onClick = onClick;
        return this;
    }

    public SpectronDialog setOptionsSelect(List<LevelEnum> list) {
        this.optionsSelect = list;
        return this;
    }

    public SpectronDialog onSelect(Consumer<LevelEnum> consumer) {
        this.consumer = consumer;
        return this;
    }

    @Override
    public void show() {
        super.show();
    }

    private void onClickButton(View view) {
        if (view.isEnabled()) {
            if (onClick != null) {
                onClick.accept(Arrays.asList(firstInput.getText().toString(), secondInput.getText().toString()));
            } else if (consumer != null) {
                int index = spinner.getSelectedItemPosition();
                consumer.accept(optionsSelect.get(index));
            }
            dismiss();
        }
    }

    private void validateFields() {
        if (optionsSelect != null && !optionsSelect.isEmpty()) {
            return;
        }

        findViewById(R.id.buttonOk).setEnabled(!(firstInput.getText().toString().isEmpty() || secondInput.getText().toString().isEmpty()));
    }

}