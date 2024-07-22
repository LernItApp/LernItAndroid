package site.lernit.lernitandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignInActivity extends AppCompatActivity {

    private TextView appTitle, signInTitle, signUpText;
    private EditText fullNameEditText, emailEditText, passwordEditText;
    private Button signInButton, signInWithGoogleButton;

    private boolean isLogIn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            signInSuccess();


        setContentView(R.layout.activity_signin);

        appTitle = findViewById(R.id.title);
        signInTitle = findViewById(R.id.signInTitle);
        signUpText = findViewById(R.id.signUpText);
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        signInWithGoogleButton = findViewById(R.id.signInWithGoogleButton);

        isLogIn = true;

        signUpText.setOnClickListener(new SignUpTextOnClickListener());

        signInButton.setOnClickListener(new signInButtonOnClickListener());


    }

    private void updateUI(FirebaseUser currentUser) {
        Log.d("SignInPage", "User is already logged in!\nUser UID: " + currentUser.getUid());
    }

    class SignUpTextOnClickListener implements View.OnClickListener {

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            isLogIn = !isLogIn; // Flip the value of isLogIn
            if(isLogIn) {
                fullNameEditText.setVisibility(View.GONE); // Hide the EditText
                signUpText.setText("New to LernIt? Sign Up!"); // Change bottom text to "New to LernIt? Sign Up!"
                signInTitle.setText("Log In"); // Change title to "Log In"
                signInButton.setText("Log In"); // Change button text to "Log In"
            } else {
                fullNameEditText.setVisibility(View.VISIBLE); // Show the EditText
                signUpText.setText("Have an account? Log In!"); // Change bottom text to "Have an account? Log In!"
                signInTitle.setText("Sign Up"); // Change title to "Sign Up"
                signInButton.setText("Sign Up"); // Change button text to "Sign Up"
            }
        }
    }
    class signInButtonOnClickListener implements View.OnClickListener {

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            // Get the input from the EditTexts
            String fullName = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Check if the fields are empty
            if (!isLogIn && fullName.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (email.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                return;
            }

            // If all fields are filled, proceed with sign-in or sign-up
            if (isLogIn) {
                // Perform log in
                Toast.makeText(SignInActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
                LogIn(email, password);
            } else {
                // Perform sign up
                Toast.makeText(SignInActivity.this, "Signing up...", Toast.LENGTH_SHORT).show();
                signUp(fullName, email, password);
            }
        }
    }

    private void signUp(String fullName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign up success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullName)
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignInActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                        signInSuccess();
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Sign up successful, but failed to update profile: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        signInSuccess();
                                    }
                                });
                    }
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(SignInActivity.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void signInSuccess() {
        // Navigate to MainActivity
        Intent intent = new Intent(SignInActivity.this, MainActivity.class); // Create the intent
        startActivity(intent); // Start the main activity
        finish(); // This closes this activity
    }

    private void LogIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Login successful
                    Toast.makeText(SignInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // Proceed to the next activity
                    signInSuccess();
                } else {
                    // Login failed
                    Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                    Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });
    }
}
