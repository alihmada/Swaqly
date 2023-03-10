package com.swaqly.swaqly.alertDialog;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.swaqly.swaqly.R;

public class AlertDialog extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private static boolean isVisible;

    private Context context;

    public AlertDialog(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    public void progressDialogShow() {
        //Initialize Progress Dialog
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        isVisible = true;
    }

    public void progressDialogDismiss() {
        progressDialog.dismiss();
        isVisible = false;
    }

    public void showSuccessDialog() {

    }

    public void showWarningDialog() {

    }

    public static void showErrorDialog(Context context, boolean isNetworkAvailable) {
        //Check for internet connectivity
        if (!isNetworkAvailable) {
            createDialog(context, context.getString(R.string.no_internet_title), context.getString(R.string.no_internet_msg));
        } else {
            createDialog(context, context.getString(R.string.time_out_title), context.getString(R.string.time_out_msg));
        }
    }

    public static void createDialog(Context context, String title, String message) {
        if(isVisible){
//            progressDialog.dismiss();
        }
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        // Show an alert dialog
        builder.setTitle(title).setMessage(message).setPositiveButton(context.getString(R.string.ok), (dialog, id) -> {
            // Close the dialog
            dialog.dismiss();
        });
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
