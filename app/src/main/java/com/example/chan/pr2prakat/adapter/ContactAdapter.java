package com.example.chan.pr2prakat.adapter;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chan.pr2prakat.DBHelper.ContactTable;
import com.example.chan.pr2prakat.DBHelper.MySQLiteHelper;
import com.example.chan.pr2prakat.R;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<ContactPojo> {
    Context context;
    ArrayList<ContactPojo> contactArrayList;
    LayoutInflater inflater;
    SQLiteDatabase db;

    public ContactAdapter(Context context, int resource, ArrayList<ContactPojo> contactArrayList) {
        super(context, resource, contactArrayList);

        this.context = context;
        this.contactArrayList = contactArrayList;
        inflater = LayoutInflater.from(context);
        this.db = db;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MySQLiteHelper helper = new MySQLiteHelper(context);
        final SQLiteDatabase db = helper.getWritableDatabase();
        View view = inflater.inflate(R.layout.contact_list_item,null);
        TextView contactTV = view.findViewById(R.id.contactNameTV);
        Button deleteContactBtn = view.findViewById(R.id.deleteContactBtn);
        Button editContactBtn = view.findViewById(R.id.editContactBtn);
        final ContactPojo contactPojo = contactArrayList.get(position);
        contactTV.setText(contactPojo.getContactNamePojo());

        deleteContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_delete_contact);
                Button dialDeleteBtn = dialog.findViewById(R.id.dialDeleteBtn);
                Button dialogNoBtn = dialog.findViewById(R.id.dialNoBtn);
                dialog.show();
                dialDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int CID = contactPojo.getCID();
                        ContactTable.deleteContact(db,CID);
                        ContactAdapter.this.remove(contactArrayList.get(position));
                        ContactAdapter.this.notifyDataSetChanged();
                        db.close();
                        dialog.cancel();
                    }
                });

                dialogNoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

            }
        });
//
//        editContactBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ContactTable.fetchContact(db,)
//                final Dialog dialog = new Dialog(getContext());
//                dialog.show();
//                dialog.setContentView(R.layout.dialog_update_contact);
//                final EditText dialogContactNameEdt = dialog.findViewById(R.id.dialogContactNameEdt);
//                final EditText dialogContactNoEdt = dialog.findViewById(R.id.dialogContactNoEdt);
//                Button dialogContactSaveBtn = dialog.findViewById(R.id.dialogContactSaveBtn);
//                Button dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
//
//                dialogContactNameEdt.setText(contactPojo.getContactNamePojo());
//                dialogContactNoEdt.setText(""+contactPojo.getContactNoPojo());
//
//                dialogContactSaveBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        String contactName = dialogContactNameEdt.getText().toString().trim();
//                        String contactNoSt = dialogContactNoEdt.getText().toString();
//                        if (!contactName.isEmpty()&&!contactNoSt.isEmpty()) {
//
//                            long contactNo =  Long.parseLong(contactNoSt);
//
//                            if (contactNoSt.length()>9 && contactNoSt.length()<11){
//
//
//                                ContentValues cv = new ContentValues();
//                                cv.put("NAME",contactName);
//                                cv.put("CONTACT",contactNo);
//                                int CID = contactPojo.getCID();
//                                int res = ContactTable.updateContact(db,CID,cv);
//                                dialog.cancel();
//                                db.close();
//                                if (res>0){
//
//                                    ContactAdapter.this.notifyDataSetChanged();
//                                    Toast.makeText(getContext(), "Contact Updated", Toast.LENGTH_SHORT).show();
//
//
//                                }else Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
//                            }else Toast.makeText(getContext(), "Not a valid number", Toast.LENGTH_SHORT).show();
//
//                        }
//                        else Toast.makeText(getContext(), "Fill all details", Toast.LENGTH_SHORT).show();
//
//
//
//                    }
//                });
//// lo.
//                dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.cancel();
//                    }
//                });
//            }
//        });

        return view;
    }

}
