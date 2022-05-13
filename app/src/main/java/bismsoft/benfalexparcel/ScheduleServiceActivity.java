package bismsoft.benfalexparcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bismsoft.benfalexparcel.DataProvider.ScheduleAServiceDP;

public class ScheduleServiceActivity extends AppCompatActivity {

    String strServiceSelect, strFirstName, strLastName, strEmail, strPhone, strPickUpAddress, strDeliverAddress;
    String strDate, strTime, strComments;

    Spinner serviceSelect;
    Button serviceSubmitBtn;
    EditText etFirstName, etLastName, etEmail, etPhone, etComments;
    TextView tvDate, tvTime, tvPickUpAddress, tvDeliverAddress;
    Calendar dateSelected;
    final Calendar newCalendar = Calendar.getInstance();
    ProgressDialog progressDialog;
    SharedPreferences sharedPref;
    String userId;
    DatabaseReference myRef, adminRef;
    String userToken, adminToken;
    PlacesClient placesClient;
    String pickUpLocation, deliveryAddress;
    Double pickupLat=0.0;
    Double pickupLog=0.0;
    Double deliveryLat=0.0;
    Double deliveryLog=0.0;
    float distance=0f;
    double price=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_service);
        getSupportActionBar().hide();
        views();

        dateSelected = Calendar.getInstance();
        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        SimpleDateFormat time = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        strDate = df.format(c);
        strTime = time.format(c);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //TODO Auto-generated method stub
                newCalendar.set(Calendar.YEAR, year);
                newCalendar.set(Calendar.MONTH, monthOfYear);
                newCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd-MMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tvDate.setText(sdf.format(newCalendar.getTime()));
            }

        };
        tvDate.setText(strDate);
        tvTime.setText(strTime);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ScheduleServiceActivity.this, date, newCalendar
                        .get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                        newCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ScheduleServiceActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        tvTime.setText(selectedHour + ":" + selectedMinute + " 'O' Clock");

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        serviceSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (serviceSelect.getSelectedItemPosition() == 0) {

                    Toast.makeText(ScheduleServiceActivity.this, "Please Select a service", Toast.LENGTH_SHORT).show();
                } else if (etFirstName.getText().toString().equals("")) {

                    etFirstName.setError("Please Write first name");
                } else if (etLastName.getText().toString().equals("")) {

                    etLastName.setError("Please Write Last Name");
                } else if (etEmail.getText().toString().equals("")) {

                    etEmail.setError("Please write your email correctly");
                } else if (etPhone.getText().toString().equals("")) {

                    etPhone.setError("Please Write Your Phone Number");


                } else if (pickUpLocation.equals("")) {

                    Toast.makeText(ScheduleServiceActivity.this, "Enter Pickup Location First", Toast.LENGTH_SHORT).show();

                } else if (deliveryAddress.equals("")) {

                    Toast.makeText(ScheduleServiceActivity.this, "Enter Delivery Location First", Toast.LENGTH_SHORT).show();


                } else if (tvDate.getText().toString().equals("Date of Service Needed")) {

                    tvDate.setTextColor(Color.RED);
                } else if (tvTime.getText().toString().equals("Time of Service Needed")) {

                    tvTime.setTextColor(Color.RED);
                }
