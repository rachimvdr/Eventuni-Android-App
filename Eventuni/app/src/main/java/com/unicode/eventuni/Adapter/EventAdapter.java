package com.unicode.eventuni.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.unicode.eventuni.Activity.EditActivity;
import com.unicode.eventuni.Config;
import com.unicode.eventuni.Model.Event;
import com.unicode.eventuni.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{
    List<Event> mEventList;

    public EventAdapter(List<Event> eventList) {
        mEventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){
        holder.mTextViewName.setText(mEventList.get(position).getName());
        holder.mTextViewDescription.setText(mEventList.get(position).getDescription());
        /*holder.mTextViewLongdescription.setText(mEventList.get(position).getLongdescription());*/
        holder.mTextViewKategori.setText(mEventList.get(position).getKategori());
        holder.mTextViewKegiatan.setText(mEventList.get(position).getKegiatan());
        /*holder.mTextViewLokasi.setText(mEventList.get(position).getLokasi());
        holder.mTextViewTiket.setText(mEventList.get(position).getTiket());
        holder.mTextViewWaktu.setText(mEventList.get(position).getWaktu());
        holder.mTextViewCp.setText(mEventList.get(position).getCp());
        holder.mTextViewLink.setText(mEventList.get(position).getLink());*/
        Glide.with(holder.itemView.getContext())
                .load(Config.IMAGES_URL + mEventList.get(position).getImage())
                .apply(new RequestOptions().override(0, 0))
                .into(holder.mImageViewFoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), EditActivity.class);
                mIntent.putExtra("Id", mEventList.get(position).getId());
                mIntent.putExtra("Name", mEventList.get(position).getName());
                mIntent.putExtra("Description", mEventList.get(position).getDescription());
                mIntent.putExtra("Longdescription", mEventList.get(position).getLongdescription());
                mIntent.putExtra("Kategori", mEventList.get(position).getKategori());
                mIntent.putExtra("Kegiatan", mEventList.get(position).getKegiatan());
                mIntent.putExtra("Lokasi", mEventList.get(position).getLokasi());
                mIntent.putExtra("Tiket", mEventList.get(position).getTiket());
                mIntent.putExtra("Waktu", mEventList.get(position).getWaktu());
                mIntent.putExtra("Cp", mEventList.get(position).getCp());
                mIntent.putExtra("Link", mEventList.get(position).getLink());
                mIntent.putExtra("Image", mEventList.get(position).getImage());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return mEventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewName, mTextViewDescription, mTextViewLongdescription, mTextViewKategori, mTextViewKegiatan, mTextViewLokasi, mTextViewTiket, mTextViewWaktu, mTextViewCp, mTextViewLink;
        public ImageView mImageViewFoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewName = (TextView) itemView.findViewById(R.id.tv_name);
            mTextViewDescription = (TextView) itemView.findViewById(R.id.tv_description);
            /*mTextViewLongdescription = (TextView) itemView.findViewById(R.id.tv_longdescription);*/
            mTextViewKategori = (TextView) itemView.findViewById(R.id.tv_kategori);
            mTextViewKegiatan = (TextView) itemView.findViewById(R.id.tv_kegiatan);
            /*mTextViewLokasi = (TextView) itemView.findViewById(R.id.tv_lokasi);
            mTextViewTiket = (TextView) itemView.findViewById(R.id.tv_tiket);
            mTextViewWaktu = (TextView) itemView.findViewById(R.id.tv_waktu);
            mTextViewCp = (TextView) itemView.findViewById(R.id.tv_cp);
            mTextViewLink = (TextView) itemView.findViewById(R.id.tv_link);*/
            mImageViewFoto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
