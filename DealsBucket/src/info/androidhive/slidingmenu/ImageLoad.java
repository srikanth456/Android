package info.androidhive.slidingmenu;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoad extends AsyncTask<String, Void, Bitmap> {

    private String url;
    private final WeakReference<ImageView> imageViewReference;

    public ImageLoad(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    protected Bitmap doInBackground(String... params) {
        url = params[0];
        try {
            return BitmapFactory.decodeStream(new URL(url).openConnection()
                    .getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(Bitmap result) {
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(result);
            }
        }
    }

    protected void onPreExecute() {
        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageResource(R.drawable.loading_icon);
            }
        }
    }
}
