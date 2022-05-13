package bismsoft.benfalexparcel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ServicesActivity extends AppCompatActivity {

    LinearLayout service1, service2,service3,service4;
    TextView service1Tv,service2Tv,service3Tv,service4Tv;
    ImageView arrowDown1,plus1,arrowDown2,plus2,arrowDown3,plus3,arrowDown4,plus4;
    Button schedual_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        getSupportActionBar().hide();
        views();
        service1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (service1Tv.getVisibility()== View.VISIBLE){
                    service1Tv.setVisibility(View.GONE);
                    arrowDown1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    plus1.setImageResource(R.drawable.ic_baseline_add_24);
                }else {
                    service1Tv.setVisibility(View.VISIBLE);
                    arrowDown1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    plus1.setImageResource(R.drawable.ic_baseline_minimize_24);
                }
            }
        });
        service2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (service2Tv.getVisibility()== View.VISIBLE){
                    service2Tv.setVisibility(View.GONE);
                    arrowDown2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    plus2.setImageResource(R.drawable.ic_baseline_add_24);
                }else {
                    service2Tv.setVisibility(View.VISIBLE);
                    arrowDown2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    plus2.setImageResource(R.drawable.ic_baseline_minimize_24);
                }

            }
        });

        service3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (service3Tv.getVisibility()== View.VISIBLE){
                    service3Tv.setVisibility(View.GONE);
                    arrowDown3.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    plus3.setImageResource(R.drawable.ic_baseline_add_24);
                }else {
                    service3Tv.setVisibility(View.VISIBLE);
                    arrowDown3.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    plus3.setImageResource(R.drawable.ic_baseline_minimize_24);
                }

            }
        });

        service4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (service4Tv.getVisibility()== View.VISIBLE){
                    service4Tv.setVisibility(View.GONE);
                    arrowDown4.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    plus4.setImageResource(R.drawable.ic_baseline_add_24);
                }else {
                    service4Tv.setVisibility(View.VISIBLE);
                    arrowDown4.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    plus4.setImageResource(R.drawable.ic_baseline_minimize_24);
                }

            }
        });

        schedual_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServicesActivity.this, ScheduleServiceActivity.class));
            }
        });




    }

    public void views(){

        service1 = findViewById(R.id.service1);
        service2 = findViewById(R.id.service2);
        service3 = findViewById(R.id.service3);
        service4 = findViewById(R.id.service4);
        service1Tv= findViewById(R.id.desc_service1_tv);
        service2Tv = findViewById(R.id.desc_service2_tv);
        service3Tv = findViewById(R.id.desc_service3_tv);
        service4Tv = findViewById(R.id.desc_service4_tv);
        arrowDown1 = findViewById(R.id.arrowdown1);
        plus1 = findViewById(R.id.plus1);
        arrowDown2 = findViewById(R.id.arrowdown2);
        plus2 = findViewById(R.id.plus2);
        arrowDown3 = findViewById(R.id.arrowdown3);
        plus3 = findViewById(R.id.plus3);
        arrowDown4 = findViewById(R.id.arrowdown4);
        plus4 = findViewById(R.id.plus4);

        schedual_btn = findViewById(R.id.schedule_btn);




    }
}