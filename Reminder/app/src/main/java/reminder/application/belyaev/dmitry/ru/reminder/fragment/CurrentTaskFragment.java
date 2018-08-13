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

public class CurrentTaskFragment extends Fragment {

	private RecyclerView rvCurrentTask;
	private RecyclerView.LayoutManager layoutManager;

	private CurrentTasksAdapter adapter;

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
		rvCurrentTask = (RecyclerView) rootView.findViewById( R.id.rvCurrentTasks );
		layoutManager = new LinearLayoutManager( getActivity() );
		rvCurrentTask.setLayoutManager( layoutManager );

		adapter = new CurrentTasksAdapter();
		rvCurrentTask.setAdapter( adapter );
		return rootView;
	}

	public void addTask(ModelTask newTask) {
		int position = -1;
		Log.d("myLogs", adapter.getItemCount() + "");
		for(int i = 0; i < adapter.getItemCount(); i++) {
			if(adapter.getItem(i).isTask()) {
				ModelTask task = (ModelTask) adapter.getItem( i );
				if(newTask.getDate() < task.getDate()) {
					position = i;
					break;
				}
			}
		}

		if(position != -1) {
			adapter.addItem( position, newTask );
		}
		else {
			adapter.addItem( newTask );
		}
	}
}
