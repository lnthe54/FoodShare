package com.example.lnthe54.foodshare.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.model.Messages;
import com.example.lnthe54.foodshare.service.APIService;
import com.example.lnthe54.foodshare.utils.ConfigIP;
import com.example.lnthe54.foodshare.utils.ReadPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * @author lnthe54 on 11/22/2018
 * @project FoodShare
 */
public class FragmentSignUp extends Fragment {
    private static final int REQUEST_CODE = 0;
    private static String PATH = "";
    private static String URL_REGISTER = "http://" + ConfigIP.IP_ADDRESS + "/androidwebservice/register.php/";

    public static FragmentSignUp instance;

    public static FragmentSignUp getInstance() {
        if (instance == null) {
            instance = new FragmentSignUp();
        }

        return instance;
    }

    private View view;
    private Unbinder unbinder;
    private String imgName;
    private String imgCode;
    private Bitmap bitmap;
    private String nameUser;
    private String mailUser;
    private String password;
    private String cfPassword;

    private AlertDialog registerDialog;


    @BindView(R.id.user_avatar)
    CircleImageView imgAvatar;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_password_cf)
    EditText etPasswordConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        registerDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
        registerDialog.setCancelable(false);
    }

    @OnClick(R.id.user_avatar)
    public void chooseAvatar() {
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
            Glide.with(getContext()).load(uri).into(imgAvatar);
        }
    }

    public String getBitMap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @OnClick(R.id.btn_register)
    public void registerUser() {
        registerDialog.show();

        nameUser = etName.getText().toString();
        mailUser = etUsername.getText().toString();
        password = etPassword.getText().toString();
        cfPassword = etPasswordConfirm.getText().toString();

        imgCode = getBitMap(bitmap);

        if (password.equals(cfPassword)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_REGISTER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService apiUpload = retrofit.create(APIService.class);
            Call<Messages> call = apiUpload.register(imgCode, imgName, nameUser, mailUser, password);

            call.enqueue(new Callback<Messages>() {
                @Override
                public void onResponse(Call<Messages> call, Response<Messages> response) {
                    Messages message = response.body();
                    if (message.getMessage().equals("Success")) {
                        registerDialog.dismiss();
                        openFrgSignIn();

                        Toast.makeText(getContext(), "Đăng Kí Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Messages> call, Throwable t) {
                }
            });
        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }

    }

    private void openFrgSignIn() {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, FragmentSignIn.getInstance());
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
