package com.extremekod.hotelsny;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnViewHotels:
                Intent intent = new Intent(this, HotelsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.alpha_in, R.anim.zoom_in);
        }
    }
}
