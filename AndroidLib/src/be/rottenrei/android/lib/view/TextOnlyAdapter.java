package be.rottenrei.android.lib.view;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Simple adapter that converts every object into a simple string set on a TextView.
 */
public abstract class TextOnlyAdapter<ObjectType> extends ArrayAdapter<ObjectType> {

	public TextOnlyAdapter(Context context, List<ObjectType> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if (convertView != null && convertView instanceof TextView) {
			view = (TextView) convertView;
		} else {
			view = new TextView(getContext());
			view.setTextSize(20);
			view.setPadding(20, 20, 20, 20);
		}
		view.setText(getText(getItem(position)));
		attachViewListeners(position, view);
		return view;
	}

	@SuppressWarnings("unused")
	protected void attachViewListeners(int position, TextView view) {
		// empty default implementation
	}

	abstract protected CharSequence getText(ObjectType item);

}
