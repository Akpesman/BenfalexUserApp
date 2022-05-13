package bismsoft.benfalexparcel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<MainDataProvider> mList;
    private OnItemClickListener mListener;
    private int height = 0;

    public interface OnItemClickListener{
        void  onItemClick(int position);
        void  onButtonChange(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MainAdapter(Context context, ArrayList<MainDataProvider> list){

        this.mcontext = context;
        this.mList = list;


    }


    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.main_customise_rv,parent,false);

        ViewHolder viewHolder = new ViewHolder(view,mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {

        MainDataProvider homeDataProvider  = mList.get(position);

        TextView titleName = holder.titleName;
        RelativeLayout linearLayout = holder.linearLayout;
        ImageView picture = holder.picture;

        titleName.setText(homeDataProvider.getTitle());
        picture.setImageResource(homeDataProvider.getPicture());




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleName,number,nameEng;
        public RelativeLayout linearLayout;
        ImageView picture;



        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);


            titleName =itemView.findViewById(R.id.main_customise_title_tv);
         linearLayout = itemView.findViewById(R.id.main_customise_linearlayout);
         picture = itemView.findViewById(R.id.imag_custome);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener!=null){

                        int position  = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}
