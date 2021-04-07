package com.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backend.DatabaseManagement;
import com.backend.DatabaseSample;
import com.backend.News;
import com.backend.NewsPreview;
import com.backend.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


public class BackendTestActivity extends AppCompatActivity {
    Button btnRead, btnWrite;
    TextView txtTitle, txtContent, txtAuthor, txtDate;
    ImageView imgThumbnail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend_test);

        initLayoutViews();
        DatabaseManagement databaseManagement = new DatabaseManagement();
        DatabaseSample databaseSample = new DatabaseSample();

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseManagement.getPreviewsByType(new DatabaseManagement.firestoreCallback() {
                    @Override
                    public void onCallback(List<NewsPreview> list) {
                        if (list.size() > 0) {
                            NewsPreview sample = list.get(0);
                            txtTitle.setText(sample.getTitle());
                            txtContent.setText(sample.getPreviewContent());
                            txtAuthor.setText(sample.getAuthorUsername());
                            txtDate.setText(sample.getCreatedDate().toString());
                            Picasso.get().load(sample.getThumbnailURL()).into(imgThumbnail);
                        } else {
                            txtContent.setText(new String("Don't have any results"));
                        }
//                        for (NewsPreview news : list) {
//                            Log.d("_OUT", news.getCreatedDate().toString());
//                        }
                    }
                }, "educations", 10);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("test", "1234");
                databaseManagement.writeUserToDatabase(user);
//                databaseSample.writeToNewsDatabase(1);
            }
        });
    }

    public void initLayoutViews(){
        btnRead = (Button) findViewById(R.id.btnRead);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        txtTitle = (TextView) findViewById(R.id.sample_title);
        txtContent = (TextView) findViewById(R.id.sample_content);
        txtContent.setMovementMethod(new ScrollingMovementMethod());
        txtAuthor = (TextView) findViewById(R.id.sample_author);
        txtDate = (TextView) findViewById(R.id.sample_date);
        imgThumbnail = (ImageView) findViewById(R.id.backend_thumbnail);
    }

}