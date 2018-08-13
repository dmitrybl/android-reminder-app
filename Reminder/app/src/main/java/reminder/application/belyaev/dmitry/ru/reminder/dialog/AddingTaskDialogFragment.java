package reminder.application.belyaev.dmitry.ru.reminder.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import reminder.application.belyaev.dmitry.ru.reminder.R;
import reminder.application.belyaev.dmitry.ru.reminder.Utils;

public class AddingTaskDialogFragment extends DialogFragment {

	private AddingTaskListener addingTaskListener;
	private static EditText etDate;
	private static EditText etTime;

	public interface AddingTaskListener {
		void onTaskAdded();
		void onTaskAddingCancel();
	}

	@Override public void onAttach( Activity activity )
	{
		super.onAttach( activity );
		try {
			addingTaskListener = (AddingTaskListener) activity;
		}
		catch(ClassCastException e) {
			throw new ClassCastException( activity.toString() + " must implement AddingTaskListener" );
		}
	}

	@Override public Dialog onCreateDialog( Bundle savedInstanceState ) {
		final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

		builder.setTitle( R.string.dialog_title );

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View container = inflater.inflate( R.layout.dialog_task, null );

		final TextInputLayout tilTitle = (TextInputLayout) container.findViewById( R.id.tilDialogTaskTitle );
		final EditText etTitle = tilTitle.getEditText();

		TextInputLayout tilDate = (TextInputLayout) container.findViewById( R.id.tilDialogTaskDate );
		etDate = tilDate.getEditText();

		TextInputLayout tilTime = (TextInputLayout) container.findViewById( R.id.tillDialogTaskTime );
		etTime = tilTime.getEditText();

		tilTitle.setHint( getResources().getString( R.string.task_title ) );
		tilDate.setHint( getResources().getString( R.string.task_date ) );
		tilTime.setHint(getResources().getString( R.string.task_time ));

		builder.setView( container );

		etDate.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				if(etDate.length() == 0) {
					etDate.setText("");
				}

				DialogFragment datePickerFragment = new DatePickerFragment();
				datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
			}
		} );

		etTime.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				if(etTime.length() == 0) {
					etTime.setText("");
				}

				DialogFragment timePickerFragment = new TimePickerFragment();
				timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
			}
		} );

		builder.setPositiveButton( R.string.dialog_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int i ) {
				addingTaskListener.onTaskAdded();
				dialog.dismiss();
			}
		});

		builder.setNegativeButton( R.string.dialog_cancel, new DialogInterface.OnClickListener() {
			@Override public void onClick( DialogInterface dialog, int i ) {
				addingTaskListener.onTaskAddingCancel();
				dialog.cancel();
			}
		} );

		AlertDialog alertDialog = builder.create();
		alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
			@Override public void onShow( DialogInterface dialog ) {
				final Button positiveButton = ((AlertDialog) dialog).getButton( DialogInterface.BUTTON_POSITIVE );
				if(etTitle.length() == 0) {
					positiveButton.setEnabled( false );
					tilTitle.setError( getResources().getString( R.string.dialog_error_empty_title ) );
				}

				etTitle.addTextChangedListener( new TextWatcher() {
					@Override public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 )
					{

					}

					@Override public void onTextChanged( CharSequence s, int i, int i1, int i2 )
					{
						if(s.length() == 0) {
							positiveButton.setEnabled( false );
							tilTitle.setError( getResources().getString( R.string.dialog_error_empty_title ) );
						}
						else {
							positiveButton.setEnabled( true );
							tilTitle.setErrorEnabled( false );
						}
					}

					@Override public void afterTextChanged( Editable editable )
					{

					}
				} );
			}
		} );

		return alertDialog;
	}

	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override public Dialog onCreateDialog( Bundle savedInstanceState ) {

			Calendar c = Calendar.getInstance();
			int year = c.get( Calendar.YEAR );
			int month = c.get( Calendar.MONTH );
			int day = c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog( getActivity(), this, year, month, day );
		}

		@Override
		public void onDateSet( DatePicker datePicker, int year, int monthOfYear, int dayOfMonth ) {
			Calendar dateCalendar = Calendar.getInstance();
			dateCalendar.set(year, monthOfYear, dayOfMonth);
			etDate.setText( Utils.getDate( dateCalendar.getTimeInMillis() ));
		}
	}

	public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

		@Override public Dialog onCreateDialog( Bundle savedInstanceState ) {

			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get( Calendar.MINUTE );

			return new TimePickerDialog( getActivity(), this, hour, minute, DateFormat.is24HourFormat( getActivity() ));
		}

		@Override public void onTimeSet( TimePicker timePicker, int hourOfDay, int minute ) {
			Calendar timeCalendar = Calendar.getInstance();
			timeCalendar.set(0, 0, 0, hourOfDay, minute);
			etTime.setText( Utils.getTime( timeCalendar.getTimeInMillis() ));
		}
	}
}
