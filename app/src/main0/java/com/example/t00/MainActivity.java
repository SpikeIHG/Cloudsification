package com.example.t00;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fridayof1995.tabanimation.SnapTabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ArgbEvaluator mArgbEvaluator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SnapTabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.smallCenterButton.setImageResource(R.drawable.ic_white_commet);
        tabLayout.largeCenterButton.setImageResource(R.drawable.ic_white_commet);
        tabLayout.startButton.setImageResource(R.drawable.ic_white_commet);
        tabLayout.endButton.setImageResource(R.drawable.ic_white_commet);

        tabLayout.setBackgroundCollapsed(R.drawable.tab_gradient_collapsed); // By default black fall gradient.
        tabLayout.setBackgroundExpanded(R.drawable.tab_gradient_expanded);


    }
}