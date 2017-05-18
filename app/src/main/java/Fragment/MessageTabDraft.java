package Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import Adapter.MessageList;
import DataStruct.SmsData;
import comyware.example.duongtan.spyware.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageTabDraft.OnTabDraftFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageTabDraft#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageTabDraft extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<SmsData> smsList = new ArrayList<SmsData>();
    private ListView DraftListView;


    private OnTabDraftFragmentInteractionListener mListener;

    public MessageTabDraft() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageTabDraft.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageTabDraft newInstance(String param1, String param2) {
        MessageTabDraft fragment = new MessageTabDraft();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_tab_draft, container, false);

        DraftListView = (ListView)view.findViewById(R.id.MessageDraftListView);
        InitDraftViewInbox();

        return view;
    }

    public void InitDraftViewInbox(){
        ReadDraft();
        MessageList messageList = new MessageList(this.getContext(),R.layout.messagetablist,smsList);
        DraftListView.setAdapter(messageList);
        DraftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.setTitle(smsList.get(position).getNumber());
                builder.setMessage(smsList.get(position).getBody());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnTabDraftFragmentInteractionListener(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTabDraftFragmentInteractionListener) {
            mListener = (OnTabDraftFragmentInteractionListener) context;
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
    public interface OnTabDraftFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnTabDraftFragmentInteractionListener(Uri uri);
    }
    public void ReadDraft(){

        Uri uriInbox = Uri.parse("content://sms/draft");
        Cursor cursorInbox = getActivity().getApplicationContext().getContentResolver().query(uriInbox, null, null ,null,null);
        getActivity().startManagingCursor(cursorInbox);

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
