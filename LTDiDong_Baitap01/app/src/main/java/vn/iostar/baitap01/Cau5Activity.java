package vn.iostar.baitap01;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class Cau5Activity extends AppCompatActivity {
    private EditText editTextInputCount;
    private Button buttonGenerate;
    private TextView textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cau5);

        editTextInputCount = findViewById(R.id.editTextInputCount);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        textViewOutput = findViewById(R.id.textViewOutput);

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editTextInputCount.getText().toString();

                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(Cau5Activity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }

                int count;
                try {
                    count = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    Toast.makeText(Cau5Activity.this, "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (count <= 0) {
                    Toast.makeText(Cau5Activity.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<Integer> perfectSquares = generatePerfectSquares(count);

                textViewOutput.setText("Các số chính phương: " + perfectSquares.toString());
                Toast.makeText(Cau5Activity.this, "Số chính phương: " + perfectSquares.toString(), Toast.LENGTH_LONG).show();

                Log.d("PerfectSquares", "Số chính phương: " + perfectSquares.toString());
            }
        });
    }

    private ArrayList<Integer> generatePerfectSquares(int n) {
        ArrayList<Integer> perfectSquares = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int randomNum = random.nextInt(1000);
            int perfectSquare = randomNum * randomNum;
            perfectSquares.add(perfectSquare);
        }
        return perfectSquares;
    }
}
