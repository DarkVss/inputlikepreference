package ru.modzi.cust_field;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssociativeListAdapter extends ArrayAdapter {
    private static ArrayList<String> variants = new ArrayList<String>();
    private static HashMap<String, String> fullVariants = new HashMap<String, String>();
    private final Context mContext;

    public AssociativeListAdapter(Context context, int textViewResourceId, HashMap<String, String> _variants) {
        super(context, textViewResourceId, variants);

        variants.clear();
        fullVariants.clear();
        fullVariants = _variants;

        for (Map.Entry<String, String> entry : fullVariants.entrySet()) {
            variants.add(entry.getKey());

        }

        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View grid;
        if (convertView == null) {
            TextView label;

            convertView = new TextView(mContext);
            label = (TextView) convertView;
            label.setText(fullVariants.get(variants.get(position)));
            label.setTextSize(18);
            label.setTextColor(0xFF000000);
            label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            return (convertView);

        } else {
            grid = convertView;
        }
        return grid;
    }

    // возвращает содержимое выделенного элемента списка
    public String getSelectedKey(int position) {
        return variants.get(position);
    }

}