//                else if (etComments.getText().toString().equals("")){
//                    etComments.setError("Please write comments");
//
//
//                }
                else {

                    strServiceSelect = serviceSelect.getSelectedItem().toString();
                    strFirstName = etFirstName.getText().toString().trim();
                    strLastName = etLastName.getText().toString().trim();
                    strEmail = etEmail.getText().toString().trim();
                    strPhone = etPhone.getText().toString().trim();
                    strPickUpAddress = pickUpLocation;
                    strDeliverAddress = deliveryAddress;
                    strDate = tvDate.getText().toString().trim();
                    strTime = tvTime.getText().toString().trim();
                    strComments = etComments.getText().toString().trim();
                    sendScheduleService();
                }

            }
        });
    }

    public void views() {

        serviceSelect = findViewById(R.id.service_select_spinner);
        serviceSubmitBtn = findViewById(R.id.service_submit_btn);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        tvPickUpAddress = findViewById(R.id.et_pickupaddress);
        tvDeliverAddress = findViewById(R.id.et_deliveraddress);
        etComments = findViewById(R.id.et_comments);
        tvDate = findViewById(R.id.tv_date);
        tvTime = findViewById(R.id.tv_time);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPref = getSharedPreferences("customer", MODE_PRIVATE);
        userId = sharedPref.getString("userId", "");
        myRef = FirebaseDatabase.getInstance().getReference("customers").child(sharedPref.getString("userId", "")).child("token");
        adminRef = FirebaseDatabase.getInstance().getReference("adminToken");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userToken = snapshot.getValue(String.class);
                //Toast.makeText(ScheduleServiceActivity.this, userToken, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminToken = snapshot.getValue(String.class);
                //Toast.makeText(ScheduleServiceActivity.this, userToken, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Places.initialize(this, getResources().getString(R.string.google_maps_key));
        placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS,Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                pickUpLocation = place.getName();
                Log.e("PlaceData", place.toString());
                pickupLat=place.getLatLng().latitude;
                pickupLog=place.getLatLng().longitude;
                distance=getKmFromLatLong(pickupLat,pickupLog,deliveryLat,deliveryLog);
                price=distance/2.0;


            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        AutocompleteSupportFragment autocompleteFragment1 = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        autocompleteFragment1.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment1.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS,Place.Field.LAT_LNG));

        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                deliveryAddress = place.getName();
                deliveryLat=place.getLatLng().latitude;
                deliveryLog=place.getLatLng().longitude;

                distance=getKmFromLatLong(pickupLat,pickupLog,deliveryLat,deliveryLog);
                price=distance/2.0;
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {

            Status status = Autocomplete.getStatusFromIntent(data);
            Log.e("TAGGGG", status.toString());
            Place place = Autocomplete.getPlaceFromIntent(data);

            tvPickUpAddress.setText(place.getAddress());
        }
    }

    public void sendScheduleService() {
        progressDialog.show();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("ServiceOrders");
        String orderKey = myRef.push().getKey();
        myRef.child(orderKey).setValue(new ScheduleAServiceDP(userId, orderKey, strServiceSelect, strFirstName, strLastName, strEmail, strPhone, strPickUpAddress, strDeliverAddress, strDate, strTime, strComments, "pending",pickupLat.toString(),pickupLog.toString(),deliveryLat.toString(),deliveryLog.toString(),price+""))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showConfirmationDialog(orderKey);
                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(adminToken, "Benfalex Package Delivery",
                                    "New Order Received !!!",
                                    getApplicationContext(), ScheduleServiceActivity.this);
                            notificationsSender.SendNotifications();
                        } else {
                            Toast.makeText(ScheduleServiceActivity.this, "Order Sending Failed Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void showConfirmationDialog(String orderKey) {
        progressDialog.dismiss();
        final Dialog confirmDialog = new Dialog(this);
        confirmDialog.setContentView(R.layout.custom_order_confirmation_dialog);
        confirmDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        confirmDialog.setCancelable(false);
        TextView tvOrderKey = confirmDialog.findViewById(R.id.order_key);
        tvOrderKey.setText(orderKey);
        Button btnOk = confirmDialog.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
                finish();
            }
        });
        tvOrderKey.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", tvOrderKey.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ScheduleServiceActivity.this, "Text Copied", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(userToken, "Benfalex Package Delivery",
                "Dear " + strFirstName + " " + strLastName + " Request Received Successfully with Order ID: " + orderKey,
                getApplicationContext(), ScheduleServiceActivity.this);
        notificationsSender.SendNotifications();
        confirmDialog.show();
    }

    public static float getKmFromLatLong(Double lat1, Double lng1, Double lat2, Double lng2){
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lng1);
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lng2);
        float distanceInMeters = loc1.distanceTo(loc2);
        return distanceInMeters/1000;
    }
}