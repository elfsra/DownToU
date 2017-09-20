package com.tatlicilar.downtoup.fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tatlicilar.downtoup.EbookAdapter;
import com.tatlicilar.downtoup.EbookIcerik;
import com.tatlicilar.downtoup.EgitimActivity;
import com.tatlicilar.downtoup.EgitimKategori;
import com.tatlicilar.downtoup.Login;
import com.tatlicilar.downtoup.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Ebook.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ebook#newInstance} factory method to
 * create an instance of this fragment.
 */
//public class Ebook extends Fragment implements EbookAdapter.AdapterListener,RecyclerView.OnItemTouchListener{
    //sezin
public class Ebook extends Fragment implements RecyclerView.OnItemTouchListener{
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog progressDialog;
//    List<EbookIcerik> ebooks = new ArrayList<>();
//    EbookAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public Ebook() {
        // Required empty public constructor
    }

  // TODO: Rename and change types and number of parameters
    public static Ebook newInstance() {
        Ebook fragment = new Ebook();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

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
        return inflater.inflate(R.layout.fragment_ebook, container, false);
    }

//    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final ImageView imageView = (ImageView)view.findViewById(R.id.image);
        View btnDownloadAsFile = view.findViewById(R.id.btn_download_file);
        View btnShowFile = view.findViewById(R.id.btn_goruntule);

        btnDownloadAsFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://downtoup-a09e8.appspot.com").child("gelisimpsikolojisi.pdf");

                //get download file url
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("Main", "File uri: " + uri.toString());
                    }
                });

                //download the file
                try {
                    showProgressDialog("", "Dosya İndiriliyor...");
                    final File localFile = File.createTempFile("dosya", "pdf");
                    storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                            dismissProgressDialog();
                            Toast.makeText(getActivity(),"İndirme başarılı", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            dismissProgressDialog();
                            Toast.makeText(getContext(),"İndirme başarısız", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e ) {
                    e.printStackTrace();
                    Log.e("Main", "IOE Exception");
                }
            }
        });

        btnShowFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://downtoup-a09e8.appspot.com").child("gelisimpsikolojisi.pdf");

//                //get download file url
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("Main", "File uri: " + uri.toString());
                        openPDF(uri);
                    }
                });

            }
        });

    }

    void openPDF(final Uri path) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(),"PDF Görüntüleyici cihazda yüklü değil", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();

    }
    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(getContext(), title, msg, true);
    }


    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new EbookAdapter(ebooks,this,this.getContext());
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        RecyclerView.ItemDecoration itemDecoration = new
//                DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(itemDecoration);
//        ebookIcerikOlustur();
//    @Override
//    public void onClickName(EbookIcerik ebook) {
//
//        Intent newIntent=new Intent(getActivity(),EgitimActivity.class);
//        newIntent.putExtra("url",ebook.getUrl());
//        startActivity(newIntent);
//    }
//
//    @Override
//    public void onClickImage(EbookIcerik ebook) {
////        Toast.makeText(this, egitim.getEgitimIsmi(),
////                Toast.LENGTH_LONG).show();
////        questionFragment.openProfile(v);
//    }
//    private void ebookIcerikOlustur() {
//        ebooks.add(new EbookIcerik("Pdf:1","Neval Göksel","Ebeveynler için",R.drawable.pdf_icon,"http://www.letstry-ict.eu/advisor/frontend/index.php?showDetail=1&detail_id=34&search_term=&filter_7_5=1"));
//        ebooks.add(new EbookIcerik("Pdf:2","Neval Göksel","Melekler için",R.drawable.pdf_icon,"http://www.letstry-ict.eu/advisor/frontend/index.php?showDetail=1&detail_id=22&search_term=&filter_7_5=1"));
//
//        mAdapter.notifyDataSetChanged();
//    }

