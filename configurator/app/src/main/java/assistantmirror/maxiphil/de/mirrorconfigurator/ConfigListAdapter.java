package assistantmirror.maxiphil.de.mirrorconfigurator;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import assistantmirror.maxiphil.de.mirrorconfigurator.config.Config;
import assistantmirror.maxiphil.de.mirrorconfigurator.config.ConfigItem;
import assistantmirror.maxiphil.de.mirrorconfigurator.config.MarkAsCaption;

public class ConfigListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final List<ConfigItem> items;


    ConfigListAdapter(Context context, List<ConfigItem> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    public List<ConfigItem> getItems() {
        return items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.items.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ConfigItem item = this.items.get(position);

        if(convertView == null || !item.getClass().toString().equals(convertView.getTag())) {
            // If convertView is null we have to inflate a new layout
            if (item.getTypeParameterClass().equals(Boolean.class)) {
                convertView = this.inflater.inflate(R.layout.config_entry_checkbox_list_item, parent, false);
                convertView.setTag(item.getTypeParameterClass());
            } else if (item.getTypeParameterClass().equals(String.class)) {
                convertView = this.inflater.inflate(R.layout.config_entry_text_list_item, parent, false);
                convertView.setTag(item.getTypeParameterClass());
            } else if (item.getTypeParameterClass().equals(MarkAsCaption.class)){
                convertView = this.inflater.inflate(R.layout.config_entry_caption_item, parent, false);
                convertView.setTag(item.getTypeParameterClass());
            }
        }

        if (convertView == null)
            return null;

        if (item.getTypeParameterClass().equals(Boolean.class)) {
            // Retrieve the view holder from the convertView
            final ConfigItem<Boolean> itemBool = ConfigItem.cast(item);

            // Bind the values to the views
            TextView key = convertView.findViewById(R.id.list_item_key);
            CheckBox value = convertView.findViewById(R.id.list_item_value_bool);

            value.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    itemBool.setValue(b);
                }
            });
            key.setText(itemBool.getKey());
            value.setChecked(itemBool.getValue());
        } else if (item.getTypeParameterClass().equals(String.class)) {
            // Retrieve the view holder from the convertView
            final ConfigItem<String> itemString = ConfigItem.cast(item);

            // Bind the values to the views
            TextView key = convertView.findViewById(R.id.list_item_key);
            EditText value = convertView.findViewById(R.id.list_item_value_string);
            value.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    itemString.setValue(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            key.setText(itemString.getKey());
            value.setText(itemString.getValue());
        }else if (item.getTypeParameterClass().equals(MarkAsCaption.class)){
            final ConfigItem<String> captionString = ConfigItem.cast(item);
            TextView caption = convertView.findViewById(R.id.list_item_key);
            caption.setText(captionString.getKey());
        }

        return convertView;
    }
}
