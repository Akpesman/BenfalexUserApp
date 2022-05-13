package bismsoft.benfalexparcel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ContactUsActivity extends AppCompatActivity {

    LinearLayout linearCall,linearEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().hide();
        views();
        linearCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+2347039549750"));
                startActivity(intent);
            }
        });

        linearEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","ukpapacy@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Banaflex Delivery Customer");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }

    private void views() {
        linearEmail=findViewById(R.id.linear_email);
        linearCall=findViewById(R.id.linear_call);
    }
}