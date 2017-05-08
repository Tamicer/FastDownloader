package com.tamic.fastdownsimple.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by liuyongkui726 on 2016-12-21.
 */

class DataSetObservable<E> extends Observable {
    Segment<SectionItem> header = new HeaderSegment();
    Segment<E> data = new DataSegment();
    Segment<SectionItem> footer = new FooterSegment();
    Segment<SectionItem> extra = new ExtraSegment();

    private List<SectionItem> mHeader = new ArrayList<>();
    private List<E> mData = new ArrayList<>();
    private List<SectionItem> mFooter = new ArrayList<>();
    private List<SectionItem> mExtra = new ArrayList<>();


    //------------------------------
    //|position    size     item   |
    //|----------------------------|
    //|    0               header0 |
    //|    1        2      header1 |
    //|----------------------------|
    //|    2               data0   |
    //|    3               data1   |
    //|    4               data2   |
    //|    5        4      data3   |
    //|----------------------------|
    //|    6               footer0 |
    //|    7        2      footer1 |
    //|----------------------------|
    //|    8               extra0  |
    //|    9        2      extra1  |
    //------------------------------

    int totalSize() {
        return mHeader.size() + mData.size() + mFooter.size() + mExtra.size();
    }

    void clear() {
        mHeader.clear();
        mData.clear();
        mFooter.clear();
        mExtra.clear();
    }

    void notifyLoading() {
        super.setChanged();
        super.notifyObservers(new Bridge.Loading());
    }

    void notifyContent() {
        super.setChanged();
        super.notifyObservers(new Bridge.Content());
    }

    void notifyError() {
        super.setChanged();
        super.notifyObservers(new Bridge.Error());
    }

    void notifyEmpty() {
        super.setChanged();
        super.notifyObservers(new Bridge.Empty());
    }

    void notifyLoadMoreFailed() {
        super.setChanged();
        super.notifyObservers(new Bridge.LoadMoreFailed());
    }

    void notifyResumeLoadMore() {
        super.setChanged();
        super.notifyObservers(new Bridge.ResumeLoadMore());
    }

    void notifyNoMore() {
        super.setChanged();
        super.notifyObservers(new Bridge.NoMore());
    }

    void notifyAutoLoadMore() {
        super.setChanged();
        super.notifyObservers(new Bridge.AutoLoadMore());
    }

    void notifyManualLoadMore() {
        super.setChanged();
        super.notifyObservers(new Bridge.ManualLoadMore());
    }

    void notifyResolveSwipeConflicts(boolean enabled) {
        super.setChanged();
        super.notifyObservers(new Bridge.SwipeConflicts(enabled));
    }

    abstract class Segment<T> {

        /**
         * 正常的插入
         *
         * @param adapterPosition 待插入的位置
         * @param item            待插入的数据
         */
        final void insert(int adapterPosition, T item) {
            if (is(adapterPosition)) {
                insertImpl(adapterPosition - positionImpl(), item);
            } else {
                throw new IndexOutOfBoundsException("Insert error,  insert position");
            }
        }

        final void insertAll(int adapterPosition, List<? extends T> items) {
            if (is(adapterPosition)) {
                insertAllImpl(adapterPosition - positionImpl(), items);
            } else {
                throw new IndexOutOfBoundsException("Insert error, check your insert position");
            }
        }

        /**
         * 插入到position之后
         *
         * @param adapterPosition 待插入的位置的前一个
         * @param item            待插入的数据
         */
        final void insertBack(int adapterPosition, T item) {
            if (is(adapterPosition)) {
                int insertPosition = adapterPosition - positionImpl() + 1;
                if (insertPosition == size()) {
                    add(item);
                } else {
                    insertImpl(insertPosition, item);
                }
            } else {
                throw new IndexOutOfBoundsException("Insert error, check your insert position");
            }
        }

        final boolean insertAllBack(int adapterPosition, List<? extends T> items) {
            if (is(adapterPosition)) {
                int insertPosition = adapterPosition - positionImpl() + 1;
                if (insertPosition == size()) {
                    addAll(items);
                } else {
                    insertAllImpl(insertPosition, items);
                }
                return true;
            } else {
                throw new IndexOutOfBoundsException("Insert error, check your insert position");
            }
        }

        /**
         * 从指定位置后删除size个数据
         *
         * @param adapterPosition 指定的位置
         * @param removeSize      删除的大小
         */
        final void removeAllBack(int adapterPosition, int removeSize) {
            if (is(adapterPosition)) {
                int removePosition = adapterPosition - positionImpl() + 1;
                for (int i = 0; i < removeSize; i++) {
                    removeImpl(removePosition);
                }
            } else {
                throw new IndexOutOfBoundsException("Remove error, check your remove position");
            }
        }

        final T get(int adapterPosition) {
            if (is(adapterPosition)) {
                return getImpl(adapterPosition - positionImpl());
            } else {
                throw new NullPointerException();
            }
        }

        final int position() {
            if (size() == 0) return -1;
            return positionImpl();
        }

        final boolean is(int adapterPosition) {
            return adapterPosition >= 0 && size() > 0 && adapterPosition - positionImpl() < size()
                    && adapterPosition - positionImpl() >= 0;
        }

        final void set(int adapterPosition, T newItem) {
            if (is(adapterPosition)) {
                setImpl(adapterPosition - positionImpl(), newItem);
            } else {
                throw new IndexOutOfBoundsException("Set error, check your set position");
            }
        }

