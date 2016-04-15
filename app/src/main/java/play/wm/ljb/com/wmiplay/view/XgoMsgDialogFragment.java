package play.wm.ljb.com.wmiplay.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class XgoMsgDialogFragment extends DialogFragment {


    public static final String TAG_CONTENT_MSG = "message";
    public static final String TAG_ENTER_TEXT = "ok";
    public static final String TAG_CANCEL_TEXT = "cancel";

    private String mContentMsg = TAG_CONTENT_MSG;
    private String mEneterText = TAG_ENTER_TEXT;
    private String mCancelText = TAG_CANCEL_TEXT;
    private EnterCancelListener mListener;

    public interface EnterCancelListener {
        void enter();

        void cancel();
    }

    public XgoMsgDialogFragment() {
    }

    public XgoMsgDialogFragment setEnterCancelListener(EnterCancelListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle!=null){
            mContentMsg = bundle.getString(TAG_CONTENT_MSG);
            mEneterText = bundle.getString(TAG_ENTER_TEXT);
            mCancelText = bundle.getString(TAG_CANCEL_TEXT);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mContentMsg)
                .setPositiveButton(mEneterText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.enter();
                        }
                    }
                })
                .setNegativeButton(mCancelText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (mListener != null) {
                                    mListener.cancel();
                                }
                            }
                        });
        return builder.create();
    }
}