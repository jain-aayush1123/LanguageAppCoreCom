package awsm.awsmizng.u.alanguageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import awsm.awsmizng.u.alanguageapp.models.FirebaseUserProfile;
import awsm.awsmizng.u.alanguageapp.statics.Constants;
import awsm.awsmizng.u.alanguageapp.statics.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FillInfo extends AppCompatActivity {

    DatabaseReference databaseReference2;

    @BindView(R.id.etDisplayName)
    EditText etDisplayName;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.germany)
    ImageView germany;
    @BindView(R.id.japan)
    ImageView japan;
    @BindView(R.id.india)
    ImageView india;
    @BindView(R.id.spain)
    ImageView spain;
    @BindView(R.id.france)
    ImageView france;
    @BindView(R.id.tvLanguageChosen)
    TextView tvLanguageChosen;
    @BindView(R.id.llForm)
    LinearLayout llForm;
    @BindView(R.id.llProgress)
    LinearLayout llProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_info);
        ButterKnife.bind(this);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference2 = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADERS);
        Query ref = databaseReference2.orderByChild("userID").equalTo(user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FirebaseUserProfile userProfile = snapshot.getValue(FirebaseUserProfile.class);
                        Constants.language = userProfile.getLanguage();
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    ViewGroup transiion = findViewById(R.id.transition_scene);
                    TransitionManager.beginDelayedTransition(transiion);
                    llProgress.setVisibility(View.GONE);
                    llForm.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        etDisplayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etDisplayName.getText().toString().isEmpty()) {
                    submit.setEnabled(true);
                } else {
                    etDisplayName.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.germany, R.id.japan, R.id.india, R.id.spain, R.id.france, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.germany:
                Constants.language = "GERMAN";
                tvLanguageChosen.setText(getString(R.string.langPref) + Constants.language);
                break;
            case R.id.japan:
                Constants.language = "JAPANESE";
                tvLanguageChosen.setText(getString(R.string.langPref) + Constants.language);
                break;
            case R.id.india:
                Constants.language = "SANSKRIT";
                tvLanguageChosen.setText(getString(R.string.langPref) + Constants.language);
                break;
            case R.id.spain:
                Constants.language = "SPANISH";
                tvLanguageChosen.setText(getString(R.string.langPref) + Constants.language);
                break;
            case R.id.france:
                Constants.language = "FRENCH";
                tvLanguageChosen.setText(getString(R.string.langPref) + Constants.language);
                break;
            case R.id.submit:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseUserProfile firebaseUserProfile = new FirebaseUserProfile(etDisplayName.getText().toString(), Constants.language, user.getUid());
                databaseReference2.child(user.getUid()).setValue(firebaseUserProfile);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
        }
    }

    /*@OnClick(R.id.submit)
    public void onViewClicked() {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // FirebaseUserProfile firebaseUserProfile = new FirebaseUserProfile(etDisplayName.getText().toString(), "German", user.getUid());
        //databaseReference2.child(user.getUid()).setValue(firebaseUserProfile);
        //Constants.language = etLanguage.getText().toString();
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        //finish();
    }*/
}
