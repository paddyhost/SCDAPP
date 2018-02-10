package com.hatchers.solar_conduction_dryer.activity.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hatchers.solar_conduction_dryer.R;
import com.hatchers.solar_conduction_dryer.activity.Preference_Manager.PrefManager;
import com.intrusoft.squint.DiagonalView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Profile_Fragment extends Fragment
{
    private static final String IMAGE_DIRECTORY = "/Downloads";
    private int GALLERY = 1, CAMERA = 2;

    private ImageView profileImage;
    private TextView userNameTxt, mobileNoTxt, dOBTxt, genderTxt,
            countryTxt, stateTxt, cityTxt, pincodeTxt;
    private PrefManager prefManager;
    private ImageButton back;
    DiagonalView diagonalView;

    private int RESULT_CANCELED;

    public Profile_Fragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializations(view);

        setInfo();

        clickListners();

        return view;
    }

    private void initializations(View view)
    {
        profileImage = (ImageView) view.findViewById(R.id.profile_image);
        back = (ImageButton) view.findViewById(R.id.btn_back);

        prefManager = new PrefManager(getActivity().getApplicationContext());
        userNameTxt = (TextView) view.findViewById(R.id.userName_txt);
        mobileNoTxt = (TextView) view.findViewById(R.id.mobileNo);
        dOBTxt = (TextView) view.findViewById(R.id.dob);
        genderTxt = (TextView) view.findViewById(R.id.gender);
        countryTxt =(TextView)view.findViewById(R.id.country);
        stateTxt =(TextView)view.findViewById(R.id.state);
        cityTxt =(TextView)view.findViewById(R.id.city);
        pincodeTxt =(TextView)view.findViewById(R.id.pincode);

        diagonalView = (DiagonalView) view.findViewById(R.id.diagonal);

    }

    public void setInfo()
    {
        userNameTxt.setText(String.valueOf("  " + prefManager.getUserName()));
        mobileNoTxt.setText(String.valueOf("  " + prefManager.getMobileNumber()));
        dOBTxt.setText(String.valueOf("  " + prefManager.getBirthDate()));
        genderTxt.setText(String.valueOf("  " + prefManager.getGender()));
        countryTxt.setText(String.valueOf("  "+prefManager.getCountry()));
        stateTxt.setText(String.valueOf("  "+prefManager.getState()));
        cityTxt.setText(String.valueOf("  "+prefManager.getCity()));
        pincodeTxt.setText(String.valueOf("  "+prefManager.getPincode()));

        if (prefManager.getGender().equalsIgnoreCase("F")) {
            genderTxt.setText(String.valueOf("  " + "Female"));
        } else if (prefManager.getGender().equalsIgnoreCase("M")) {
            genderTxt.setText(String.valueOf("  " + "Male"));
        }

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        File profile = new File(wallpaperDirectory, "profile.jpg");
        if(profile.exists())
        {
            Bitmap bitmap = BitmapFactory.decodeFile(profile.getAbsolutePath());
            profileImage.setImageBitmap(bitmap);
            diagonalView.setImageBitmap(bitmap);

            // to set the background color (color should have some alpha val)
           //  diagonalView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            // to make the solid color diagonal
          //  diagonalView.setSolidColor(getResources().getColor(R.color.colorPrimaryDark));

        }
        else
        {
            profileImage.setImageResource(R.drawable.ic_account_circle_black_24dp);
            diagonalView.setSolidColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void clickListners()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    private void showPictureDialog()
    {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhotoFromCamera();
                                break;
                            case 1:
                                choosePhotoFromGallary();
                                break;
                            case 2:
                                dialog.dismiss();
                        }
                    }
                });
        AlertDialog alert=builder.create();
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public void choosePhotoFromGallary()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    profileImage.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);
                    diagonalView.setBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profileImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            diagonalView.setImageBitmap(thumbnail);
            diagonalView.setBitmap(thumbnail);


            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, "profile.jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}



