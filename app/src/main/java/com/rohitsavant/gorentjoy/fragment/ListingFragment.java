package com.rohitsavant.gorentjoy.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.rohitsavant.gorentjoy.adapter.ListingsAdapter;
import com.rohitsavant.gorentjoy.pojo.Category;
import com.rohitsavant.gorentjoy.pojo.CategoryResponse;
import com.rohitsavant.gorentjoy.pojo.ListingResponse;
import com.rohitsavant.gorentjoy.pojo.Listings;
import com.rohitsavant.gorentjoy.support.Constants;
import com.rohitsavant.gorentjoy.support.SupportFunctions;

import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String[] tinyUrl = {null};
    ArrayList<Listings> listings;
    ListingsAdapter listingsAdapter;
    @BindView(R.id.listing_recycler) RecyclerView listingRecycler;
    RequestQueue mVolleyRequest;
    public static LinearLayout mLinemptyList;

    private OnFragmentInteractionListener mListener;

    public ListingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListingFragment newInstance(String param1, String param2) {
        ListingFragment fragment = new ListingFragment();
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
        View view= inflater.inflate(R.layout.fragment_listing, container, false);
        ButterKnife.bind(this,view);
        mVolleyRequest = Volley.newRequestQueue(getContext());

        mLinemptyList=view.findViewById(R.id.empty_list);

        setListingAdapter(new LinearLayoutManager(getContext()));
        GetAds();
        return view;
    }

    public void setListingAdapter(RecyclerView.LayoutManager mLayoutManager){
        listings=new ArrayList<>();
        SnapHelper snapHelper = new LinearSnapHelper();

        listingsAdapter = new ListingsAdapter(listings,getContext(),getActivity().getSupportFragmentManager());
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        listingRecycler.setLayoutManager(mLayoutManager);
        listingRecycler.setItemAnimator(new DefaultItemAnimator());
        //snapHelper.attachToRecyclerView(mRecyclerView);
        listingRecycler.setAdapter(listingsAdapter);
        listingRecycler.setNestedScrollingEnabled(false);
        listingRecycler.setFocusable(false);

        listingsAdapter.notifyDataSetChanged();
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

    private void GetAds() {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Getting Products...");
        dialog.setCancelable(false);
        dialog.show();

        String api_key=getResources().getString(R.string.api_key);
        HashMap<String,String> param = new HashMap();
        param.put(Constants.CATEGORY_ID,mParam1);
        param.put(Constants.API_KEY,api_key);

        //url with params
        String Longurl= SupportFunctions.appendParam(MyApplication.LISTINGS,param);

        URLShortener.shortUrl(Longurl, new URLShortener.LoadingCallback() {
            @Override
            public void startedLoading() {
                Log.e("Product url","Loading...");
                // outputLink.setText("Loading...");
            }

            @Override
            public void finishedLoading(@Nullable String shortUrl) {
                //make sure the string is not null
                if(shortUrl != null)
                {
                    Log.e("Product url",shortUrl);
                    tinyUrl[0] =shortUrl;
                    Log.e("Product url",tinyUrl[0]);

                    JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,tinyUrl[0],null,
                            // The third parameter Listener overrides the method onResponse() and passes
                            //JSONObject as a parameter
                            new Response.Listener<JSONObject>() {

                                // Takes the response from the JSON request
                                @Override
                                public void onResponse(JSONObject response) {

                                    try
                                    {
                                        Log.e("Cat Json_string",response.toString());
                                        Gson gson = new Gson();

                                        ListingResponse listingResponse= gson.fromJson(response.toString(), ListingResponse.class);
                                        Listings[] listing=listingResponse.getAds();

                                        for (Listings item : listing) {

                                            listings.add(item);
                                            Log.e("Product title: ",item.getTitle());
                                        }
                                        listingsAdapter.notifyDataSetChanged();

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
