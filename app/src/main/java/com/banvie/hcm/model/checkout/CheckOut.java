package com.banvie.hcm.model.checkout;

import com.banvie.hcm.dialog.CheckOutDialog;

public class CheckOut {
    private String code;
    private CheckOutData data;

    public CheckOut() {

    }

    public CheckOut(String code, CheckOutData data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CheckOutData getData() {
        return data;
    }

    public void setData(CheckOutData data) {
        this.data = data;
    }

    public class CheckOutData {
        private long createdDate;
        private String createdBy;
        private long lastModifiedDate;
        private String lastModifiedBy;
        private int optCounter;
        private String id;
        private long trackingDate;
        private String userId;
        private int inTime;
        private String checkinFrom;
        private String lastAction;
        private boolean scheduleProcess;
        private String orgId;
        private int totalWorkingTime;
        private int workingDay;
        private int state;
        private int ot;
        private boolean lateCheckIn;
        private boolean earlyCheckOut;
        private double longitudeCheckIn;
        private double latitudeCheckIn;
        private String addressCheckIn;
        private boolean checkInIsInOffice;
        private boolean isSkipCheckInOutNormal;
        private boolean isHoliday;
        private boolean isWrongFormat;
        private boolean isConflictData;
        private boolean isDataNotFound;
        private boolean isDuplicateRecord;
        private boolean isSubmitedLeaveRequest;

        public CheckOutData() {

        }

        public CheckOutData(long createdDate, String createdBy, long lastModifiedDate,
                            String lastModifiedBy, int optCounter, String id, long trackingDate,
                            String userId, int inTime, String checkinFrom, String lastAction,
                            boolean scheduleProcess, String orgId, int totalWorkingTime,
                            int workingDay, int state, int ot, boolean lateCheckIn,
                            boolean earlyCheckOut, double longitudeCheckIn, double latitudeCheckIn,
                            String addressCheckIn, boolean checkInIsInOffice,
                            boolean isSkipCheckInOutNormal, boolean isHoliday, boolean isWrongFormat,
                            boolean isConflictData, boolean isDataNotFound, boolean isDuplicateRecord,
                            boolean isSubmitedLeaveRequest) {
            this.createdDate = createdDate;
            this.createdBy = createdBy;
            this.lastModifiedDate = lastModifiedDate;
            this.lastModifiedBy = lastModifiedBy;
            this.optCounter = optCounter;
            this.id = id;
            this.trackingDate = trackingDate;
            this.userId = userId;
            this.inTime = inTime;
            this.checkinFrom = checkinFrom;
            this.lastAction = lastAction;
            this.scheduleProcess = scheduleProcess;
            this.orgId = orgId;
            this.totalWorkingTime = totalWorkingTime;
            this.workingDay = workingDay;
            this.state = state;
            this.ot = ot;
            this.lateCheckIn = lateCheckIn;
            this.earlyCheckOut = earlyCheckOut;
            this.longitudeCheckIn = longitudeCheckIn;
            this.latitudeCheckIn = latitudeCheckIn;
            this.addressCheckIn = addressCheckIn;
            this.checkInIsInOffice = checkInIsInOffice;
            this.isSkipCheckInOutNormal = isSkipCheckInOutNormal;
            this.isHoliday = isHoliday;
            this.isWrongFormat = isWrongFormat;
            this.isConflictData = isConflictData;
            this.isDataNotFound = isDataNotFound;
            this.isDuplicateRecord = isDuplicateRecord;
            this.isSubmitedLeaveRequest = isSubmitedLeaveRequest;
        }

        public long getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(long createdDate) {
            this.createdDate = createdDate;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public long getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(long lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public String getLastModifiedBy() {
            return lastModifiedBy;
        }

        public void setLastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
        }

        public int getOptCounter() {
            return optCounter;
        }

        public void setOptCounter(int optCounter) {
            this.optCounter = optCounter;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTrackingDate() {
            return trackingDate;
        }

        public void setTrackingDate(long trackingDate) {
            this.trackingDate = trackingDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getInTime() {
            return inTime;
        }

        public void setInTime(int inTime) {
            this.inTime = inTime;
        }

        public String getCheckinFrom() {
            return checkinFrom;
        }

        public void setCheckinFrom(String checkinFrom) {
            this.checkinFrom = checkinFrom;
        }

        public String getLastAction() {
            return lastAction;
        }

        public void setLastAction(String lastAction) {
            this.lastAction = lastAction;
        }

        public boolean isScheduleProcess() {
            return scheduleProcess;
        }

        public void setScheduleProcess(boolean scheduleProcess) {
            this.scheduleProcess = scheduleProcess;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public int getTotalWorkingTime() {
            return totalWorkingTime;
        }

        public void setTotalWorkingTime(int totalWorkingTime) {
            this.totalWorkingTime = totalWorkingTime;
        }

        public int getWorkingDay() {
            return workingDay;
        }

        public void setWorkingDay(int workingDay) {
            this.workingDay = workingDay;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getOt() {
            return ot;
        }

        public void setOt(int ot) {
            this.ot = ot;
        }

        public boolean isLateCheckIn() {
            return lateCheckIn;
        }

        public void setLateCheckIn(boolean lateCheckIn) {
            this.lateCheckIn = lateCheckIn;
        }

        public boolean isEarlyCheckOut() {
            return earlyCheckOut;
        }

        public void setEarlyCheckOut(boolean earlyCheckOut) {
            this.earlyCheckOut = earlyCheckOut;
        }

        public double getLongitudeCheckIn() {
            return longitudeCheckIn;
        }

        public void setLongitudeCheckIn(double longitudeCheckIn) {
            this.longitudeCheckIn = longitudeCheckIn;
        }

        public double getLatitudeCheckIn() {
            return latitudeCheckIn;
        }

        public void setLatitudeCheckIn(double latitudeCheckIn) {
            this.latitudeCheckIn = latitudeCheckIn;
        }

        public String getAddressCheckIn() {
            return addressCheckIn;
        }

        public void setAddressCheckIn(String addressCheckIn) {
            this.addressCheckIn = addressCheckIn;
        }

        public boolean isCheckInIsInOffice() {
            return checkInIsInOffice;
        }

        public void setCheckInIsInOffice(boolean checkInIsInOffice) {
            this.checkInIsInOffice = checkInIsInOffice;
        }

        public boolean isSkipCheckInOutNormal() {
            return isSkipCheckInOutNormal;
        }

        public void setSkipCheckInOutNormal(boolean skipCheckInOutNormal) {
            isSkipCheckInOutNormal = skipCheckInOutNormal;
        }

        public boolean isHoliday() {
            return isHoliday;
        }

        public void setHoliday(boolean holiday) {
            isHoliday = holiday;
        }

        public boolean isWrongFormat() {
            return isWrongFormat;
        }

        public void setWrongFormat(boolean wrongFormat) {
            isWrongFormat = wrongFormat;
        }

        public boolean isConflictData() {
            return isConflictData;
        }

        public void setConflictData(boolean conflictData) {
            isConflictData = conflictData;
        }

        public boolean isDataNotFound() {
            return isDataNotFound;
        }

        public void setDataNotFound(boolean dataNotFound) {
            isDataNotFound = dataNotFound;
        }

        public boolean isDuplicateRecord() {
            return isDuplicateRecord;
        }

        public void setDuplicateRecord(boolean duplicateRecord) {
            isDuplicateRecord = duplicateRecord;
        }

        public boolean isSubmitedLeaveRequest() {
            return isSubmitedLeaveRequest;
        }

        public void setSubmitedLeaveRequest(boolean submitedLeaveRequest) {
            isSubmitedLeaveRequest = submitedLeaveRequest;
        }
    }
}
