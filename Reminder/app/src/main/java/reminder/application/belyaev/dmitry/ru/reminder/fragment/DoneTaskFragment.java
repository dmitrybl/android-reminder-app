package reminder.application.belyaev.dmitry.ru.reminder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import reminder.application.belyaev.dmitry.ru.reminder.R;

public class DoneTaskFragment extends TaskFragment {

	RecyclerView recyclerView;
	RecyclerView.LayoutManager layoutManager;

	public DoneTaskFragment()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState )
	{
		// Inflate the layout for this fragment
		View rootView = inflater.inflate( R.layout.fragment_done_task, container, false );
		recyclerView = (RecyclerView) rootView.findViewById( R.id.rvDoneTasks );
		layoutManager = new LinearLayoutManager( getActivity() );
		recyclerView.setLayoutManager( layoutManager );
		return rootView;
	}
}
