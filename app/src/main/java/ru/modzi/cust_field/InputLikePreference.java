package ru.modzi.cust_field;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Parent class for inputs what look like preference's inputs
 * default state it's EditText, but not recommended to use directly, only through child classes
 */
public class InputLikePreference extends RelativeLayout {
    protected final TextView value;
    protected final TextView label;
    protected final ImageView iconLeft;
    protected final ImageView iconRight;

    protected Bitmap _iconLeft;
    protected Bitmap _iconRight;

    protected String _label;
    protected String _value;
    protected String _hintText = getResources().getString(R.string.default_hint);
    protected String _button = getResources().getString(R.string.default_button);

    public InputLikePreference(Context context) {
        super(context);
        //init custom view
        this.initView();
        this.value = findViewById(R.id.value);
        this.label = findViewById(R.id.label);
        this.iconLeft = findViewById(R.id.iconLeft);
        this.iconRight = findViewById(R.id.iconRight);
        this.setView();
        this.initControl();
    }

    public InputLikePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setAttrs(context, attrs);
        //init custom view
        this.initView();
        this.value = findViewById(R.id.value);
        this.label = findViewById(R.id.label);
        this.iconLeft = findViewById(R.id.iconLeft);
        this.iconRight = findViewById(R.id.iconRight);
        this.setView();
        this.initControl();
    }

    public InputLikePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setAttrs(context, attrs);
        //init custom view
        this.initView();
        this.value = findViewById(R.id.value);
        this.label = findViewById(R.id.label);
        this.iconLeft = findViewById(R.id.iconLeft);
        this.iconRight = findViewById(R.id.iconRight);
        this.setView();
        this.initControl();
    }

    protected void setAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.InputLikePreference,
                0, 0);
        String label;
        String defaultValue;
        String button;
        try {
            label = a.getString(R.styleable.InputLikePreference_labelText);
            if (label != null && !label.equals("")) {
                this._label = label;
            }
            defaultValue = a.getString(R.styleable.InputLikePreference_hintText);
            if (defaultValue != null && !defaultValue.equals("")) {
                this._hintText = defaultValue;
            }
            button = a.getString(R.styleable.InputLikePreference_buttonText);
            if (button != null && !button.equals("")) {
                this._button = button;
            }
            /*if (R.styleable.InputLikePreference_iconLeft != 0) {
                this._iconLeft = BitmapFactory.decodeResource(getResources(), R.styleable.InputLikePreference_iconLeft);
            }
            if (R.styleable.InputLikePreference_iconRight != 0) {
                this._iconRight = BitmapFactory.decodeResource(getResources(), R.styleable.InputLikePreference_iconRight);
            }*/
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                if (attrs.getAttributeName(i).equals("iconLeft")) {
                    this._iconLeft = BitmapFactory.decodeResource(getResources(), attrs.getAttributeResourceValue(i, 0));
                }
                if (attrs.getAttributeName(i).equals("iconRight")) {
                    this._iconRight = BitmapFactory.decodeResource(getResources(), attrs.getAttributeResourceValue(i, 0));
                }
            }
        } finally {
            a.recycle();
        }
    }

    protected void initView() {
        inflate(getContext(), R.layout.input_like_preference, this);
    }

    protected void setView() {
        this.label.setText(this._label);

        boolean setDefaultValue = this._value == null || this._value.equals("");

        this.value.setText(setDefaultValue ? this._hintText : this._value);

        this.iconLeft.setImageBitmap(this._iconLeft);
        this.iconRight.setImageBitmap(this._iconRight);
    }

    /**
     * Set onClickListener for this
     */
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
                dialogValue.setText(_value);
                dialogValue.setHint(R.string.default_dialog_hint);

                Button dialogButton = dialog.findViewById(R.id.button);
                dialogButton.setText(_button);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        _value = dialogValue.getText().toString();
                        value.setText(!_value.equals("") ? _value : _hintText);
                    }
                });

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(dialogValue.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                        dialog.cancel();
                    }
                });
                dialog.show();
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

    public String getValue() {
        return this._value == null ? "" : this._value;
    }

    public void setValue(String value) {
        this._value = value;

        boolean setDefaultValue = this._value == null || this._value.equals("");

        this.value.setText(setDefaultValue ? this._hintText : this._value);
    }

    public String getLabel() {
        return this._label == null ? "" : this._label;
    }

    public void setLabel(String label) {
        this._label = label;

        this.label.setText(this._label);
    }

    public void setButton(String button) {
        this._button = button;
    }
}
