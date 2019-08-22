package ru.modzi.cust_field;


import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListLikePreference extends InputLikePreference {
    public HashMap<String, String> variants = new HashMap<String, String>();

    public ListLikePreference(Context context) {
        super(context);
    }

    public ListLikePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListLikePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initControl() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled}},
                        new int[]{Color.parseColor("#4b4b4b"), Color.parseColor("#4b4b4b")}
                );
                final Dialog dialog = new Dialog(getContext(), R.style.DialogTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_input_list_once);
                dialog.setCancelable(true);

                final ArrayList<String> _variants = new ArrayList<String>();
                for (Map.Entry<String, String> entry : variants.entrySet()) {
                    _variants.add(entry.getKey());
                }

                final TextView dialogLabel = dialog.findViewById(R.id.label);
                dialogLabel.setText(_label);
                final RadioGroup dialogRadioGroup = dialog.findViewById(R.id.rGroup);
                final Button dialogButton = dialog.findViewById(R.id.button);
                dialogButton.setText(_button);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        for (int i = 0; i < dialogRadioGroup.getChildCount(); i++) {
                            RadioButton _radioButton = (RadioButton) dialogRadioGroup.getChildAt(i);
                            if (_radioButton.isChecked()) {
                                setValue(_variants.get(i));
                                break;
                            }
                        }
                    }
                });

                int index = 100;
                for (String key : _variants) {
                    index++;
                    RadioButton rButton = new RadioButton(getContext());

                    rButton.setText(variants.get(key));
                    rButton.setTextColor(Color.rgb(0, 0, 0));
                    rButton.setId(index);
                    rButton.setButtonTintList(colorStateList);

                    if (_value != null && _value.contains(key)) {
                        rButton.setChecked(true);
                    }

                    dialogRadioGroup.addView(rButton);
                }

                dialog.show();
                Window window = dialog.getWindow();
                assert window != null;
                window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);
            }
        });
    }

    @Override
    public void setValue(String value) {
        this._value = (this.variants.containsKey(value)) ? value : null;

        boolean setDefaultValue = this._value == null || this._value.equals("");

        this.value.setText(setDefaultValue ? this._hintText : this.variants.get(this._value));
    }

    @Override
    public String getValue() {
        return variants.get(this._value);
    }

    public HashMap<String, String> getVariants() {
        return this.variants;
    }

    public void setVariants(HashMap<String, String> variants) {
        this.variants = variants;
    }
}
