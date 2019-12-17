package com.geektech.taskapp1.ui.gallery;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geektech.taskapp1.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.os.Environment.getExternalStorageDirectory;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    Button button;
    ArrayList<URL> urls;
    ArrayList<File> files;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        button = root.findViewById(R.id.download_button);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        file();
        return root;
    }

    @AfterPermissionGranted(101)
    private void file() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    File folder = new File(getExternalStorageDirectory(), "Gallery");
                    folder.mkdirs();

                    files = new ArrayList<>();

                    File imageFile1 = new File(folder, "image1.png");
                    File imageFile2 = new File(folder, "image2.png");
                    File imageFile3 = new File(folder, "image3.png");
                    File imageFile4 = new File(folder, "image4.png");
                    File imageFile5 = new File(folder, "image5.png");


                    files.add(imageFile1);
                    files.add(imageFile2);
                    files.add(imageFile3);
                    files.add(imageFile4);
                    files.add(imageFile5);
                    downloadFile();
                } else {
                    EasyPermissions.requestPermissions(getActivity(), "Разрешить?", 101, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });
    }

    public void downloadFile() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    urls = new ArrayList<>();

                    urls.add(new URL("https://img.pngio.com/-png-3-png-image-pictures-of-png-512_512.png"));
                    urls.add(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhNc-M4si0RjW2te1YdJPESB1kG7aTzMbDIMoPOn1GXLF-LAzN&s"));
                    urls.add(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmkE_9l5aIeBrmSicj3u_tnQfIk9QCRHdIVx2r-jrLi7LdjZ0P&s"));
                    urls.add(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTcGq4fW5OUwJqoJfUe76bN91CqgJoaqE8MSj1kPxxJlS5tSwA&s"));
                    urls.add(new URL("https://i.skyrock.net/1211/77881211/pics/2991715307_1_3_IhrDabDy.png"));
                    for (int i = 0; i < urls.size(); i++) {

                        FileUtils.copyURLToFile(urls.get(i), files.get(i));

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });
        thread.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}