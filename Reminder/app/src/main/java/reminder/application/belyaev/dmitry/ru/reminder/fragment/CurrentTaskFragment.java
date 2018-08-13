package reminder.application.belyaev.dmitry.ru.reminder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import reminder.application.belyaev.dmitry.ru.reminder.R;
import reminder.application.belyaev.dmitry.ru.reminder.adapter.CurrentTasksAdapter;
import reminder.application.belyaev.dmitry.ru.reminder.model.ModelTask;

public class CurrentTaskFragment extends TaskFragment {

	public CurrentTaskFragment()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState )
	{

		// Inflate the layout for this fragment
		View rootView = inflater.inflate( R.layout.fragment_current_task, container, false );
		recyclerView = (RecyclerView) rootView.findViewById( R.id.rvCurrentTasks );
		layoutManager = new LinearLayoutManager( getActivity() );
		recyclerView.setLayoutManager( layoutManager );

		adapter = new CurrentTasksAdapter(this);
		recyclerView.setAdapter( adapter );
		return rootView;
	}

}
