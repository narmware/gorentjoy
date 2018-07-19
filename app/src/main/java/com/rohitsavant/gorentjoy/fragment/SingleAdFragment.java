package com.rohitsavant.gorentjoy.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pddstudio.urlshortener.URLShortener;
import com.rohitsavant.gorentjoy.MyApplication;
import com.rohitsavant.gorentjoy.R;
import com.rohitsavant.gorentjoy.adapter.GalleryAdapter;
import com.rohitsavant.gorentjoy.adapter.ListingsAdapter;
import com.rohitsavant.gorentjoy.pojo.GalleryImages;
import com.rohitsavant.gorentjoy.pojo.ListingResponse;
import com.rohitsavant.gorentjoy.pojo.Listings;
import com.rohitsavant.gorentjoy.pojo.SingleAdResponse;
import com.rohitsavant.gorentjoy.support.Constants;
import com.rohitsavant.gorentjoy.support.SupportFunctions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingleAdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingleAdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleAdFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.ad_recycler) RecyclerView adRecycler;
    RequestQueue mVolleyRequest;
    ArrayList<GalleryImages> images;
    ArrayList<String> photoUrl;
    GalleryAdapter galleryAdapter;
    String[] tinyUrl = {null};

    public SingleAdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleAdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleAdFragment newInstance(String param1, String param2) {
        SingleAdFragment fragment = new SingleAdFragment();
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
        View view= inflater.inflate(R.layout.fragment_single_ad, container, false);
        ButterKnife.bind(this,view);
        mVolleyRequest = Volley.newRequestQueue(getContext());

        setListingAdapter(new GridLayoutManager(getContext(),3));
        GetSingleAdInfo();
        return view;
    }

    public void setListingAdapter(RecyclerView.LayoutManager mLayoutManager){
        images=new ArrayList<>();
        photoUrl=new ArrayList<>();

        SnapHelper snapHelper = new LinearSnapHelper();

        galleryAdapter = new GalleryAdapter(images,photoUrl,getContext());

        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        adRecycler.setLayoutManager(mLayoutManager);
        adRecycler.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        adRecycler.setAdapter(galleryAdapter);
        adRecycler.setNestedScrollingEnabled(false);
        adRecycler.setFocusable(false);

        galleryAdapter.notifyDataSetChanged();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void GetSingleAdInfo() {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting Ad...");
        dialog.setCancelable(false);
        dialog.show();

        String api_key=getResources().getString(R.string.api_key);

       /*
        HashMap<String,String> param = new HashMap();
        param.put(Constants.CATEGORY_ID,mParam1);
        param.put(Constants.API_KEY,api_key);*/

        //url with params
       // String Longurl= SupportFunctions.appendParam(MyApplication.LISTINGS,param);

        String Longurl=MyApplication.LISTINGS+"/"+mParam1+"?apikey="+api_key;
        URLShortener.shortUrl(Longurl, new URLShortener.LoadingCallback() {
            @Override
            public void startedLoading() {
                Log.e("Advertise url","Loading...");
            }

            @Override
            public void finishedLoading(@Nullable String shortUrl) {
                //make sure the string is not null
                if(shortUrl != null)
                {
                    Log.e("Advertise url",shortUrl);
                    tinyUrl[0] =shortUrl;
                    Log.e("Advertise url",tinyUrl[0]);

                    JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,tinyUrl[0],null,
                            // The third parameter Listener overrides the method onResponse() and passes
                            //JSONObject as a parameter
                            new Response.Listener<JSONObject>() {

                                // Takes the response from the JSON request
                                @Override
                                public void onResponse(JSONObject response) {

                                    try
                                    {
                                        Log.e("Advertise Json_string",response.toString());
                                        Gson gson = new Gson();

                                        SingleAdResponse singleAdResponse= gson.fromJson(response.toString(), SingleAdResponse.class);
                                        Listings listing=singleAdResponse.getAd();
                                        GalleryImages[] galleryImages=null;

                                        galleryImages=listing.getImages();

                                        for (GalleryImages image : galleryImages) {

                                            images.add(image);
                                            photoUrl.add(image.getImage());

                                            Log.e("Advertise Image url: ",image.getImage());

                                        }
                                        galleryAdapter.notifyDataSetChanged();

                                    } catch (Exception e) {

                                        e.printStackTrace();
                                        dialog.dismiss();

                                    }
                                    dialog.dismiss();
                                }
                            },
                            // The final parameter overrides the method onErrorResponse() and passes VolleyError
                            //as a parameter
                            new Response.ErrorListener() {
                                @Override
                                // Handles errors that occur due to Volley
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley", "Test Error");
                                    //showNoConnectionDialog();
                                    dialog.dismiss();

                                }
                            }
                    );
                    mVolleyRequest.add(obreq);
                }
            }
        });

    }

}
