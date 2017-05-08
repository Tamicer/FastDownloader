package com.tamic.fastdownsimple.widget;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.tamic.rx.fastdown.widget.DownItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;


/**
 * Created by liuyongkui726 on 2016-12-21.
 */
public abstract class AbstractAdapter<T extends DownItemType, VH extends AbstractViewHolder> extends
        RecyclerView.Adapter<VH> {
    private DataSetObservable<T> dataSet;
    private RecyclerView mRecyclerView;

    public AbstractAdapter() {
        dataSet = new DataSetObservable<>();
    }

    public void clear() {
        dataSet.clear();
    }

    public void clearData() {
        dataSet.data.clear();
    }

    public void clearHeader() {
        dataSet.header.clear();
    }

    public void clearFooter() {
        dataSet.footer.clear();
    }

    public void addHeader(SectionItem header) {
        dataSet.header.add(header);
    }

    public void addFooter(SectionItem footer) {
        dataSet.footer.add(footer);
    }

    public T get(int position) {
        return dataSet.data.get(position);
    }

    public List<T> getData() {
        return dataSet.data.getAll();
    }

    /**
     * 添加数据, 并触发刷新.
     * 添加之后数据总数为0, 显示EmptyView;
     * 添加之后数据总数大于0, 当添加0个数据时,自动停止LoadMore;
     *
     * @param data list of data
     */
    public void addAll(List<? extends T> data) {
        dataSet.data.addAll(data);
        notifyDataSetChanged();

        if (dataSet.totalSize() == 0) {
            dataSet.notifyEmpty();
        } else {
            dataSet.notifyContent();
            if (data.size() == 0) {
                dataSet.notifyNoMore();
            }
        }
    }

    /**
     * 正常插入数据
     *
     * @param adapterPosition 插入的位置
     * @param item            插入的数据
     */
    public void insert(int adapterPosition, T item) {
        dataSet.data.insert(adapterPosition, item);
        notifyItemInserted(adapterPosition);
    }

    public void insertAll(int adapterPosition, List<? extends T> items) {
        dataSet.data.insertAll(adapterPosition, items);
        notifyItemRangeInserted(adapterPosition, items.size());
    }

    /**
     * 插入数据到position之后
     *
     * @param adapterPosition position
     * @param item            插入的数据
     */
    public void insertBack(int adapterPosition, T item) {
        dataSet.data.insertBack(adapterPosition, item);
        notifyItemInserted(adapterPosition + 1);
    }

    public void insertAllBack(int adapterPosition, List<? extends T> items) {
        dataSet.data.insertAllBack(adapterPosition, items);
        notifyItemRangeInserted(adapterPosition + 1, items.size());
    }

    public void swap(int fromAdapterPosition, int toAdapterPosition) {
        dataSet.data.swap(fromAdapterPosition, toAdapterPosition);
        notifyItemMoved(fromAdapterPosition, toAdapterPosition);
    }

    /**
     * 正常的删除
     *
     * @param adapterPosition 待删除的位置
     */
    public void remove(int adapterPosition) {
        dataSet.data.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    /**
     * 从指定的position之后删除size个数据
     *
     * @param adapterPosition position
     * @param removeSize      删除的数据大小
     */
    public void removeBack(int adapterPosition, int removeSize) {
        dataSet.data.removeAllBack(adapterPosition, removeSize);
        notifyItemRangeRemoved(adapterPosition + 1, removeSize);
    }

    /**
     * 显示Loading View
     */
    public void showLoading() {
        dataSet.notifyLoading();
    }

    /**
     * 清除当前所有数据,并显示ErrorView
     */
    public void showError() {
        dataSet.clear();
        notifyDataSetChanged();
        dataSet.notifyError();
    }

    /**
     * 显示底部LoadMoreErrorView
     */
    public void loadMoreFailed() {
        dataSet.notifyLoadMoreFailed();
    }

    /**
     * 恢复LoadMore
     */
    public void resumeLoadMore() {
        dataSet.notifyResumeLoadMore();
    }

    /**
     * 手动触发加载更多, 显示LoadMoreView ,并执行loadMore逻辑
     */
    public void manualLoadMore() {
        dataSet.notifyManualLoadMore();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH viewHolder = createHeaderFooterViewHolder(parent, viewType);
        if (viewHolder != null) return viewHolder;
        return onNewCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (dataSet.header.is(position)) {
            dataSet.header.get(position).onBind();
        } else if (dataSet.data.is(position)) {
            onNewBindViewHolder(holder, position);
        } else if (dataSet.footer.is(position)) {
            dataSet.footer.get(position).onBind();
        } else {
            dataSet.extra.get(position).onBind();
        }
    }

    @Override
    public final int getItemViewType(int position) {
        //用header和footer的HashCode表示它们的ItemType,
        //普通类型的数据由该数据类型的ItemType决定

        if (dataSet.header.is(position)) {
            return dataSet.header.get(position).hashCode();
        }  else if (dataSet.footer.is(position)) {
            return dataSet.footer.get(position).hashCode();
        } else if (dataSet.data.is(position)){
            return dataSet.data.get(position).itemType();
        }
        else {
            return dataSet.extra.get(position).hashCode();
        }
    }

    @Override
    public final int getItemCount() {
        return dataSet.totalSize();
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getAdapterPosition();

        //瀑布流的 Header Footer 宽度处理
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (!dataSet.data.is(position)) {
                p.setFullSpan(true);
            }
        }

        //判断是否需要自动加载
        if (mRecyclerView.getScrollState() != SCROLL_STATE_IDLE) return;
        if (dataSet.extra.size() == 0) {
            if (position == dataSet.totalSize() - 1) {
                loadMore();
            }
        } else {
            if (position == dataSet.totalSize() - 1 - dataSet.extra.size()) {
                loadMore();
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;

        //Grid的 Header Footer 宽度处理
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isData = dataSet.data.is(position);
                    if (isData) {
                        return 1;
                    } else {
                        return gridManager.getSpanCount();
                    }
                }
            });
        }
    }

    void resolveSwipeConflicts(boolean enabled) {
        dataSet.notifyResolveSwipeConflicts(enabled);
    }

    boolean canDrag(int adapterPosition) {
        return dataSet.data.is(adapterPosition);
    }

    void show(View view, boolean enabled) {
        if (dataSet.extra.size() == 0) {
            if (enabled) {
                dataSet.extra.add(new SectionItemImpl(view));
                notifyItemInserted(dataSet.extra.position());
            }
        } else {
            if (!dataSet.extra.get(dataSet.extra.position()).createView(null).equals(view)) {
                if (enabled) {
                    dataSet.extra.set(dataSet.extra.position(), new SectionItemImpl(view));
                    notifyItemChanged(dataSet.extra.position());
                }
            } else {
                if (!enabled) {
                    int position = dataSet.extra.position();
                    dataSet.extra.remove(position);
                    notifyItemRemoved(position);
                }
            }
        }
    }

    void registerObserver(Observer observer) {
        dataSet.addObserver(observer);
    }

    protected abstract VH onNewCreateViewHolder(ViewGroup parent, int viewType);

    protected abstract void onNewBindViewHolder(VH holder, int position);

    private void loadMore() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                dataSet.notifyAutoLoadMore();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private VH createHeaderFooterViewHolder(ViewGroup parent, int viewType) {
        List<SectionItem> tempContainer = new ArrayList<>();
        tempContainer.addAll(dataSet.header.getAll());
        tempContainer.addAll(dataSet.footer.getAll());
        tempContainer.addAll(dataSet.extra.getAll());

        for (SectionItem each : tempContainer) {
            if (each.hashCode() == viewType) {
                View view = each.createView(parent);
                return (VH) new SectionItemViewHolder(view);
            }
        }
        return null;
    }

    private class SectionItemViewHolder extends AbstractViewHolder {

        SectionItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(DownItemType data) {

        }
    }
}

