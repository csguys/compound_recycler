package demo.base.list_library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * ********** Pawan ********
 * **********(20/12/17)*******
 */

public  abstract class ProAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY = 989;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == TYPE_EMPTY) {
            ProgressBar progressBar = new ProgressBar(parent.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            progressBar.setLayoutParams(params);
            return new EmptyHolder(progressBar);
        }
        return onCreateViewHolderPro(parent, viewType);
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolderPro(final ViewGroup parent, final int viewType);

    /**
     * ViewHolder for empty view
     */
    private class EmptyHolder extends RecyclerView.ViewHolder{

        public EmptyHolder(final View itemView) {
            super(itemView);
        }
    }

}
