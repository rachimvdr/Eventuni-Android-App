package com.unicode.eventuni.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.Toast;

import com.unicode.eventuni.Config;
import com.unicode.eventuni.Model.PostPutDelEvent;
import com.unicode.eventuni.R;
import com.unicode.eventuni.Rest.ApiClient;
import com.unicode.eventuni.Rest.ApiInterface;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertActivity extends AppCompatActivity {

    EditText edtName, edtDescription, edtLongdescription, edtLokasi, edtTiket, edtWaktu, edtCp, edtLink;
    Spinner spnrKategori;
    RadioGroup rgKegiatan;
    Button btnGalery, btSubmit;
    ImageView imgHolder;

    private String mediaPath;
    private String postPath;

    ApiInterface mApiInterface;
    private static final int REQUEST_PICK_PHOTO = Config.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = Config.REQUEST_WRITE_PERMISSION;
    private static final String INSERT_FLAG = Config.INSERT_FLAG;

    // Akses Izin Ambil Gambar dari Storage
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveImageUpload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        // Identifikasi Komponen Action Bar
        String actionBarTitle;
        actionBarTitle = "Tambah Event";
        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Identifikasi Komponen Form
        edtName = (EditText) findViewById(R.id.edt_name);
        edtDescription = (EditText) findViewById(R.id.edt_description);
        edtLongdescription = (EditText) findViewById(R.id.edt_longdescription);
        spnrKategori = findViewById(R.id.spnr_kategori);
        rgKegiatan = findViewById(R.id.rg_kegiatan);
        edtLokasi = (EditText) findViewById(R.id.edt_lokasi);
        edtTiket = (EditText) findViewById(R.id.edt_tiket);
        edtWaktu = (EditText) findViewById(R.id.edt_waktu);
        edtCp = (EditText) findViewById(R.id.edt_cp);
        edtLink = (EditText) findViewById(R.id.edt_link);
        imgHolder = (ImageView) findViewById(R.id.imgHolder);
        btnGalery = (Button) findViewById(R.id.btn_galery);
        btSubmit = (Button) findViewById(R.id.btn_submit);


        // Definisi API
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        // Fungsi Tombol Pilih Galery
        btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });

        // Fungsi Tombol Simpan
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
    }

    // Akses Izin Ambil Gambar dari Storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Ambil Image Dari Galeri dan Foto
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    imgHolder.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }

    // Simpan Data
    private void saveImageUpload(){
        int kegiatanSw = rgKegiatan.getCheckedRadioButtonId();
        String kegiatan = "";

        switch (kegiatanSw) {
            case R.id.rb_online:
                kegiatan = "Online";
                break;
            case R.id.rb_offline:
                kegiatan = "Offline";
                break;
        }

        if (mediaPath== null)
        {
            Toast.makeText(getApplicationContext(), "Silahkan upload poster", Toast.LENGTH_LONG).show();
        }
        else {
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imagefile.getName(), reqBody);

            Call<PostPutDelEvent> postEventCall = mApiInterface.postEvent(partImage, RequestBody.create(MediaType.parse("text/plain"), edtName.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtDescription.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtLongdescription.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), spnrKategori.getSelectedItem().toString()), RequestBody.create(MediaType.parse("text/plain"), kegiatan), RequestBody.create(MediaType.parse("text/plain"), edtLokasi.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtTiket.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtWaktu.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtCp.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtLink.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), INSERT_FLAG));
            postEventCall.enqueue(new Callback<PostPutDelEvent>() {
                @Override
                public void onResponse(Call<PostPutDelEvent> call, Response<PostPutDelEvent> response) {
                    MainActivity.ma.refresh();
                    finish();
                }

                @Override
                public void onFailure(Call<PostPutDelEvent> call, Throwable t) {
                    Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                    //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                    Toast.makeText(getApplicationContext(), "Silahkan tunggu", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    // Cek Versi Android Untuk Dapatkan Izin
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            saveImageUpload();
        }
    }

    // Menu Kembali Ke Home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}