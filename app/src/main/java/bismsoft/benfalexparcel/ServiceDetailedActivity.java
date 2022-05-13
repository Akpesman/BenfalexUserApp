package bismsoft.benfalexparcel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServiceDetailedActivity extends AppCompatActivity {

    TextView titleService,DescService;
    String titleString, DescString;
    Button schedualBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detailed);

        views();

        Intent intent = getIntent();
        titleString= intent.getStringExtra("title");
        DescString=intent.getStringExtra("desc");

        titleService.setText(titleString);
        DescService.setText(DescString);

        schedualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServiceDetailedActivity.this, ScheduleServiceActivity.class));
            }
        });


    }


    public void  views(){

        titleService = findViewById(R.id.title_tv);
        DescService = findViewById(R.id.desc_tv);
        schedualBtn = findViewById(R.id.schedule_btn);
    }
}