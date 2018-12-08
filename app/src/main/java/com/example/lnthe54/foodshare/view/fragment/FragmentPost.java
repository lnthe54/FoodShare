package com.example.lnthe54.foodshare.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.model.Messages;
import com.example.lnthe54.foodshare.service.APIService;
import com.example.lnthe54.foodshare.utils.ReadPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class FragmentPost extends Fragment {
    private static final int REQUEST_CODE = 0;
    private static final String TAG = "FragmentPost";
    private static final String UPLOAD_URL = "http://192.168.1.244/androidwebservice/adddata.php/";
    private static String PATH = "";
    public static FragmentPost instance;

    public static FragmentPost getInstance() {
        if (instance == null) {
            instance = new FragmentPost();
        }

        return instance;
    }

    private View view;
    private Unbinder unbinder;
    private String foodName;
    private String foodPrice;
    private String foodTime;
    private String foodAdd;
    private String foodDesc;
    private int areaID;
    private String imgName;
    private String imgCode;
    private Bitmap bitmap;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_price)
    EditText etPrice;

    @BindView(R.id.et_time)
    EditText etTime;

    @BindView(R.id.et_address)
    EditText etAddress;

    @BindView(R.id.et_description)
    EditText etDesc;

    @BindView(R.id.img_food)
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.img_food)
    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            PATH = ReadPathUtil.getPath(getContext(), data.getData());
            Uri uri = Uri.fromFile(new File(PATH));
            imgName = PATH.substring(PATH.lastIndexOf("/") + 1);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(getContext()).load(uri).into(imageView);
        }
    }

    public String getBitMap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @OnClick(R.id.btn_post)
    public void uploadFood() {
        foodName = etName.getText().toString();
        foodPrice = etPrice.getText().toString();
        foodTime = etTime.getText().toString();
        foodAdd = etAddress.getText().toString();
        foodDesc = etDesc.getText().toString();
        areaID = 1;
        imgCode = getBitMap(bitmap);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UPLOAD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiUpload = retrofit.create(APIService.class);
        Call<Messages> call = apiUpload.uploadFood(foodName, foodPrice, imgCode, imgName, foodTime, foodAdd, foodDesc, areaID);
        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Messages message = response.body();
                if (message.getMessage().equals("Success")) {
                    Toast.makeText(getContext(), "Upload Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
