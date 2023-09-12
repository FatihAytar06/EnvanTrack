package com.example.mystockss.Activities;
import java.util.UUID;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;


import com.example.mystockss.R;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView imageView;
    private Button uploadBtn,takePhotoBtn,updateBtn;
    private EditText envanterAdi,envanterAdedi,birimStokFiyat,marka;

    private Uri imageUri;
    private StorageReference  storageReference;
    String[] itemInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        imageView = findViewById(R.id.photoView);
        uploadBtn = findViewById(R.id.uploadBtn);
        envanterAdi = findViewById(R.id.itemAdiEdittext);
        envanterAdedi = findViewById(R.id.stokAdediTextView);
        birimStokFiyat = findViewById(R.id.birimFiyatTextView);
        takePhotoBtn = findViewById(R.id.takePhotoBtn);
        marka = findViewById(R.id.markaEdittext);
        updateBtn = findViewById(R.id.updateBtn);

        storageReference = FirebaseStorage.getInstance().getReference();

        scanCode();


        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();

            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    String envanterad = envanterAdi.getText().toString();
                    String envanterAdet = envanterAdedi.getText().toString();
                    String birimFiyat = birimStokFiyat.getText().toString();
                    String itemMarka = marka.getText().toString();
                    addItemToFirestore(imageUri,envanterad,itemMarka,envanterAdet,birimFiyat);
                } else {
                    Toast.makeText(AddItemActivity.this, "Lütfen önce fotoğraf çekin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String envanterad = envanterAdi.getText().toString();
                String envanterAdet = envanterAdedi.getText().toString();
                String itemMarka = marka.getText().toString();
                StokGuncelle(envanterad,itemMarka,envanterAdet);
            }
        });



    }


    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Flaşı açmak için ses açma tuşuna basınız.");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barlauncher.launch(options);

    }
    ActivityResultLauncher<ScanOptions> barlauncher = registerForActivityResult(new ScanContract(), result -> {

        if (result.getContents()!=null){
            itemInfo=result.getContents().split(",");
            envanterAdi.setText(itemInfo[0]);
            marka.setText(itemInfo[1]);
            birimStokFiyat.setText(itemInfo[2]+" tl");
            Picasso.get().load(itemInfo[4]).into(imageView);

            imageUri = Uri.parse(itemInfo[4]);

            envanterAdi.setFocusable(false);
            envanterAdi.setFocusableInTouchMode(false);

            birimStokFiyat.setFocusable(false);
            birimStokFiyat.setFocusableInTouchMode(false);
            if (itemInfo.length>0){
                uploadBtn.setVisibility(View.GONE);
                takePhotoBtn.setVisibility(View.GONE);
            }
            else{
                updateBtn.setVisibility(View.GONE);
            }
        }
        else{
            updateBtn.setVisibility(View.GONE);
        }
    });
    // Kamera iznini kontrol edin ve kullanıcıdan izin isteyin
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            openCamera();
        }
    }
    private Uri saveImageToStorage(Bitmap imageBitmap) {
        File imagesFolder = new File(getCacheDir(), "images");
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }
        UUID uniqueId = UUID.randomUUID();
        File file = new File(imagesFolder, uniqueId+".jpg");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    // Kamera izin isteği sonucunu kontrol edin
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Kamera izni reddedildi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Kamera uygulamasını açın ve fotoğraf çekme işlemini başlatın
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            imageUri = saveImageToStorage(imageBitmap);
            System.out.println("imageuri: "+imageUri);
        }
    }




    private void addItemToFirestore(Uri imageUri, String itemAdi,String marka, String itemAdet,String birimFiyat) {
        // Resmi Firebase Storage'e yükle
        StorageReference imageRef = storageReference.child("images/" + imageUri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(imageUri);

        // Firestore'a veriyi yükle
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            // Resim yükleme başarılı olduysa, indirme URL'sini al
            return imageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                Uri downloadUri = task.getResult();
                String qrData = itemAdi+","+marka+","+birimFiyat+","+itemAdet+","+imageUri;

                Bitmap qrBitmap = generateQRCode(qrData, 400, 400);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageData = baos.toByteArray();

                //Firebase Referans oluşturma
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                UUID uniqueId = UUID.randomUUID();
                StorageReference qrRef = storageRef.child("qrcode/" + uniqueId+".jpg");

                UploadTask uploadTask2 = qrRef.putBytes(imageData);

                uploadTask2.continueWithTask(task2 -> {
                    if (!task2.isSuccessful()) {
                        throw task2.getException();
                    }

                    // Resim yükleme başarılı olduysa, indirme URL'sini al
                    return qrRef.getDownloadUrl();
                }).addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        Uri qrdownloadUri = task2.getResult();



                // Firestore'a veriyi ekle
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("itemAdi", itemAdi.toLowerCase());
                itemData.put("itemAdet", String.valueOf(itemAdet));
                itemData.put("itemResim", downloadUri.toString());
                itemData.put("qrResim", qrdownloadUri.toString());
                itemData.put("birimFiyat", birimFiyat);
                itemData.put("marka", marka);
                itemData.put("eklenmeTarihi", FieldValue.serverTimestamp());



                                    FirebaseFirestore.getInstance().collection("item")
                                            .add(itemData)
                                            .addOnSuccessListener(documentReference -> {
                                                Toast.makeText(AddItemActivity.this, "Veri başarıyla eklendi", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddItemActivity.this, FeedActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(AddItemActivity.this, "Veri ekleme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });

                                } else {
                Toast.makeText(AddItemActivity.this, "Resim yükleme hatası: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
                });
        }});
    }

    public void StokGuncelle(String itemAdi,String marka, String itemAdet){
        FirebaseFirestore.getInstance().collection("item")
                .whereEqualTo("itemAdi", itemAdi.toLowerCase())
                .whereEqualTo("marka", marka)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Eşleşen envanteri al ve güncelle
                        String envanterId = documentSnapshot.getId();
                        int stok = Integer.valueOf(documentSnapshot.getString("itemAdet"));
                        // Güncellenecek bilgileri burada belirleyin
                        Map<String, Object> updateData = new HashMap<>();
                        updateData.put("itemAdet", String.valueOf(stok + Integer.valueOf(itemAdet)));
                        // Diğer güncellenecek alanları da ekleyebilirsiniz

                        // Firestore'daki dokümanı güncelle
                        FirebaseFirestore.getInstance().collection("item").document(envanterId)
                                .update(updateData)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(AddItemActivity.this, "Stok Güncelleme Başarılı!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddItemActivity.this, FeedActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(AddItemActivity.this, "Stok Güncelleme Başarısız!", Toast.LENGTH_SHORT).show();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    // Sorgu hatası
                });
    }
    public Bitmap generateQRCode(String data, int width, int height) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

}