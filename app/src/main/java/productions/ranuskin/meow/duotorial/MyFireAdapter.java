package productions.ranuskin.meow.duotorial;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class MyFireAdapter extends FirebaseRecyclerAdapter<ChatClassroomMessage, MyFireAdapter.MyViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private Context context;
    private RecyclerView recyclerView;

    public MyFireAdapter(@NonNull FirebaseRecyclerOptions<ChatClassroomMessage> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder vh, int position, @NonNull final ChatClassroomMessage model) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getDisplayName().equals(model.getUserName())) {
            vh.tvUsername.setVisibility(View.GONE);
            vh.tvUserDuotorial.setVisibility(View.GONE);
            vh.tvMessageTime.setVisibility(View.GONE);
            vh.tvMessageText.setVisibility(View.GONE);
            vh.tvStepNumber.setVisibility(View.GONE);
            vh.tvMyText.setVisibility(View.VISIBLE);
            vh.tvMyDate.setVisibility(View.VISIBLE);
            vh.tvMyText.setText(model.getMessageText());
            vh.tvMyDate.setText(translateTimestamp(model));

        } else {
            vh.tvMyText.setVisibility(View.GONE);
            vh.tvMyDate.setVisibility(View.GONE);
            vh.tvUsername.setVisibility(View.VISIBLE);
            vh.tvUserDuotorial.setVisibility(View.VISIBLE);
            vh.tvMessageTime.setVisibility(View.VISIBLE);
            vh.tvMessageText.setVisibility(View.VISIBLE);
            vh.tvStepNumber.setVisibility(View.VISIBLE);
            vh.tvUsername.setText(model.getUserName());
            vh.tvMessageText.setText(model.getMessageText());
            vh.tvUserDuotorial.setText(model.getUserDuotorial());
            Integer inputInt = model.getStepNumber();
            vh.tvStepNumber.setText(inputInt.toString());
            String dateDisplay = translateTimestamp(model);
            vh.tvMessageTime.setText(dateDisplay);
            vh.tvUserDuotorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddToDuotorialDatabase.addData(model.getUserDuotorial(), model.getImageURL());
                    Intent intent = new Intent(context, DuotorialActivity.class);
                    intent.putExtra("TITLE", model.getUserDuotorial());
                    intent.putExtra("IMAGE", model.getImageURL());
                    intent.putExtra("DESCRIPTION", "");
                    context.startActivity(intent);

                }
            });
        }
        //*4
        //    private String messageText;
        //    private String userName;
        //    private String userDuotorial;
        //    private int stepNumber;
        //    private long messageTime;
    }

    private String translateTimestamp(@NonNull ChatClassroomMessage model) {
        Long tempDate = model.getMessageTime();
        Date date = new Date(tempDate);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.classroom_chat_row, parent, false));
    }

    //Reperesents a single row
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        TextView tvMessageText;
        TextView tvUserDuotorial;
        TextView tvStepNumber;
        TextView tvMessageTime;
        TextView tvMyText;
        TextView tvMyDate;

        //...*4
        public MyViewHolder(View v) {
            super(v);

            tvUsername = v.findViewById(R.id.tvUserNameChat);
            tvMessageText = v.findViewById(R.id.tvMessage);
            tvUserDuotorial = v.findViewById(R.id.tvTutorial);
            tvStepNumber = v.findViewById(R.id.tvStep);
            tvMessageTime = v.findViewById(R.id.tvDate);
            tvMyText = v.findViewById(R.id.tvMyTextMessage);
            tvMyDate = v.findViewById(R.id.tvMyDate);
            //...
        }
    }

//    @Override
//    public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {
//        super.onChildChanged(type, snapshot, newIndex, oldIndex);
//
//        if (recyclerView!=null)
//        recyclerView.smoothScrollToPosition(newIndex);
//        //if (newIndex >= getItemCount()){
//         //   Toast.makeText(context, "good", Toast.LENGTH_SHORT).show();
//
//        //}
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        this.recyclerView = recyclerView;
//    }
//
//    @Override
//    public void onDataChanged() {
//        super.onDataChanged();
//   //     Toast.makeText(context, "good", Toast.LENGTH_SHORT).show();
//
//
//    }
}
