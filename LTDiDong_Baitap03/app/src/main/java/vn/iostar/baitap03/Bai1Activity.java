package vn.iostar.baitap03;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class Bai1Activity extends AppCompatActivity {
    private RelativeLayout layout;
    private int[] backgrounds = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);

        layout = findViewById(R.id.main_layout);

        int newBg = getRandomBackground();
        layout.setBackgroundResource(newBg);
        saveBackground(newBg);
    }

    private int getRandomBackground() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int lastBg = prefs.getInt("lastBg", -1);
        int newBg;
        Random random = new Random();

        do {
            newBg = backgrounds[random.nextInt(backgrounds.length)];
        } while (newBg == lastBg);

        return newBg;
    }

    private void saveBackground(int bg) {
        SharedPreferences.Editor editor = getSharedPreferences("AppPrefs", MODE_PRIVATE).edit();
        editor.putInt("lastBg", bg);
        editor.apply();
    }
}
