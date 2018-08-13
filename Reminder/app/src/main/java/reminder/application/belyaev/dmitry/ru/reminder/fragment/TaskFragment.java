package reminder.application.belyaev.dmitry.ru.reminder.fragment;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import reminder.application.belyaev.dmitry.ru.reminder.adapter.CurrentTasksAdapter;
import reminder.application.belyaev.dmitry.ru.reminder.model.ModelTask;

public abstract class TaskFragment extends Fragment {
	protected RecyclerView recyclerView;
	protected RecyclerView.LayoutManager layoutManager;

	protected CurrentTasksAdapter adapter;

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
