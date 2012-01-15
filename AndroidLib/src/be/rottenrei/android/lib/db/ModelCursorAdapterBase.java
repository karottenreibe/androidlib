package be.rottenrei.android.lib.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import be.rottenrei.android.lib.util.ExceptionUtils;

/**
 * Base class for cursor adapters that use a {@link TableBase}.
 */
abstract public class ModelCursorAdapterBase<ModelType extends IModelType> extends CursorAdapter {

	private final TableBase<ModelType> table;
	private final int layoutId;

	public ModelCursorAdapterBase(Context context, TableBase<ModelType> table, int layoutId) {
		super(context, table.getCursor(null, null, null));
		this.table = table;
		this.layoutId = layoutId;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		try {
			ModelType object = table.getSerializer().deSerialize(cursor);
			fillView(view, object);
		} catch (DatabaseException e) {
			ExceptionUtils.handleExceptionWithMessage(e, context, 0, null);
		}
	}

	abstract protected void fillView(View view, ModelType object);

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
		try {
			ModelType object = table.getSerializer().deSerialize(cursor);
			fillView(view, object);
		} catch (DatabaseException e) {
			ExceptionUtils.handleExceptionWithMessage(e, context, 0, null);
		}
		return view;
	}

}