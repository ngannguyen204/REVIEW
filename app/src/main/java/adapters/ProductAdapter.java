package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nguyenthibaongan_k224111493_m02.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import models.Product;

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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.imgProduct = convertView.findViewById(R.id.imageView);
            holder.txtProductName = convertView.findViewById(R.id.txtProductname);
            holder.txtProductPrice = convertView.findViewById(R.id.txtProductPrice);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductPrice.setText(String.format("%,.0f VND", product.getUnitPrice()));

        // Show placeholder before loading image
        holder.imgProduct.setImageResource(R.mipmap.ic_launcher);

        // Cancel old task if running
        if (holder.imgProduct.getTag() instanceof LoadImageTask) {
            ((LoadImageTask) holder.imgProduct.getTag()).cancel(true);
        }

        // Load image from URL using AsyncTask
        if (product.getImageLink() != null && !product.getImageLink().isEmpty()) {
            LoadImageTask task = new LoadImageTask(holder.imgProduct);
            holder.imgProduct.setTag(task);
            task.execute(product.getImageLink());
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductPrice;
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlString = urls[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null && !isCancelled()) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}