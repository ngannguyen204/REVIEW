package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import models.Product;
import com.example.nguyenthibaongan_k224111493_m02.R;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private List<Product> products;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.imgProduct = convertView.findViewById(R.id.imgProductItem);
            holder.txtName = convertView.findViewById(R.id.txtProductName);
            holder.txtPrice = convertView.findViewById(R.id.txtProductPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);
        holder.txtName.setText(product.getProductName());
        holder.txtPrice.setText(String.format("$%.2f", product.getUnitPrice()));
        holder.imgProduct.setImageResource(product.getImageLink());

        return convertView;
    }

    static class ViewHolder {
        ImageView imgProduct;
        TextView txtName;
        TextView txtPrice;
    }
}