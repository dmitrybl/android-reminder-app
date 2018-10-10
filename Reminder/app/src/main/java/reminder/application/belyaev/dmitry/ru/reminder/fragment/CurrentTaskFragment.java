package reminder.application.belyaev.dmitry.ru.reminder.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import reminder.application.belyaev.dmitry.ru.reminder.R;
import reminder.application.belyaev.dmitry.ru.reminder.adapter.CurrentTasksAdapter;
import reminder.application.belyaev.dmitry.ru.reminder.database.DBHelper;
import reminder.application.belyaev.dmitry.ru.reminder.model.ModelTask;

public class CurrentTaskFragment extends TaskFragment {

	public CurrentTaskFragment()
	{
		// Required empty public constructor
	}

	public interface OnTaskDoneListener {
		void onTaskDone(ModelTask task);
	}

	OnTaskDoneListener onTaskDoneListener;

	@Override
	public void onAttach( Activity activity )
	{
		super.onAttach( activity );
		try {
			onTaskDoneListener = (OnTaskDoneListener) activity;
		}
		catch(ClassCastException e) {
			throw new ClassCastException( activity.toString() + " must implement OnTaskDoneListener" );
		}
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

	@Override
	public void findTasks(String title) {
		adapter.removeAllItems();
		List<ModelTask> tasks = new ArrayList<>(  );
		tasks.addAll( activity.dbHelper.query().getTasks( DBHelper.SELECTION_LIKE_TITLE + " AND " +
				DBHelper.SELECTION_STATUS + " OR " +
				DBHelper.SELECTION_STATUS, new String[]{"%" + title + "%", Integer.toString( ModelTask.STATUS_CURRENT ),
				Integer.toString( ModelTask.STATUS_OVERDUE )}, DBHelper.TASK_DATE_COLUMN));
		for(int i = 0; i < tasks.size(); i++) {
			addTask( tasks.get( i ), false );
		}
	}

	@Override public void addTaskFromDB()
	{
		adapter.removeAllItems();
		List<ModelTask> tasks = new ArrayList<>(  );
		tasks.addAll( activity.dbHelper.query().getTasks( DBHelper.SELECTION_STATUS + " OR " +
		DBHelper.SELECTION_STATUS, new String[]{Integer.toString( ModelTask.STATUS_CURRENT ),
		Integer.toString( ModelTask.STATUS_OVERDUE )}, DBHelper.TASK_DATE_COLUMN));
		for(int i = 0; i < tasks.size(); i++) {
			addTask( tasks.get( i ), false );
		}
	}

	@Override public void moveTask( ModelTask task )
	{
		onTaskDoneListener.onTaskDone( task );
	}
}
