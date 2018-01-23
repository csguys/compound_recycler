package demo.base.list_library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * ********** Pawan ********
 * **********(9/11/17)*******
 */

public class ProRecyclerView extends FrameLayout implements View.OnClickListener, CustomRecycler.RecyclerOnEmptyListener {

    private static final String TAG = ProRecyclerView.class.getSimpleName();
    private static final boolean DEFAULT_SWIPE_BEHAVIOUR = false;
    private static final boolean DEFAULT_PAGINATION = false;
    private static final int DEFAULT_LOAD_THRESHOLD = 0;

    private static int RES_EMPTY_VIEW = R.drawable.ic_folder_open_black_24dp;

    private CustomRecycler recyclerView;
    private AppCompatImageView ivEmptyImage;
    private AppCompatTextView tvMessage, tvButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View errorLayout;
    private int resInfoImage;
    private CharSequence textMessage;
    private CharSequence textButton;
    private OnClickListener onRetryListener;
    private boolean isSwipeEnable;
    private int paginationThreshold;
    private RecyclerView.LayoutManager proLayoutManager;
    private PaginationListener paginationListener;


    public ProRecyclerView(final Context context) {
        this(context, null);
    }

    public ProRecyclerView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProRecyclerView(final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private void initView(final Context context, final AttributeSet attributeSet) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ProRecyclerView
                , 0, 0);
        resInfoImage = array.getResourceId(R.styleable.ProRecyclerView_pro_empty_image, R.drawable.ic_folder_open_black_24dp);
        textMessage = array.getText(R.styleable.ProRecyclerView_pro_info_text);
        textButton = array.getText(R.styleable.ProRecyclerView_pro_button_text);
        isSwipeEnable = array.getBoolean(R.styleable.ProRecyclerView_pro_swipe, DEFAULT_SWIPE_BEHAVIOUR);
        paginationThreshold = array.getInteger(R.styleable.ProRecyclerView_pro_paging_size, DEFAULT_LOAD_THRESHOLD);
        array.recycle();
        initView(context);
    }

    private void initView(final Context context) {
        View view = inflate(context, R.layout.pro_recycler, this);
        recyclerView = view.findViewById(R.id.pro_x_recycler_view);
        ivEmptyImage = view.findViewById(R.id.pro_x_error_image);
        tvMessage = view.findViewById(R.id.pro_x_error_text);
        tvButton = view.findViewById(R.id.pro_x_error_button);
        swipeRefreshLayout = view.findViewById(R.id.pro_x_swipe_to_refresh);
        tvButton.setOnClickListener(this);
        recyclerView.setEmptyListener(this);
        errorLayout = view.findViewById(R.id.pro_x_error_layout);
        tvMessage.setText(textMessage);
        tvButton.setText(textButton);
        ivEmptyImage.setImageResource(resInfoImage);
        swipeRefreshLayout.setEnabled(isSwipeEnable);
    }

    /**
     * Check weather swipe is enabled
     * @return boolean
     */
    public boolean isSwipeEnable() {
        return isSwipeEnable;
    }

    /**
     * Enable SwipeRefreshLayout
     * @param enable boolean
     */
    public void setSwipeEnable(final boolean enable) {
        this.isSwipeEnable = enable;
        swipeRefreshLayout.setEnabled(isSwipeEnable);
    }

    /**
     * Check weather SwipeRefreshing in progress
     * @return refreshing status
     */
    public boolean isRefreshing() {
        return isSwipeEnable && swipeRefreshLayout.isRefreshing();
    }

    /**
     * change status of SwipeRefreshLayout
     * false disable
     * true enable
     * @param refreshing boolean
     */
    public void setRefreshing(final boolean refreshing) {
        if (!isSwipeEnable) {
            return;
        }
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    /**
     * Method to attach listener for SwipeRefreshing view
     * @param onRefreshListener OnRefreshListener
     */
    public void setSwipeRefreshLayoutListener(final SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        isSwipeEnable = true;
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    /**
     * SetImage Resource for info view in Recycler in case of empty data, internet issue
     * @param resInfoImage int image resource
     */
    public void setResInfoImage(@DrawableRes final int resInfoImage) {
        this.resInfoImage = resInfoImage;
        ivEmptyImage.setImageResource(resInfoImage);
    }

    /**
     * Show info message in case of error
     */
    public void showMessage() {
        errorLayout.setVisibility(VISIBLE);
        recyclerView.setVisibility(GONE);
    }

    /**
     * Set String resource message for info
     *
     * @param textMessage int string resource
     */
    public void setTextMessage(@StringRes  final int textMessage) {
        this.textMessage = getContext().getString(textMessage);
        tvMessage.setText(textMessage);
    }

    /**
     * Set String resource name for Retry button
     * @param textButton int String Resource
     */
    public void setTextButton(@StringRes final int textButton) {
        this.textButton = getContext().getString(textButton);
        tvButton.setText(textButton);
    }

    /**
     * set CharSequence message for text info
     * @param textMessage int string resource
     */
    public void setTextMessage(final CharSequence textMessage) {
        this.textMessage = textMessage;
        this.tvMessage.setText(textMessage);
    }

    /**
     * set CharSequence name for Retry button
     * @param textButton int String Resource
     */
    public void setTextButton(final CharSequence textButton) {
        this.textButton = textButton;
        tvButton.setText(textButton);
    }

    /**
     * method to set click listener on view below info/error image
     * @param onRetryClickListener View.OnClickListener object
     */
    public void setOnRetryListener(final OnClickListener onRetryClickListener) {
        this.onRetryListener = onRetryClickListener;
    }

    /**
     * get pagination threshold
     * @return threshold limit
     */
    public int getPaginationThreshold() {
        return paginationThreshold;
    }

    /**
     * set pagination threshold
     * @param paginationThreshold threshold limit
     */
    public void setPaginationThreshold(final int paginationThreshold) {
        this.paginationThreshold = paginationThreshold;
    }


    /**
     * Method to show info when adapter is empty
     * @param enable boolean flag
     */
    private void showEmptyView(final boolean enable) {
        ivEmptyImage.setImageResource(RES_EMPTY_VIEW);
        errorLayout.setVisibility(enable ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(enable ? View.GONE : View.VISIBLE);
        Log.i(TAG, "showEmptyCalled");
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.pro_x_error_button) {
            if (onRetryListener != null) {
                onRetryListener.onClick(v);
            }
        }
    }

    @Override
    public void onEmptyAdapter(final boolean enable) {
        showEmptyView(enable);
    }

    /**
     * Attach RecyclerView.Adapter to recycler
     * @param adapter adapter
     */
    public void setAdapter(final RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    /**
     * attach onScrollListener for recycler view
     * @param listener OnScrollListener
     */
    public void addOnScrollListener(final RecyclerView.OnScrollListener listener) {
        recyclerView.addOnScrollListener(listener);
    }

    /**
     * attach RecyclerView.LayoutManager
     * @param layout LayoutManager
     */
    public void setLayoutManager(final RecyclerView.LayoutManager layout) {
        proLayoutManager = layout;
        recyclerView.setLayoutManager(layout);
    }

    /**
     * Getter for recycler view
     * @return recycler
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * Method to attach pagination listener which get called every time when scrolling reached to given value {@link ProRecyclerView#paginationThreshold}
     * @param pagingListener {@link PaginationListener} object
     */
    public void setPagingListener(final PaginationListener pagingListener) {
        //if threshold is 0 don't attach listener
        if (paginationThreshold <= 0) {
            return;
        }
        this.paginationListener = pagingListener;
        recyclerView.addOnScrollListener(new PagingManager(proLayoutManager, paginationThreshold) {
            @Override
            public void onEndReached() {
                if (paginationListener != null) {
                    paginationListener.onEndReaching();
                }
            }
        });
    }

    /**
     * Listener interface for paging callback
     */
    public interface PaginationListener{
        void onEndReaching();
    }

}
