package com.geektech.taskapp1.ui.slideshow;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment implements View.OnClickListener {

    private SlideshowViewModel slideshowViewModel;
    private List<String> urls;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        root.findViewById(R.id.btnDownload).setOnClickListener(this);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        urls = new ArrayList<>();
        urls.add("https://img.pngio.com/-png-3-png-image-pictures-of-png-512_512.png");
        urls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhNc-M4si0RjW2te1YdJPESB1kG7aTzMbDIMoPOn1GXLF-LAzN&s");
        return root;
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        File folder = new File(Environment.getExternalStorageDirectory(), "TaskApp/Images");
        folder.mkdirs();
        downloadsFiles(folder);
    }

    private void downloadsFiles(final File folder) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                for(int i = 0; i < urls.size(); i++) {
                    String url = urls.get(i);
                    String fileName = url.substring(url.lastIndexOf("/")+1);
                    File file = new File(folder, fileName);
                    FileUtils.copyURLToFile(new URL(url), file);
                }
            } catch(IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        thread.start();
        }
    }