package demo.base.list_library;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;


/**
 * Created by alex on 8/8/17.
 */

public class CustomRecycler extends RecyclerView {

    private static final String TAG = CustomRecycler.class.getSimpleName();

    private RecyclerOnEmptyListener emptyListener;

    private AdapterDataObserver observer = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            observeAdapter();
        }

        @Override
        public void onItemRangeChanged(final int positionStart, final int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            observeAdapter();
        }

        @Override
        public void onItemRangeInserted(final int positionStart, final int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            observeAdapter();
        }

        @Override
        public void onItemRangeMoved(final int fromPosition, final int toPosition, final int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            observeAdapter();
        }

        @Override
        public void onItemRangeRemoved(final int positionStart, final int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            observeAdapter();
        }
    };

    /**
     * function to check adapter status
     */
    private void observeAdapter() {
        Adapter<?> adapter = getAdapter();
        if (adapter != null && emptyListener != null) {
            Log.i(TAG, "adapter set :" + adapter.getItemCount());
            emptyListener.onEmptyAdapter(adapter.getItemCount() == 0);
        }
    }


    /**
     * @param context context
     */
    public CustomRecycler(final Context context) {
        super(context);
    }

    /**
     * @param context context
     * @param attrs   attrsset
     */
    public CustomRecycler(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context  context
     * @param attrs    attrs
     * @param defStyle def
     */
    public CustomRecycler(final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Set listener for empty adapter
     * @param emptyListener RecyclerOnEmptyListener
     */
    public void setEmptyListener(final RecyclerOnEmptyListener emptyListener) {
        this.emptyListener = emptyListener;
    }

    @Override
    public void setAdapter(final Adapter adapter) {
        final Adapter<?> oldAdapter = getAdapter();
        super.setAdapter(adapter);
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        observer.onChanged();
    }



    public interface RecyclerOnEmptyListener{
        void onEmptyAdapter(final boolean enable);
    }

}
