package Fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import comyware.example.duongtan.spyware.R;

import java.util.ArrayList;

import Adapter.MessageList;
import DataStruct.SmsData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageTabInbox.OnTabInboxFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageTabInbox#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageTabInbox extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<SmsData> smsList = new ArrayList<SmsData>();
    private ListView InboxListView;

    private OnTabInboxFragmentInteractionListener mListener;

    public MessageTabInbox() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageTabInbox.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageTabInbox newInstance(String param1, String param2) {
        MessageTabInbox fragment = new MessageTabInbox();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        read_message();
        MessageList messageList = new MessageList(this.getContext(),R.layout.messagetablist,smsList);
        InboxListView.setAdapter(messageList);
        InboxListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_tab_inbox, container, false);

        InboxListView = (ListView)getView().findViewById(R.id.MessageInboxListView);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnTabInboxFragmentInteractionListener(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTabInboxFragmentInteractionListener) {
            mListener = (OnTabInboxFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTabInboxFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnTabInboxFragmentInteractionListener(Uri uri);
    }
    public void read_message(){


        Uri uriInbox = Uri.parse("content://sms/inbox");
        Cursor cursorInbox = getActivity().getApplicationContext().getContentResolver().query(uriInbox, null, null ,null,null);
        getActivity().startManagingCursor(cursorInbox);

        // Read the sms data and store it in the list
        if(cursorInbox.moveToFirst()) {
            for(int i=0; i < cursorInbox.getCount(); i++) {
                SmsData sms = new SmsData();
                sms.setBody(cursorInbox.getString(cursorInbox.getColumnIndexOrThrow("body")).toString());
                sms.setNumber(cursorInbox.getString(cursorInbox.getColumnIndexOrThrow("address")).toString());
                smsList.add(sms);

                cursorInbox.moveToNext();
            }
        }
        cursorInbox.close();
    }
}
