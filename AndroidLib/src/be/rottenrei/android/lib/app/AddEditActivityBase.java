package be.rottenrei.android.lib.app;

import java.sql.SQLException;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import be.rottenrei.android.lib.R;
import be.rottenrei.android.lib.util.ExceptionUtils;
import be.rottenrei.android.lib.util.UIUtils;

/**
 * Base class for activities that edit and/or add model elements.
 */
public abstract class AddEditActivityBase<ModelType> extends Activity {

	public static final String DELETED_EXTRA = AddEditActivityBase.class.getName() + ".deleted";

	protected abstract ModelType getModel();

	protected abstract void setModel(ModelType model);

	protected abstract boolean validate(ModelType model);

	protected abstract void persist(ModelType model) throws SQLException;

	protected abstract String getExtra();

	protected abstract Parcelable parcel(ModelType model);

	protected abstract ModelType unparcel(Parcelable parcelable);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		Intent intent = getIntent();
		ModelType model = null;
		Parcelable extra = null;
		if (savedInstanceState != null) {
			extra = savedInstanceState.getParcelable(getExtra());
		} else if (intent != null) {
			extra = intent.getParcelableExtra(getExtra());
		}
		if (extra != null) {
			model = unparcel(extra);
		}
		if (model != null) {
			setModel(model);
		}
	}

	/** Must set the content view. */
	protected abstract void setupView();

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(getExtra(), parcel(getModel()));
	}

	public void onCancelClicked(@SuppressWarnings("unused") View view) {
		setResult(RESULT_CANCELED);
		finish();
	}

	public void onSaveClicked(@SuppressWarnings("unused") View view) {
		ModelType model = getModel();
		if (!validate(model)) {
			return;
		}
		try {
			persist(model);
		} catch (SQLException e) {
			ExceptionUtils.handleExceptionWithMessage(e, this, R.string.no_database, AddEditActivityBase.class);
		}
		UIUtils.informUser(this, R.string.success_edit);
		Intent intent = new Intent();
		intent.putExtra(getExtra(), parcel(model));
		setResult(RESULT_OK, intent);
		finish();
	}

	public void onDeleteClicked(@SuppressWarnings("unused") View view) {
		UIUtils.confirm(this, R.string.confirm_delete, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				performDelete();
			}
		});
	}

	private void performDelete() {
		ModelType model = getModel();
		try {
			delete(model);
		} catch (SQLException e) {
			ExceptionUtils.handleExceptionWithMessage(e, this, R.string.no_database, AddEditActivityBase.class);
		}
		UIUtils.informUser(this, R.string.success_delete);
		Intent intent = new Intent();
		intent.putExtra(DELETED_EXTRA, true);
		setResult(RESULT_OK, intent);
		finish();
	}

	abstract protected void delete(ModelType model) throws SQLException;
}
