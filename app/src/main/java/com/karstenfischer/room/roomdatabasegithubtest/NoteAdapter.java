package com.karstenfischer.room.roomdatabasegithubtest;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {


    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        //public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        //public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority() &&

                    oldItem.getBlutzucker() == newItem.getBlutzucker() &&
                    oldItem.getBe() == newItem.getBe() &&
                    oldItem.getBolus() == newItem.getBolus() &&
                    oldItem.getKorrektur() == newItem.getKorrektur() &&
                    oldItem.getBasal() == newItem.getBasal();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int position) {
        Note currentNote = getItem(position);
        noteHolder.tvTitle.setText(currentNote.getTitle());
        noteHolder.tvDescription.setText(currentNote.getDescription());
        noteHolder.tvPriority.setText(String.valueOf(currentNote.getPriority()));

        noteHolder.tvBlutzucker.setText(String.valueOf(currentNote.getBlutzucker()));
        noteHolder.tvBe.setText(String.valueOf(currentNote.getBe()));
        noteHolder.tvBolus.setText(String.valueOf(currentNote.getBolus()));
        noteHolder.tvKorrektur.setText(String.valueOf(currentNote.getKorrektur()));
        noteHolder.tvBasal.setText(String.valueOf(currentNote.getBasal()));
    }

    //Für onSwipe
    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvPriority;

        private TextView tvBlutzucker;
        private TextView tvBe;
        private TextView tvBolus;
        private TextView tvKorrektur;
        private TextView tvBasal;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPriority = itemView.findViewById(R.id.tvPriority);

            tvBlutzucker = itemView.findViewById(R.id.tvBlutzucker);
            tvBe = itemView.findViewById(R.id.tvBe);
            tvBolus = itemView.findViewById(R.id.tvBolus);
            tvKorrektur = itemView.findViewById(R.id.tvKorrektur);
            tvBasal = itemView.findViewById(R.id.tvBasal);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }

                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
