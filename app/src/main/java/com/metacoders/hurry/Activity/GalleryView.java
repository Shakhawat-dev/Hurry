package com.metacoders.hurry.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.metacoders.hurry.R;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.builder.GallerySettings;

import java.util.ArrayList;
import java.util.List;

import static com.veinhorn.scrollgalleryview.loader.picasso.dsl.DSL.image;

public class GalleryView extends AppCompatActivity {
    ArrayList<String> images = new ArrayList<>();
    List<MediaInfo> mediaInfoList = new ArrayList<>();
    private ScrollGalleryView galleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);

        images.clear();
        mediaInfoList.clear();
        // recieve the module
        Intent p = getIntent();
        images = (ArrayList<String>) p.getSerializableExtra("LIST");

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for (String item : images) {
            mediaInfoList.add(image(item));
        }


        galleryView = ScrollGalleryView
                .from((ScrollGalleryView) findViewById(R.id.scroll_gallery_view))
                .settings(
                        GallerySettings
                                .from(getSupportFragmentManager())
                                .thumbnailSize(150)
                                .enableZoom(true)
                                .build()
                )
                .add(mediaInfoList)
                .build();
    }
}