package reminder.application.belyaev.dmitry.ru.reminder.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import reminder.application.belyaev.dmitry.ru.reminder.R;
import reminder.application.belyaev.dmitry.ru.reminder.Utils;
import reminder.application.belyaev.dmitry.ru.reminder.fragment.DoneTaskFragment;
import reminder.application.belyaev.dmitry.ru.reminder.fragment.TaskFragment;
import reminder.application.belyaev.dmitry.ru.reminder.model.Item;
import reminder.application.belyaev.dmitry.ru.reminder.model.ModelTask;

public class DoneTasksAdapter extends TaskAdapter {

	public DoneTasksAdapter( DoneTaskFragment taskFragment )
	{
		super( taskFragment );
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
	{
		View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.model_task, parent, false );
		TextView title = (TextView) v.findViewById( R.id.tvTaskTitle );
		TextView date = (TextView) v.findViewById( R.id.tvTaskDate );
		CircleImageView priority = v.findViewById( R.id.cvTaskPriority );
		return new TaskViewHolder( v, title, date, priority );

	}

	@Override public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
	{
		Item item = items.get( position );

		if( item.isTask() ) {
			holder.itemView.setEnabled( true );
			final ModelTask task = (ModelTask) item;
			final TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
			final View itemView = taskViewHolder.itemView;
			final Resources resources = itemView.getResources();
			taskViewHolder.title.setText( task.getTitle() );
			if( task.getDate() != 0 ) {
				taskViewHolder.date.setText( Utils.getFullDate( task.getDate() ) );
			} else {
				taskViewHolder.date.setText( null );
			}

			itemView.setVisibility( View.VISIBLE );
			itemView.setBackgroundColor( resources.getColor( R.color.gray_200 ) );

			taskViewHolder.title.setTextColor( resources.getColor( R.color.primary_text_disabled_material_light ) );
			taskViewHolder.date.setTextColor( resources.getColor( R.color.primary_text_disabled_material_light ) );
			taskViewHolder.priority.setColorFilter( resources.getColor( task.getPriorityColor() ) );
			taskViewHolder.priority.setImageResource( R.drawable.ic_check_circle_white_48dp );

			taskViewHolder.priority.setOnClickListener( new View.OnClickListener() {
				@Override public void onClick( View view )
				{
					task.setStatus( ModelTask.STATUS_CURRENT );

					itemView.setBackgroundColor( resources.getColor( R.color.gray_50 ) );

					taskViewHolder.title.setTextColor( resources.getColor( R.color.primary_text_default_material_light ) );
					taskViewHolder.date.setTextColor( resources.getColor( R.color.primary_text_default_material_light ) );
					taskViewHolder.priority.setColorFilter( resources.getColor( task.getPriorityColor() ) );

					ObjectAnimator flipIn = ObjectAnimator.ofFloat( taskViewHolder.priority, "rotationY", 180f, 0f );
					taskViewHolder.priority.setImageResource( R.drawable.ic_checkbox_blank_circle_white_48dp );
					flipIn.addListener( new Animator.AnimatorListener() {
						@Override public void onAnimationStart( Animator animator )
						{

						}

						@Override public void onAnimationEnd( Animator animator )
						{
							if( task.getStatus() != ModelTask.STATUS_DONE ) {

								ObjectAnimator translationX = ObjectAnimator.ofFloat( itemView,
									"translationX", 0f, -itemView.getWidth() );

								ObjectAnimator translationXBack = ObjectAnimator.ofFloat( itemView,
									"translationX", -itemView.getWidth(), 0f );

								translationX.addListener( new Animator.AnimatorListener() {
									@Override public void onAnimationStart( Animator animator )
									{

									}

									@Override public void onAnimationEnd( Animator animator )
									{
										itemView.setVisibility( View.GONE );
										getTaskFragment().moveTask( task );
										removeItem( taskViewHolder.getLayoutPosition() );
									}

									@Override public void onAnimationCancel( Animator animator )
									{

									}

									@Override public void onAnimationRepeat( Animator animator )
									{

									}
								} );

								AnimatorSet translationSet = new AnimatorSet();
								translationSet.play( translationX ).before( translationXBack );
								translationSet.start();
							}
						}

						@Override public void onAnimationCancel( Animator animator )
						{

						}

						@Override public void onAnimationRepeat( Animator animator )
						{

						}
					} );

					flipIn.start();
				}
			} );
		}
	}
}
