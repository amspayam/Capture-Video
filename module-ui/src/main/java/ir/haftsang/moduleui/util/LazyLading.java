package ir.haftsang.moduleui.util;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by P.kokabi on 7/13/2016.
 */

public class LazyLading extends RecyclerView.OnScrollListener {

    /*Page Size*/
    public static final Integer FIRST_INDEX = 1;
    private static final Integer PAGE_SIZE = 10;

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.

    private int pageIndex = FIRST_INDEX;

    private LinearLayoutManager mLinearLayoutManager;
    private LazyLadingListener listener;

    public LazyLading(LinearLayoutManager linearLayoutManager, LazyLadingListener listener) {
        mLinearLayoutManager = linearLayoutManager;
        this.listener = listener;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        /*Pagination Conditions*/
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 3;
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            // Do something
            pageIndex++;

            listener.onLoadMore(pageIndex);

            loading = true;
        }

    }

    public void reset() {
        previousTotal = PAGE_SIZE;
        pageIndex = FIRST_INDEX;
        loading = false;
    }

    public interface LazyLadingListener {
        void onLoadMore(int pageIndex);
    }

}