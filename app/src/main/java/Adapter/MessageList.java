package Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import DataStruct.SmsData;
import comyware.example.duongtan.spyware.R;

public class MessageList extends ArrayAdapter<SmsData> {


    private Context context;
    private ArrayList<SmsData> arrayData;
    private int layoutId;
    public MessageList(Context context, int resource, ArrayList<SmsData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayData = objects;
        this.layoutId = resource;

    }

    @Override
    public int getCount(){
        return arrayData.size();
    }


    @Override
    public View getView(int position, View ConvertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(context);
        ConvertView = inflater.inflate(layoutId,null);

        TextView textViewName = (TextView)ConvertView.findViewById(R.id.MessageListName);
        TextView textViewContent = (TextView)ConvertView.findViewById(R.id.MessageListContent);

        textViewName.setText(arrayData.get(position).getNumber());
        textViewContent.setText(arrayData.get(position).getBody());

        return ConvertView;
    }
}
