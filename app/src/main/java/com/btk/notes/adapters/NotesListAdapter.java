package com.btk.notes.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.btk.notes.R;
import com.btk.notes.model.NoteEntity;
import com.btk.notes.utils.Constants;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesListHolder> {

    private final String TAG = NotesListAdapter.class.getSimpleName();
    private List<NoteEntity> mAllNotes;

    private onItemClickListener listener;

    public NotesListAdapter(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_list_item, null, false);
        return new NotesListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListHolder notesListHolder, int i) {
        NoteEntity entity = mAllNotes.get(i);
        notesListHolder.mTitle.setText(entity.getTitle());
        notesListHolder.mDescription.setText(entity.getDescription());
        notesListHolder.mCardView.setOnClickListener(view -> listener.onClick(i));
        notesListHolder.mCardView.setBackgroundColor(Constants.getColor(entity.getBgColor()));
    }

    @Override
    public int getItemCount() {
        return mAllNotes.size();
    }

    public void SetData(List<NoteEntity> list) {
        this.mAllNotes = list;
        notifyDataSetChanged();
    }

    public NoteEntity getItem(int pos) {
        return mAllNotes.get(pos);
    }

    public interface onItemClickListener {
        void onClick(int pos);
    }

    class NotesListHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mDescription;
        CardView mCardView;

        private NotesListHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_note_title_name);
            mDescription = itemView.findViewById(R.id.tv_card_description);
            mCardView = itemView.findViewById(R.id.id_card);
        }
    }
}
