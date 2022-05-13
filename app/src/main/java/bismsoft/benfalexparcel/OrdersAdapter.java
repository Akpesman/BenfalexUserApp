package bismsoft.benfalexparcel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bismsoft.benfalexparcel.DataProvider.ScheduleAServiceDP;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHold> {
    Context mContext;
    ArrayList<ScheduleAServiceDP> list;

    public OrdersAdapter(Context mContext, ArrayList<ScheduleAServiceDP> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.custom_order_view,parent,false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        String status=list.get(holder.getAdapterPosition()).getOrderStatus();
        holder.serviceType.setText(list.get(holder.getAdapterPosition()).getServiceType());
        holder.firstName.setText(list.get(holder.getAdapterPosition()).getFirstName());
        holder.lastName.setText(list.get(holder.getAdapterPosition()).getLastName());
        holder.email.setText(list.get(holder.getAdapterPosition()).getEmail());
        holder.phone.setText(list.get(holder.getAdapterPosition()).getPhone());
        holder.pickupAddress.setText(list.get(holder.getAdapterPosition()).getPickupAddress());
        holder.deliveryAddess.setText(list.get(holder.getAdapterPosition()).getDeliveryAddress());
        holder.pickupDate.setText(list.get(holder.getAdapterPosition()).getDate());
        holder.pickupTime.setText(list.get(holder.getAdapterPosition()).getTime());
        holder.comments.setText(list.get(holder.getAdapterPosition()).getComments());
        holder.orderStatus.setText(status);
        String prices=list.get(holder.getAdapterPosition()).getPrice();
        float pricef=Float.parseFloat(prices);
        DecimalFormat df = new DecimalFormat("#.##");
        holder.tvPrice.setText(""+df.format(pricef)+" £");

        Picasso.get().load(list.get(holder.getAdapterPosition()).getPictureLink()).into(holder.orderImg);


        if(status.equalsIgnoreCase("pending"))
        {
            holder.btnGreen.setText("Mark as Pickedup");
        }
        else if(status.equalsIgnoreCase("pickedup"))
        {
            holder.btnGreen.setText("Mark as Delivered");
            holder.btnRed.setVisibility(View.GONE);
        }
        else if(status.equalsIgnoreCase("delivered"))
        {
            holder.btnGreen.setVisibility(View.GONE);
            holder.btnRed.setVisibility(View.GONE);
        }

        holder.btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(mContext);
                dialog.setContentView(R.layout.custom_confirm_cancel);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setTitle("Cancel Order");
                Button btnYes=dialog.findViewById(R.id.btn_yes);
                Button btnNo=dialog.findViewById(R.id.btn_no);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference("ServiceOrders").child(list.get(holder.getAdapterPosition()).getOrderKey()).removeValue();
                        Toast.makeText(mContext, "Order Cancelled", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.pickupAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MapsActivity.class).putExtra("latitude", list.get(holder.getAdapterPosition()).getPickupLat()).putExtra("longitude", list.get(holder.getAdapterPosition()).getPickupLog()));
            }
        });

        holder.deliveryAddess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MapsActivity.class).putExtra("latitude",list.get(holder.getAdapterPosition()).getDeliveryLat()).putExtra("longitude",list.get(holder.getAdapterPosition()).getDeliveryLog()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHold extends RecyclerView.ViewHolder {
        TextView serviceType,firstName,lastName,email,phone,pickupAddress,deliveryAddess,tvPrice
                ,pickupDate,pickupTime,comments,orderStatus;
        ImageView orderImg;
        Button btnGreen,btnRed;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            serviceType=itemView.findViewById(R.id.tv_servicetype);
            firstName=itemView.findViewById(R.id.tv_firstname);
            lastName=itemView.findViewById(R.id.tv_lastname);
            email=itemView.findViewById(R.id.tv_email);
            phone=itemView.findViewById(R.id.tv_phone);
            pickupAddress=itemView.findViewById(R.id.tv_pickup_address);
            deliveryAddess=itemView.findViewById(R.id.tv_delivery_address);
            pickupDate=itemView.findViewById(R.id.tv_pickup_date);
            pickupTime=itemView.findViewById(R.id.tv_pickup_time);
            comments=itemView.findViewById(R.id.tv_comments);
            orderStatus=itemView.findViewById(R.id.tv_status);
            orderImg=itemView.findViewById(R.id.iv_order_img);
            btnRed=itemView.findViewById(R.id.btn_red);
            btnGreen=itemView.findViewById(R.id.btn_green);
            tvPrice=itemView.findViewById(R.id.tv_price);

        }
    }
}
