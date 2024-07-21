package site.lernit.lernitandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView appTitle, signInTitle, signUpText;
    private EditText fullNameEditText, emailEditText, passwordEditText;
    private Button signInButton, signInWithGoogleButton;

    private boolean isLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Toast.makeText(MainActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (email.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                return;
            }

            // If all fields are filled, proceed with sign-in or sign-up
            if (isLogIn) {
                // Perform log in
                Toast.makeText(MainActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
            } else {
                // Perform sign up
                Toast.makeText(MainActivity.this, "Signing up...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}