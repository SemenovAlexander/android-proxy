package com.lechucksoftware.proxy.proxysettings.ui.dialogs.tour;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.lechucksoftware.proxy.proxysettings.App;
import com.lechucksoftware.proxy.proxysettings.R;
import com.lechucksoftware.proxy.proxysettings.ui.dialogs.rating.MailFeedbackDialog;
import com.lechucksoftware.proxy.proxysettings.ui.dialogs.rating.RateAppDialog;
import com.lechucksoftware.proxy.proxysettings.ui.base.BaseDialogFragment;
import com.lechucksoftware.proxy.proxysettings.utils.startup.StartupAction;

public class AppTourDialog extends BaseDialogFragment
{
    public static String TAG = AppTourDialog.class.getSimpleName();
    private StartupAction startupAction;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        startupAction = (StartupAction) b.getSerializable("ACTION");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getTheme());

//        builder.setTitle(R.string.app_rater_dialog_title);
        builder.setMessage(R.string.do_you_like);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {

                RateAppDialog rateDialog = RateAppDialog.newInstance(startupAction);
                rateDialog.show(getFragmentManager(), "RateAppDialog");

                App.getEventsReporter().sendEvent(R.string.analytics_cat_user_action,
                        R.string.analytics_act_dialog_button_click,
                        R.string.analytics_lab_like_app_dialog, 1L);
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {

                MailFeedbackDialog feedbackDialog = MailFeedbackDialog.newInstance(startupAction);
                feedbackDialog.show(getFragmentManager(), "MailFeedbackDialog");

                App.getEventsReporter().sendEvent(R.string.analytics_cat_user_action,
                        R.string.analytics_act_dialog_button_click,
                        R.string.analytics_lab_like_app_dialog, 0L);
            }
        });

        AlertDialog alert = builder.create();
        return alert;
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);

        App.getEventsReporter().sendEvent(R.string.analytics_cat_user_action,
                R.string.analytics_act_dialog_button_click,
                R.string.analytics_lab_like_app_dialog, 0L);
    }

    public static AppTourDialog newInstance(StartupAction action)
    {
        AppTourDialog frag = new AppTourDialog();

        Bundle b = new Bundle();
        b.putSerializable("ACTION", action);
        frag.setArguments(b);

        return frag;
    }
}
