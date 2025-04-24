package vn.iotstar.baitap09.vidu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide; // If loading profile pic from URL
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.iotstar.baitap09.R;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbarProfile;
    CircleImageView ivProfilePic;
    TextView tvUserId, tvUsername, tvFullName, tvEmail, tvGender;
    Button btnLogout;

    // --- Step 1: Declare the ActivityResultLauncher ---
    private ActivityResultLauncher<Intent> profileUpdateResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // --- Toolbar Setup ---
        toolbarProfile = findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbarProfile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // --- Find Views ---
        ivProfilePic = findViewById(R.id.ivProfilePic);
        tvUserId = findViewById(R.id.tvUserId);
        tvUsername = findViewById(R.id.tvUsername);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);
        btnLogout = findViewById(R.id.btnLogout);

        // --- Step 2: Register the ActivityResultLauncher ---
        // This needs to be done early, typically in onCreate or as a member initializer
        profileUpdateResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // --- Step 4: Handle the result ---
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            // Check if data is not null and contains our expected extra
                            if (data != null && data.hasExtra(ProfileImageUpload.RESULT_EXTRA_IMAGE_URL)) {
                                String newImageUrl = data.getStringExtra(ProfileImageUpload.RESULT_EXTRA_IMAGE_URL);

                                Log.d("ProfileActivity", "Received new avatar URL: " + newImageUrl);

                                // Update the ImageView using Glide
                                if (newImageUrl != null && !newImageUrl.isEmpty()) {
                                    Glide.with(ProfileActivity.this)
                                            .load(newImageUrl)
                                            .placeholder(R.drawable.ic_launcher_background) // Keep placeholder
                                            .error(R.drawable.ic_launcher_background)     // Keep error fallback
                                            // Add these lines to bypass cache and ensure the new image is shown quickly
                                            .skipMemoryCache(true)
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .into(ivProfilePic);
                                }
                            } else {
                                Log.d("ProfileActivity", "Result OK, but no new image URL found in data.");
                            }
                        } else {
                            // Handle cases where the upload was cancelled or failed if necessary
                            Log.d("ProfileActivity", "Profile image update cancelled or failed. Result code: " + result.getResultCode());
                        }
                    }
                });


        // --- Load User Data ---
        loadUserProfile(); // Load initial data

        // --- Set Click Listeners ---
        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the UploadImageActivity
                Intent intent = new Intent(ProfileActivity.this, ProfileImageUpload.class);

                String currentUserId = tvUserId.getText().toString();
                if (currentUserId.isEmpty() || currentUserId.equals("UNKNOWN_USER")) {
                    Toast.makeText(ProfileActivity.this, "Cannot edit profile: User ID not loaded correctly.", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra(ProfileImageUpload.EXTRA_USER_ID, currentUserId);

                // --- Step 3: Launch the activity using the launcher ---
                profileUpdateResultLauncher.launch(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your logout logic here
                Toast.makeText(ProfileActivity.this, "Logout Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserProfile() {
        String userId = "12345";
        String username = "trung1";
        String fullName = "Nguyễn Hữu Trung";
        String email = "trung2@gmail.com";
        String gender = "Male";
        String profileImageUrl = null;

        tvUserId.setText(userId);
        tvUsername.setText(username);
        tvFullName.setText(fullName);
        tvEmail.setText(email);
        tvGender.setText(gender);

        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(profileImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivProfilePic);
        } else {
            ivProfilePic.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}