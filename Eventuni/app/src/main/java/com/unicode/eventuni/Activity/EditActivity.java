package com.unicode.eventuni.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.unicode.eventuni.Config;
import com.unicode.eventuni.Model.PostPutDelEvent;
import com.unicode.eventuni.R;
import com.unicode.eventuni.Rest.ApiClient;
import com.unicode.eventuni.Rest.ApiInterface;

import java.io.File;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    EditText edtName, edtDescription, edtLongdescription, edtLokasi, edtTiket, edtWaktu, edtCp, edtLink;
    String ID;
    Spinner spnrKategori;
    String[] ArrayKategori = new String[]{"Festival", "Hiburan", "Kompetisi", "Pameran", "Seminar", "Sosial", "Talkshow", "Workshop"};
    RadioGroup rgKegiatan;
    ImageView imgHolder;
    Button btnGalery, btUpdate;

    private String mediaPath;
    private String postPath;

    ApiInterface mApiInterface;
    private static final int REQUEST_PICK_PHOTO = Config.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = Config.REQUEST_WRITE_PERMISSION;

    private final int ALERT_DIALOG_CLOSE = Config.ALERT_DIALOG_CLOSE;
    private final int ALERT_DIALOG_DELETE = Config.ALERT_DIALOG_DELETE;
    private static final String UPDATE_FLAG = Config.UPDATE_FLAG;

    // Akses Izin Ambil Gambar dari Storage
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateImageUpload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Identifikasi Komponen Action Bar
        String actionBarTitle;
        actionBarTitle = "Detail Event";
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
        btUpdate = (Button) findViewById(R.id.btn_submit);

        // Identifikasi intent ke Komponen Form
        Intent mIntent = getIntent();
        ID = mIntent.getStringExtra("Id");
        edtName.setText(mIntent.getStringExtra("Name"));
        /*edtDescription.setText(mIntent.getStringExtra("Description"));*/
        edtLongdescription.setText(mIntent.getStringExtra("Longdescription"));
        /*spnrKategori.setSelection(Arrays.asList(ArrayKategori).indexOf(mIntent.getStringExtra("Kategori")));*/
        /*rgKegiatan.check(getIdRG(mIntent.getStringExtra("Kegiatan")));*/
        edtLokasi.setText(mIntent.getStringExtra("Lokasi"));
        edtTiket.setText(mIntent.getStringExtra("Tiket"));
        edtWaktu.setText(mIntent.getStringExtra("Waktu"));
        edtCp.setText(mIntent.getStringExtra("Cp"));
        edtLink.setText(mIntent.getStringExtra("Link"));

        // Masukan Gambar Ke Image View Gunakan Glide
        Glide.with(EditActivity.this)
                .load(Config.IMAGES_URL + mIntent.getStringExtra("Image"))
                .apply(new RequestOptions().override(0, 0))
                .into(imgHolder);

        // Definisi API
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        // Fungsi Tombol Pilih Galery
        /*btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });*/

        // Fungsi Tombol Update
        /*btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImageUpload();
            }
        });*/
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

    public int getIdRG (String rg){
        int id;
        if(rg.equals("Online")){
            id = R.id.rb_online;
        }
        else{
            id = R.id.rb_offline;
        }
        return id;
    }

    // Update Data
    private void updateImageUpload() {
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
            Toast.makeText(getApplicationContext(), "Silahkan update poster", Toast.LENGTH_LONG).show();
        }
        else {
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imagefile.getName(), reqBody);

            Call<PostPutDelEvent> putEventCall = mApiInterface.postUpdateEvent(partImage, RequestBody.create(MediaType.parse("text/plain"), ID), RequestBody.create(MediaType.parse("text/plain"), edtName.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtDescription.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtLongdescription.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), spnrKategori.getSelectedItem().toString()), RequestBody.create(MediaType.parse("text/plain"), kegiatan), RequestBody.create(MediaType.parse("text/plain"), edtLokasi.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtTiket.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtWaktu.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtCp.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), edtLink.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), UPDATE_FLAG));
            putEventCall.enqueue(new Callback<PostPutDelEvent>() {
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
            updateImageUpload();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }*/

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Kembali";
            dialogMessage = "Apakah anda ingin kembali melihat event yang lain?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus event ini?";
            dialogTitle = "Hapus Event";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose) {
                            finish();
                        } else {
                            // Kode Hapus
                            if (ID.trim().isEmpty()==false){
                                Call<PostPutDelEvent> deleteEvent = mApiInterface.deleteEvent(ID);
                                deleteEvent.enqueue(new Callback<PostPutDelEvent>() {
                                    @Override
                                    public void onResponse(Call<PostPutDelEvent> call, Response<PostPutDelEvent> response) {
                                        MainActivity.ma.refresh();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<PostPutDelEvent> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Silahkan tunggu", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "Silahkan tunggu", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}