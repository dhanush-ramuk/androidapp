package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class infoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
    }
public void email(View v){
    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto","dhanush.10087@gmail.com", null));
    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
    intent.putExtra(Intent.EXTRA_TEXT, "m");

    try {
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));

        finish();
        Log.i("Finished sending email...", "");
    } catch (android.content.ActivityNotFoundException ex) {
        Toast.makeText(infoPage.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
    }
}

public void twitter(View v){
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/dhanush_ramuk"));
    startActivity(browserIntent);
}

    public void icons8(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://icons8.com"));
        startActivity(browserIntent);
    }
}
