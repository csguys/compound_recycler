package demo.base.list_library;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by Pawan Bhardwaj on 23/01/18.
 */

public abstract class PagingManager extends RecyclerView.OnScrollListener {

    private RecyclerView.LayoutManager layoutManager;
    private int threshHold;

    public PagingManager(final RecyclerView.LayoutManager layoutManager, final int threshHold) {
        this.layoutManager = layoutManager;
        this.threshHold = threshHold;
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        int firstVisibleItem = getFirstVisibleItem();
        int visibleCount = layoutManager.getChildCount();
        int totalCount = layoutManager.getItemCount();
        if ((firstVisibleItem + visibleCount) >= totalCount
                && firstVisibleItem >0
                && totalCount > threshHold) {
            onEndReached();
        }

    }

    /**
     * Get first visible item index
     * @return index
     */
    private int getFirstVisibleItem() {
        int temp =0;

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] position = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
            for (int i = 0; i < position.length; i++) {
                if (i ==0) {
                    temp = position[i];
                }
                if (position[i] > temp) {
                    temp = position[i];
                }
            }
        }
        else if (layoutManager instanceof GridLayoutManager) {
            temp = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        else if (layoutManager instanceof LinearLayoutManager){
            temp = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        return temp;
    }

    /**
     * method called when scrolled below threshold
     */
    public abstract void onEndReached();
}
