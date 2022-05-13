package bismsoft.benfalexparcel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import bismsoft.benfalexparcel.DataProvider.ScheduleAServiceDP;

public class Orders extends AppCompatActivity {

    RecyclerView rvPending;
    ProgressDialog progressDialog;
    Query pendingRef;
    ArrayList<ScheduleAServiceDP> ordersList;
    ScheduleAServiceDP scheduleAServiceDP;
    SharedPreferences sharedPref;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        views();
    }

    private void views() {
        rvPending=findViewById(R.id.rv_orders);
        sharedPref=getSharedPreferences("customer",MODE_PRIVATE);
        userId=sharedPref.getString("userId","");
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        pendingRef= FirebaseDatabase.getInstance().getReference("ServiceOrders").orderByChild("userId").equalTo(userId);
        ordersList=new ArrayList<>();
        rvPending.setLayoutManager(new GridLayoutManager(this,1));

        getDataForOrders();
    }

    private void getDataForOrders() {
        progressDialog.show();
        pendingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ordersList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    scheduleAServiceDP=snapshot1.getValue(ScheduleAServiceDP.class);
                    ordersList.add(scheduleAServiceDP);
                }
                Collections.reverse(ordersList);
                OrdersAdapter adapter=new OrdersAdapter(Orders.this,ordersList);
                rvPending.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}