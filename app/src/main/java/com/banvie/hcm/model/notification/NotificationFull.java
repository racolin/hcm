package com.banvie.hcm.model.notification;

import java.util.List;

public class NotificationFull {
    private Data1 data;
    private int unReadCount;
    private boolean turnOff;

    public NotificationFull() {

    }

    public NotificationFull(Data1 data, int unReadCount, boolean turnOff) {
        this.data = data;
        this.unReadCount = unReadCount;
        this.turnOff = turnOff;
    }

    public class Data1 {
        private String code;
        private Data2 data;

        public Data1() {

        }

        public Data1(String code, Data2 data) {
            this.code = code;
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Data2 getData() {
            return data;
        }

        public void setData(Data2 data) {
            this.data = data;
        }

        public class Data2 {
            private List<Notification> items;
            private int page;
            private int size;
            private int totalItems;
            private int totalPages;
            private int totalElements;
            private boolean hasNext;
            private boolean hasPrevious;

            public Data2() {

            }

            public Data2(List<Notification> items, int page, int size, int totalItems,
                         int totalPages, int totalElements, boolean hasNext, boolean hasPrevious) {
                this.items = items;
                this.page = page;
                this.size = size;
                this.totalItems = totalItems;
                this.totalPages = totalPages;
                this.totalElements = totalElements;
                this.hasNext = hasNext;
                this.hasPrevious = hasPrevious;
            }

            public List<Notification> getItems() {
                return items;
            }

            public void setItems(List<Notification> items) {
                this.items = items;
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getTotalItems() {
                return totalItems;
            }

            public void setTotalItems(int totalItems) {
                this.totalItems = totalItems;
            }

            public int getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public int getTotalElements() {
                return totalElements;
            }

            public void setTotalElements(int totalElements) {
                this.totalElements = totalElements;
            }

            public boolean isHasNext() {
                return hasNext;
            }

            public void setHasNext(boolean hasNext) {
                this.hasNext = hasNext;
            }

            public boolean isHasPrevious() {
                return hasPrevious;
            }

            public void setHasPrevious(boolean hasPrevious) {
                this.hasPrevious = hasPrevious;
            }
        }
    }

    public Data1 getData() {
        return data;
    }

    public void setData(Data1 data) {
        this.data = data;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public boolean isTurnOff() {
        return turnOff;
    }

    public void setTurnOff(boolean turnOff) {
        this.turnOff = turnOff;
    }
}
