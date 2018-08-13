package reminder.application.belyaev.dmitry.ru.reminder.model;

public class ModelTask implements Item {

	private String title;
	private long date;

	public ModelTask() {

	}

	public ModelTask(String title, long date) {
		this.title = title;
		this.date = date;
	}

	public boolean isTask() {
		return true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
}