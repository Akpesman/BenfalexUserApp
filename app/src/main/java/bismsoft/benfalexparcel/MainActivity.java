package bismsoft.benfalexparcel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button scheduleBtn;
    RecyclerView recyclerView;
    MainDataProvider mainDataProvider;
    MainAdapter mainAdapter;
    private ArrayList<MainDataProvider> list;
    String[] titleArray ={"PACKAGE \nRECEIVING","PACKAGE\n PICKUP","PICKUP &\n DELIVER","PICKUP & PREP \nFOR SHIPMENT"};
    int[] imageArray = {R.drawable.image2,R.drawable.image1,R.drawable.image3,R.drawable.image4};
    String lastService1="Benfalex Parcel Delivery is also a mobile mailroom solution for small businesses that can’t facilitate the expenses of an in-house mailroom. We’ll pick-up, package/box, weigh, and label all your small items.Weight restrictions apply";
    String lastService2 ="\n\nWhat We Do\n\n";
    String lastService3="> Pickup & Deliver Only\n" +
            "> Pickup & Drop To Shipper/Customer\n" +
            "> Pickup, Package & Deliver To Shipper/Customer\n" +
            "> Pickup, Box & Deliver To Shipper/Customer\n" +
            "> Stamps & Label Services";

    SpannableString spannableString;

    String finalString,userToken;

    BottomNavigationView bottomNavigationView;
    DatabaseReference myRef;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         views();
         recyclerViewMehtod();

         FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
             @Override
             public void onComplete(@NonNull Task<String> task) {
                 if(task.isSuccessful())
                 {
                     userToken=task.getResult();
                     myRef.setValue(userToken);
                     FirebaseMessaging.getInstance().subscribeToTopic("/topics/"+userToken);

                 }
             }
         });


         spannableString = new SpannableString(lastService2);
         spannableString.setSpan(new AbsoluteSizeSpan(R.dimen.textsize),0,lastService2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
         finalString = lastService1+spannableString+lastService3;


         scheduleBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 startActivity(new Intent(MainActivity.this, ScheduleServiceActivity.class));


             }
         });
         mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
             @Override
             public void onItemClick(int position) {

                 switch (position){



                     case 0:

                         Intent intent = new Intent(MainActivity.this, ServiceDetailedActivity.class);
                         intent.putExtra("title","Pickup, Hold & Delivery Services");
                         intent.putExtra("desc","In recent years, the number of online parcel delivery has expanded tremendously. Growth in COVID-19 has been driven by high consumer demand and a growing variety of items offered online. To meet up with the high volumes of demands of the customers, most of the parcel and courier services have suffered unimaginable financial setbacks while leaving customers dissatisfied. Problems range from delayed parcel delivery, unattended parcel delivery, failed delivery attempts, lost or stolen parcels, and scammers sending suspecting messages to customers to defraud them. With our PACKAGE DELIVERY SERVICES, we provide the solution by preventing the theft of our customer’s packages and data protection from cyber criminals. We’ll pick-up your “delivered” packages, hold, and deliver them when you’re available.");
                         startActivity(intent);

                         break;
                     case 1:

                         Intent intent1 = new Intent(MainActivity.this, ServiceDetailedActivity.class);
                         intent1.putExtra("title","Receive, Hold & Delivery Services");
                         intent1.putExtra("desc","For whilst you simply are unable to individually receive your deliveries, you have got a solution provider.  Our services include offering the best options to securely receive your pre-ordered packages after which time a private sustainable package delivery that is handy for our customers.");
                         startActivity(intent1);

                         break;
                     case 2:

                         Intent intent2 = new Intent(MainActivity.this, ServiceDetailedActivity.class);
                         intent2.putExtra("title","Same/Next Day Pickup & Delivery Services");
                         intent2.putExtra("desc","Considering that you want same-day delivery, our services put your needs first and we provide the solution for those bustling customers with special delivery services");
                         startActivity(intent2);

                         break;
                     case 3:

                         Intent intent3 = new Intent(MainActivity.this, ServiceDetailedActivity.class);
                         intent3.putExtra("title","Mobile Mailroom Solution for Small Businesses");
                         intent3.putExtra("desc",finalString);
                         startActivity(intent3);

                         break;
                 }

             }

             @Override
             public void onButtonChange(int position) {

             }
         });




          bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                  switch (item.getItemId()){

                      case R.id.page_1:
                          finish();

                          startActivity(new Intent(MainActivity.this, MainActivity.class));

                          return true;

                      case R.id.page_2:


                          startActivity(new Intent(MainActivity.this, ContactUsActivity.class));

                          return true;

                      case R.id.page_3:


                          startActivity(new Intent(MainActivity.this, AboutUsActivity.class));

                          return true;

                      case R.id.page_4:


                          startActivity(new Intent(MainActivity.this, ServicesActivity.class));

                          return true;

                      case R.id.page_5:


                          startActivity(new Intent(MainActivity.this, Orders.class));

                          return true;


                  }
                  return true;
              }
          });








    }

    public void recyclerViewMehtod() {

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        list = new ArrayList<MainDataProvider>();

        for (int i = 0; i < titleArray.length; i++) {

            mainDataProvider = new MainDataProvider(titleArray[i], imageArray[i]);
            list.add(mainDataProvider);
        }

        mainAdapter = new MainAdapter(MainActivity.this, list);
        recyclerView.setAdapter(mainAdapter);

    }

    public void views(){
        scheduleBtn = findViewById(R.id.schedule_btn);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        sharedPref=getSharedPreferences("customer",MODE_PRIVATE);
        myRef= FirebaseDatabase.getInstance().getReference("customers").child(sharedPref.getString("userId","")).child("token");
    }




}