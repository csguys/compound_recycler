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
    private static final int DEFAULT_LOAD_THRESHOLD = 10;

    private CustomRecycler recyclerView;
    private AppCompatImageView ivEmptyImage;
    private AppCompatTextView tvMessage, tvButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View errorLayout;
    private int resImage, resMessage, resButton;
    private OnClickListener onRetryListener;
    private boolean isSwipeEnable;
    private boolean isPagination;
    private int paginationThreshold;


    public ProRecyclerView(final Context context) {
        super(context);
        initView(context);
    }

    public ProRecyclerView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ProRecyclerView(final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
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
        tvMessage.setText(resMessage);
        tvButton.setText(resButton);
        ivEmptyImage.setImageResource(resImage);
        swipeRefreshLayout.setEnabled(isSwipeEnable);
    }

    private void initView(final Context context, final AttributeSet attributeSet) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ProRecyclerView
                , 0, 0);
        resImage = array.getResourceId(R.styleable.ProRecyclerView_pro_empty_image, R.drawable.ic_folder_open_black_24dp);
        resMessage = array.getResourceId(R.styleable.ProRecyclerView_pro_warn_text, R.string.pro_string_empty_data);
        resButton = array.getResourceId(R.styleable.ProRecyclerView_pro_button_text, R.string.pro_string_retry);
        isSwipeEnable = array.getBoolean(R.styleable.ProRecyclerView_pro_swipe, DEFAULT_SWIPE_BEHAVIOUR);
        isPagination = array.getBoolean(R.styleable.ProRecyclerView_pro_paging, DEFAULT_PAGINATION);
        paginationThreshold = array.getInteger(R.styleable.ProRecyclerView_pro_paging_size, DEFAULT_LOAD_THRESHOLD);
        array.recycle();
        initView(context);
    }

    /**
     * Check weather swipe is enabled
     *
     * @return boolean
     */
    public boolean isSwipeEnable() {
        return isSwipeEnable;
    }

    /**
     * Enable swipe to refresh
     *
     * @param enable enable boolean flag
     */
    public void setSwipeEnable(final boolean enable) {
        this.isSwipeEnable = enable;
        swipeRefreshLayout.setEnabled(isSwipeEnable);
    }

    /**
     * Check weather SwipeRefreshing in progress
     *
     * @return refreshing status
     */
    public boolean isRefreshing() {
        return isSwipeEnable && swipeRefreshLayout.isRefreshing();
    }

    /**
     * Setter to set SwipeRefreshing
     *
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
     *
     * @param onRefreshListener OnRefreshListener
     */
    public void setSwipeRefreshLayoutListener(final SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        isSwipeEnable = true;
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    /**
     * SetImage Resource for info view in Recycler in case of empty data, internet issue
     *
     * @param resImage int image resource
     */
    public void setResImage(@DrawableRes final int resImage) {
        this.resImage = resImage;
        ivEmptyImage.setImageResource(resImage);
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
     * @param resMessage int string resource
     */
    public void setResMessage(@StringRes  final int resMessage) {
        this.resMessage = resMessage;
        tvMessage.setText(resMessage);
    }

    /**
     * Set String resource name for Retry button
     * @param resButton int String Resource
     */
    public void setResButton(@StringRes final int resButton) {
        this.resButton = resButton;
        tvButton.setText(resButton);
    }

    /**
     * Use to set onRetry click listener
     * @param onRetryClickListener OnClickListener
     */
    public void setOnRetryListener(final View.OnClickListener onRetryClickListener) {
        this.onRetryListener = onRetryClickListener;
    }

    /**
     * Method to show info when adapter is empty
     * @param enable boolean flag
     */
    private void showEmptyView(final boolean enable) {
        errorLayout.setVisibility(enable ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(enable ? View.GONE : View.VISIBLE);
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
     * Attach ProAdapter to recycler
     * @param adapter adapter
     */
    public void setAdapter(final ProAdapter adapter) {
        recyclerView.setAdapter(adapter);
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
        recyclerView.setLayoutManager(layout);
    }

    /**
     * Getter for recycler view
     * @return recycler
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}
