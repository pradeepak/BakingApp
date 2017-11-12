package com.udacity.pk.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.pk.bakingapp.Config.RecipeApiClient;
import com.udacity.pk.bakingapp.Config.RecipeApiInterface;
import com.udacity.pk.bakingapp.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment implements CardClickListener, Callback<List<Recipe>> {

    public static final String TAG = RecipeListFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    private RecipeApiInterface recipeApiInterface;

    private RecyclerView recipeRecyclerView;

    private List<Recipe> recipeList;

    private RecipeListAdapter recipeListAdapter;

    private Toolbar toolbar;

    private boolean tabletMode;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeListFragment newInstance() {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        tabletMode = getResources().getBoolean(R.bool.tabletMode);
        return inflater.inflate(R.layout.recipe_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAppBar(view);
        recipeRecyclerView = (RecyclerView) view.findViewById(R.id.recipeList);
        recipeApiInterface = RecipeApiClient.getRetrofitClient().create(RecipeApiInterface.class);
        Call<List<Recipe>> call = recipeApiInterface.getRecipeList();
        call.enqueue(this);
    }

    private void setupAppBar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.recipeListToolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.recipe_list_title);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

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

    @Override
    public void onCardClick(Recipe recipe) {
        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailFragment.RECIPE_KEY, recipe);
        startActivity(intent);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        if (response.body() != null) {
            recipeList = response.body();
            recipeListAdapter = new RecipeListAdapter(recipeList, this);
            recipeRecyclerView.setAdapter(recipeListAdapter);

            recipeRecyclerView
                .setLayoutManager(tabletMode ? new GridLayoutManager(getContext(), 3) : new LinearLayoutManager(getContext()));
            Log.d(TAG, "onResponse: " + recipeList.get(0).getIngredientList().get(0).getQuantity());
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Log.e(TAG, "onFailure: failed to retrieve" + t.getMessage(), t);
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
}
