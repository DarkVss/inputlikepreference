package ru.modzi.cust_field;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import ru.tinkoff.decoro.Mask;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class FormattedInputLikePreference extends InputLikePreference {
    private FormatWatcher formatWatcher;
    private Slot[] format = PredefinedSlots.RUS_PHONE_NUMBER;

    public FormattedInputLikePreference(Context context) {
        super(context);
    }

    public FormattedInputLikePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormattedInputLikePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initControl() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(), R.style.DialogTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_input_string);
                dialog.setCancelable(true);

                final TextView dialogLabel = dialog.findViewById(R.id.label);
                dialogLabel.setText(_label);
                final EditText dialogValue = dialog.findViewById(R.id.value);
                dialogValue.setText("0");
                dialogValue.setInputType(InputType.TYPE_CLASS_PHONE);

                formatWatcher = new MaskFormatWatcher(
                        MaskImpl.createTerminated(format) // маска для серии и номера
                );
                formatWatcher.installOn(dialogValue);

                Button dialogButton = dialog.findViewById(R.id.button);
                dialogButton.setText(_button);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String valueDialog = dialogValue.getText().toString();
                        if (checkValidValue(valueDialog)) {
                            _value = valueDialog;
                            value.setText(!_value.equals("") ? _value : _hintText);
                        } else {
                            _value = null;
                            value.setText(_hintText);
                        }
                        formatWatcher.removeFromTextView();
                    }
                });

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(dialogValue.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                        formatWatcher.removeFromTextView();
                        dialog.cancel();
                    }
                });
                dialog.show();
                dialogValue.setText(_value);
                Window window = dialog.getWindow();
                assert window != null;
                window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);

                dialogValue.requestFocus();
                dialogValue.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(dialogValue, InputMethodManager.SHOW_IMPLICIT);
                    }
                }, 1);
            }
        });
    }

    private boolean checkValidValue(String value) {
        value = value.replaceAll("[^\\d.]", "");

        return value.length() == 11;
    }

    @Override
    public void setValue(String value) {
        super.setValue(this.checkValidValue(value) ? value : null);
    }

    /*public Slot[] getFormat() {
        return this.format;
    }

    public void setFormat(Slot[] phoneFormat) {
        this.format = phoneFormat;
    }*/
}