        final void remove(int adapterPosition) {
            if (is(adapterPosition)) {
                removeImpl(adapterPosition - positionImpl());
            } else {
                throw new IndexOutOfBoundsException("Remove error, check your remove position");
            }
        }

        final void remove(T needRemove) {
            if (size() != 0) {
                removeImpl(needRemove);
            } else {
                throw new IndexOutOfBoundsException("Remove error, check your remove position");
            }
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        void swap(int fromAdapterPosition, int toAdapterPosition) {
            final List l = getAll();
            fromAdapterPosition = fromAdapterPosition - positionImpl();
            toAdapterPosition = toAdapterPosition - positionImpl();
            l.set(fromAdapterPosition, l.set(toAdapterPosition, l.get(fromAdapterPosition)));
        }

        abstract int size();

        abstract void clear();

        abstract void add(T item);

        abstract void addAll(List<? extends T> items);

        abstract List<T> getAll();

        abstract int positionImpl();

        abstract void insertImpl(int position, T item);

        abstract void insertAllImpl(int position, List<? extends T> items);

        abstract T getImpl(int position);

        abstract void setImpl(int position, T newItem);

        abstract void removeImpl(int position);

        abstract void removeImpl(T needRemove);

    }

    private class HeaderSegment extends Segment<SectionItem> {

        @Override
        int size() {
            return mHeader.size();
        }

        @Override
        void clear() {
            mHeader.clear();
        }

        @Override
        List<SectionItem> getAll() {
            return mHeader;
        }

        @Override
        void add(SectionItem item) {
            mHeader.add(item);
        }

        @Override
        void addAll(List<? extends SectionItem> items) {
            mHeader.addAll(items);
        }

        @Override
        int positionImpl() {
            return 0;
        }

        @Override
        void insertImpl(int position, SectionItem item) {
            mHeader.add(position, item);
        }

        @Override
        void insertAllImpl(int position, List<? extends SectionItem> items) {
            mHeader.addAll(position, items);
        }

        @Override
        SectionItem getImpl(int position) {
            return mHeader.get(position);
        }

        @Override
        void setImpl(int position, SectionItem newItem) {
            mHeader.set(position, newItem);
        }

        @Override
        void removeImpl(int position) {
            mHeader.remove(position);
        }

        @Override
        void removeImpl(SectionItem needRemove) {
            mHeader.remove(needRemove);
        }
    }

    private class DataSegment extends Segment<E> {

        @Override
        int size() {
            return mData.size();
        }

        @Override
        void clear() {
            mData.clear();
        }

        @Override
        void add(E item) {
            mData.add(item);
        }

        @Override
        void addAll(List<? extends E> items) {
            mData.addAll(items);
        }

        @Override
        List<E> getAll() {
            return mData;
        }

        @Override
        int positionImpl() {
            return header.size();
        }

        @Override
        void insertImpl(int position, E item) {
            mData.add(position, item);
        }

        @Override
        void insertAllImpl(int position, List<? extends E> items) {
            mData.addAll(position, items);
        }

        @Override
        E getImpl(int position) {
            return mData.get(position);
        }

        @Override
        void setImpl(int position, E newItem) {
            mData.set(position, newItem);
        }

        @Override
        void removeImpl(int position) {
            mData.remove(position);
        }

        @Override
        void removeImpl(E needRemove) {
            mData.remove(needRemove);
        }
    }

    private class FooterSegment extends Segment<SectionItem> {

        @Override
        int size() {
            return mFooter.size();
        }

        @Override
        void clear() {
            mFooter.clear();
        }

        @Override
        List<SectionItem> getAll() {
            return mFooter;
        }

        @Override
        void add(SectionItem item) {
            mFooter.add(item);
        }

        @Override
        void addAll(List<? extends SectionItem> items) {
            mFooter.addAll(items);
        }

        @Override
        int positionImpl() {
            return header.size() + data.size();
        }

        @Override
        void insertImpl(int position, SectionItem item) {
            mFooter.add(position, item);
        }

        @Override
        void insertAllImpl(int position, List<? extends SectionItem> items) {
            mFooter.addAll(position, items);
        }

        @Override
        SectionItem getImpl(int position) {
            return mFooter.get(position);
        }

        @Override
        void setImpl(int position, SectionItem newItem) {
            mFooter.set(position, newItem);
        }

        @Override
        void removeImpl(int position) {
            mFooter.remove(position);
        }

        @Override
        void removeImpl(SectionItem needRemove) {
            mFooter.remove(needRemove);
        }
    }

    private class ExtraSegment extends Segment<SectionItem> {

        @Override
        int size() {
            return mExtra.size();
        }

        @Override
        void clear() {
            mExtra.clear();
        }

        @Override
        List<SectionItem> getAll() {
            return mExtra;
        }

        @Override
        void add(SectionItem item) {
            mExtra.add(item);
        }

        @Override
        void addAll(List<? extends SectionItem> items) {
            mExtra.addAll(items);
        }

        @Override
        int positionImpl() {
            return header.size() + data.size() + footer.size();
        }

        @Override
        void insertImpl(int position, SectionItem item) {
            mExtra.add(position, item);
        }

        @Override
        void insertAllImpl(int position, List<? extends SectionItem> items) {
            mExtra.addAll(position, items);
        }

        @Override
        SectionItem getImpl(int position) {
            return mExtra.get(position);
        }

        @Override
        void setImpl(int position, SectionItem newItem) {
            mExtra.set(position, newItem);
        }

        @Override
        void removeImpl(int position) {
            mExtra.remove(position);
        }

        @Override
        void removeImpl(SectionItem needRemove) {
            mExtra.remove(needRemove);
        }
    }

}

