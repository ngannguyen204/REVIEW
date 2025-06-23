package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nguyenthibaongan_k224111493_m02.R;

import java.util.List;

import models.Categories;

public class CategoryAdapter extends ArrayAdapter<Categories> {
    private Context context;
    private int resource;
    private List<Categories> categories;

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Categories> categories) {
        super(context, resource, categories);
        this.context = context;
        this.resource = resource;
        this.categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.tvCategoryName = convertView.findViewById(R.id.txtCategoryName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Categories category = categories.get(position);
        holder.tvCategoryName.setText(category.getCateName());

        return convertView;
    }

    static class ViewHolder {
        TextView tvCategoryName;
    }
}