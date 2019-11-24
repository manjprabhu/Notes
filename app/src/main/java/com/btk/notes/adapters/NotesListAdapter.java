package com.btk.notes.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.btk.notes.Model.NoteEntity;
import com.btk.notes.R;
import com.btk.notes.Utils.Constants;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesListHolder> {

    private final String TAG  = NotesListAdapter.class.getSimpleName();
    private List<NoteEntity> mAllNotes;

    private onItemClickListener listener;

    public NotesListAdapter(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_list_item,null,false);
        return new NotesListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListHolder notesListHolder, int i) {
        NoteEntity entity = mAllNotes.get(i);
        Log.v(TAG,"onBindViewHolder position:"+i+":"+entity.getBgColor());
        notesListHolder.mTitle.setText(entity.getTitle());
        notesListHolder.mDescription.setText(entity.getDescription());
        notesListHolder.mCardView.setOnClickListener(view -> listener.onClick(i));
        notesListHolder.mCardView.setBackgroundColor(Constants.getColor(entity.getBgColor()));
    }

    @Override
    public int getItemCount() {
        return mAllNotes.size();
    }

    public interface onItemClickListener {
        void onClick(int pos);
    }

    class NotesListHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mDescription;
        CardView mCardView;

        private NotesListHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tv_note_title_name);
            mDescription = (TextView) itemView.findViewById(R.id.tv_card_description);
            mCardView =(CardView) itemView.findViewById(R.id.id_card);
        }
    }

    public void SetData(List<NoteEntity> list) {
        this.mAllNotes = list;
        notifyDataSetChanged();
    }

    public NoteEntity getItem(int pos) {
        Log.v(TAG,"DeleteRow:"+pos);
        return mAllNotes.get(pos);
    }

    public void undoDelete(NoteEntity entity,int pos) {
        mAllNotes.add(pos,entity);
        notifyItemInserted(pos);
    }
}