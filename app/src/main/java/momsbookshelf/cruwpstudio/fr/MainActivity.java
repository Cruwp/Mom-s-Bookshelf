package momsbookshelf.cruwpstudio.fr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.gson.Gson;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    private CameraView cameraView;
    private View loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        View scanBtn = findViewById(R.id.scan);
        cameraView = findViewById(R.id.camera);
        loader = findViewById(R.id.loader);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.capturePicture();
            }
        });
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                // scan l'image
                scanImage(picture);
            }
        });
    }

    private void scanImage(byte[] picture) {

        Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector();
        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
            @Override
            public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                cameraView.stop();
                if (!barcodes.isEmpty()) {
                    FirebaseVisionBarcode barcode = barcodes.get(0);
                    Toast.makeText(MainActivity.this, "Recherche en cours", Toast.LENGTH_LONG).show();
                    downloadDatas(barcode.getRawValue());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Task failed with an exception
                // ...
                Toast.makeText(MainActivity.this, "Aucun code barre n'a été trouvé", Toast.LENGTH_LONG).show();
                cameraView.start();
            }
        });
    }

    private void downloadDatas(String rawValue) {
        TextView barcode = findViewById(R.id.barcode);
        final TextView title = findViewById(R.id.title);
        final TextView author = findViewById(R.id.author);
        final ImageView cover = findViewById(R.id.cover);

        title.setText(null);
        author.setText(null);
        barcode.setText(null);
        cover.setImageBitmap(null);
        loader.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openlibrary.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final String isbn = "ISBN:" + rawValue;
        retrofit.create(APIServices.class).listRepos(isbn, "json", "data").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Book b;
                loader.setVisibility(View.GONE);
                try {
                    JSONObject json = new JSONObject(response.body().string());

                    Gson gson = new Gson();
                    b = gson.fromJson(json.getJSONObject(isbn).toString(), Book.class);

                    if (b != null) {
                        title.setText(b.title);
                        author.setText(b.authors[0].name + " (" + b.publish_date + ")");

                        if (b.cover.medium != null) {
                            Picasso.get().load(b.cover.medium).into(cover);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cameraView.start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loader.setVisibility(View.GONE);
                cameraView.start();
            }
        });

        barcode.setText(rawValue);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }
}
