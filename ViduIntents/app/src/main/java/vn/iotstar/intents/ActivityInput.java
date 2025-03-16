package vn.iotstar.intents;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityInput extends AppCompatActivity {

    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editTextInput = findViewById(R.id.editTextInput);
        Button buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(view -> {
            String data = editTextInput.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("data_return", data);
            setResult(RESULT_OK, resultIntent);
            finish(); // Đóng Activity và gửi kết quả về MainActivity
        });
    }
}
