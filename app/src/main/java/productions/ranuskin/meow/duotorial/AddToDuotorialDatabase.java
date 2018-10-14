package productions.ranuskin.meow.duotorial;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ran Loock on 4/23/2018.
 *  Copyright © 2018 Ran Loock. All rights reserved.
 */

public class AddToDuotorialDatabase {
    public static void addData(final String title, final String imageURL) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference userDuoAmount = FirebaseDatabase.getInstance().getReference().child("users")
                .child(user.getUid()).child("duotorialAmount");
        userDuoAmount.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData mutableData) {
                Integer currentValue = mutableData.getValue(Integer.class);
                    /*if (currentValue == null) {
                        mutableData.setValue(0);
                    } else {
                        currentValue++;
                        mutableData.setValue(currentValue);
                    }*/


                final DatabaseReference duoRef = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(user.getUid()).child("history");
                boolean flag = false;
//                while ( currentValue >= 2){
//                    currentValue %= 2;
//                    flag = true;
//                }
//                if (flag == true) {
//                    duoRef.child(currentValue.toString()).removeValue();
//                }
                final Integer finalCurrentValue = mutableData.getValue(Integer.class);
                duoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (finalCurrentValue == 0 ){
                            duoRef.child(0+"").child(title).setValue(imageURL);
                            userDuoAmount.setValue(1);
                            return;
                        }
                        Integer i = 0;
                        boolean flag = false;

                        while (dataSnapshot.child(i.toString()).hasChildren()) {
                            if (dataSnapshot.child(i.toString()).hasChild(title)) {
                                duoRef.child(i.toString()).child(title).setValue(imageURL);
                                //duoRef.child(i.toString()).child(getImage).setValue(1);
                                flag = true;
                                break;
                            }
                            i++;

                        }
                        if (!flag) {
                            Integer addto = finalCurrentValue;
                            duoRef.child(addto.toString()).child(title).setValue(imageURL);
                            // duoRef.child(finalCurrentValue.toString()).child(getImage).setValue(1);
                            userDuoAmount.setValue(addto+1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });

    }
}
