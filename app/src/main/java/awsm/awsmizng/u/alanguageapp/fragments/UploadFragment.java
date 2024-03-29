package awsm.awsmizng.u.alanguageapp.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import awsm.awsmizng.u.alanguageapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.uploadViews)
    FrameLayout uploadViews;
    Unbinder unbinder;

    public UploadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        unbinder = ButterKnife.bind(this, view);

        TabLayout.Tab article = tabLayout.newTab();
        article.setText("ARTICLES");
        tabLayout.addTab(article);

        TabLayout.Tab quiz = tabLayout.newTab();
        quiz.setText("QUIZZES");
        tabLayout.addTab(quiz);

        

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()){
                    case 0 :
                        fragment = new UploadArticlesFragment();
                        break;
                    case 1:
                        fragment = new UploadQuizzesFragment();
                        break;
                }
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.uploadViews, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fragment fragment = new UploadArticlesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.uploadViews, fragment).commit();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromParentFragment(Uri uri);
    }
}
