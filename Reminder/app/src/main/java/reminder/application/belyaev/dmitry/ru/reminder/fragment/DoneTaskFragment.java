package reminder.application.belyaev.dmitry.ru.reminder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import reminder.application.belyaev.dmitry.ru.reminder.R;

public class DoneTaskFragment extends Fragment {

	public DoneTaskFragment()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState )
	{
		// Inflate the layout for this fragment
		return inflater.inflate( R.layout.fragment_done_task, container, false );
	}
}