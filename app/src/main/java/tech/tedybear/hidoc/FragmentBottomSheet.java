package tech.tedybear.hidoc;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FragmentBottomSheet extends BottomSheetDialogFragment {

    public TextInputLayout regPhoneNo;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Preferences preferences;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        preferences = new Preferences(getContext());
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(view);

        //Firebase Instantiation
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        //Hooks to all xml elements in activity_sign_up.xml
        regPhoneNo = view.findViewById(R.id.reg_phone);

        Button button = view.findViewById(R.id.registers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePhoneNo()) {
                    return;
                }

                String phoneNo = regPhoneNo.getEditText().getText().toString();
                preferences.saveSPString(preferences.SP_NOMOR, phoneNo);
                Intent intent = new Intent(getContext(), VerifyPhoneNo.class);
                intent.putExtra("phoneNo", phoneNo);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        return dialog;
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

}
