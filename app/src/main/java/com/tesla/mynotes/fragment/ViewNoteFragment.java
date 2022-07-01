package com.tesla.mynotes.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tesla.mynotes.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewNoteFragment extends Fragment {


    public ViewNoteFragment() {
        // Required empty public constructor
    }

    String tittleNote , contentNote;
    int colorNote;

    public ViewNoteFragment(String tittleNote, String contentNote, int colorNote) {
        this.tittleNote = tittleNote;
        this.contentNote = contentNote;
        this.colorNote = colorNote;
    }


    public ViewNoteFragment(int contentLayoutId, String tittleNote, String contentNote, int colorNote) {
        super(contentLayoutId);
        this.tittleNote = tittleNote;
        this.contentNote = contentNote;
        this.colorNote = colorNote;
    }

    public static ViewNoteFragment newInstance(String param1, String param2) {
        ViewNoteFragment fragment = new ViewNoteFragment();
        return fragment;
    }

    TextView tvTittleNotes;
    ImageView buttonBack;
    ConstraintLayout colorBackground;
    EditText editTextContent;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTittleNotes = view.findViewById(R.id.id_tittle_view_note);
        editTextContent = view.findViewById(R.id.id_content_view_note);
        buttonBack = view.findViewById(R.id.id_btn_back_note);
        colorBackground = view.findViewById(R.id.id_constraint_color_view_note);

        tvTittleNotes.setText(tittleNote);
        editTextContent.setText(contentNote);
        colorBackground.setBackgroundColor(ActivityCompat.getColor(getActivity().getApplicationContext(),colorNote));
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(getId(),new ListNotesFragment());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_note, container, false);
    }
}