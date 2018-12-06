package com.myapps.tc_android.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.CarBuilder;
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.Car;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import in.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener;
import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCarUserActivity extends AppCompatActivity implements Callback<ApiResponse<Car>> {

    @BindView(R.id.editText_addCar_name)
    EditText editTextAddCarName;
    @BindView(R.id.editText_addCar_factory)
    EditText editTextAddCarFactory;
    @BindView(R.id.editText_addCar_year)
    EditText editTextAddCarYear;
    @BindView(R.id.editText_addCar_kilometer)
    EditText editTextAddCarKilometer;
    @BindView(R.id.editText_addCar_color)
    EditText editTextAddCarColor;
    @BindView(R.id.checkbox_addCar_automate)
    CheckBox editTextAddCarAutomate;
    @BindView(R.id.editText_addCar_price)
    EditText editTextAddCarPrice;
    @BindView(R.id.button_addCar)
    Button buttonAddCar;
    @BindView(R.id.imageview_previewImage)
    CircleImageView imageviewPreviewImage;
    private ImagePicker imagePicker;
    private String TAG = AddCarUserActivity.class.getSimpleName();
    private File image;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_admin);
        ButterKnife.bind(this);
        imagePicker = new ImagePicker();
    }

    @OnClick(R.id.button_addCar)
    public void onViewClicked() {
        if (validate()) {
            CarBuilder builder = new CarBuilder()
                    .setName(editTextAddCarName.getText().toString())
                    .setColor(editTextAddCarColor.getText().toString())
                    .setFactory(editTextAddCarFactory.getText().toString())
                    .setKilometer(Integer.parseInt(editTextAddCarKilometer.getText().toString()))
                    .setPrice(Integer.parseInt(editTextAddCarPrice.getText().toString()))
                    .setYear(Integer.parseInt(editTextAddCarYear.getText().toString()))
                    .setAutomate(editTextAddCarAutomate.isChecked());
            addCar(builder.createCar());
        }
    }

    private boolean validate() {
        boolean valid = true;
        String name = editTextAddCarName.getText().toString();
        String color = editTextAddCarColor.getText().toString();
        String factory = editTextAddCarFactory.getText().toString();
        String kilometer = editTextAddCarKilometer.getText().toString();
        String price = editTextAddCarPrice.getText().toString();
        String year = editTextAddCarYear.getText().toString();


        if (name.isEmpty() || name.length() > 20) {
            editTextAddCarName.setError("enter a valid name");
            requestFocus(editTextAddCarName);
            valid = false;
        } else {
            editTextAddCarName.setError(null);
        }


        if (color.isEmpty() || color.length() > 20) {
            editTextAddCarColor.setError("enter a valid color");
            requestFocus(editTextAddCarColor);
            valid = false;
        } else {
            editTextAddCarColor.setError(null);
        }


        if (factory.isEmpty() || factory.length() > 20) {
            editTextAddCarFactory.setError("enter a valid factory");
            requestFocus(editTextAddCarFactory);
            valid = false;
        } else {
            editTextAddCarFactory.setError(null);
        }


        if (kilometer.isEmpty() || kilometer.length() > 6) {
            editTextAddCarKilometer.setError("enter a valid kilometer");
            requestFocus(editTextAddCarKilometer);
            valid = false;
        } else {
            editTextAddCarKilometer.setError(null);
        }

        if (price.isEmpty() || price.length() > 10) {
            editTextAddCarPrice.setError("enter a valid price");
            requestFocus(editTextAddCarPrice);
            valid = false;
        } else {
            editTextAddCarPrice.setError(null);
        }

        if (year.isEmpty() || year.length() > 4) {
            editTextAddCarYear.setError("enter a valid year");
            requestFocus(editTextAddCarYear);
            valid = false;
        } else {
            editTextAddCarYear.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void addCar(Car car) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<Car>> call = service.addCar(car);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<ApiResponse<Car>> call, @NonNull Response<ApiResponse<Car>> response) {
        if (response.isSuccessful()) {
            if (response.body().getStatus().equals("OK")) {
                car = response.body().getObject();
                sendPhoto();
                Toast.makeText(AddCarUserActivity.this, car.getName() + " Added Successfully ", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("Add Car Error", " status : " + response.body().getStatus());
            }

        } else {
            Log.e("Connection", "Failed To Add Car : " + response.message());
        }
    }

    @Override
    public void onFailure(@NonNull Call<ApiResponse<Car>> call, @NonNull Throwable t) {
        Log.e("Connection", "Failed To Connect : " + t.getMessage());

    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == RESULT_OK) {
            imagePicker.addOnCompressListener(new ImageCompressionListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onCompressed(String filePath) {
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    imageviewPreviewImage.setImageBitmap(bitmap);
                    image = new File(filePath);
                }
            });
        }
        String filePath = imagePicker.getImageFilePath(data);
        if (filePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imageviewPreviewImage.setImageBitmap(bitmap);
            image = new File(filePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.button_chooseImage)
    public void onPickImage() {
        if (isStoragePermissionGranted()) {
            imagePicker.withActivity(this)
                    .withCompression(false)
                    .start();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            onPickImage();
        }
    }

    private void sendPhoto() {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", image.getName(), reqFile);
        Call req = service.uploadImage(body, car.getId());
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, response.body().toString());
                    Log.i(TAG, "image " + image.getName() + " uploaded successfully");
                    finish();
                    startActivity(new Intent(AddCarUserActivity.this, HomePageActivity.class));
                } else {
                    Log.e(TAG, "uploading image failed with code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
