package bismsoft.benfalexparcel.DataProvider;

public class ScheduleAServiceDP {
    String userId,orderKey,serviceType,firstName,lastName,email,phone,pickupAddress,deliveryAddress,date,time,comments,orderStatus,pictureLink,pickupLat,pickupLog,deliveryLat,deliveryLog,price;
    public ScheduleAServiceDP() {
    }

    public ScheduleAServiceDP(String userId,String orderKey, String serviceType, String firstName, String lastName,
                              String email, String phone, String pickupAddress, String deliveryAddress,
                              String date, String time, String comments,String orderStatus,String pickupLat,String pickupLog,String deliveryLat,String deliveryLog,String price) {
        this.userId=userId;
        this.orderKey = orderKey;
        this.serviceType = serviceType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.pickupAddress = pickupAddress;
        this.deliveryAddress = deliveryAddress;
        this.date = date;
        this.time = time;
        this.comments = comments;
        this.orderStatus=orderStatus;
        this.pictureLink="https://firebasestorage.googleapis.com/v0/b/xcel-ebff1.appspot.com/o/pending.png?alt=media&token=6b4c9156-ede2-4481-8f42-792414c506d2";
        this.pickupLat=pickupLat;
        this.pickupLog=pickupLog;
        this.deliveryLat=deliveryLat;
        this.deliveryLog=deliveryLog;
        this.price=price;

    }

    public String getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(String pickupLat) {
        this.pickupLat = pickupLat;
    }

    public String getPickupLog() {
        return pickupLog;
    }

    public void setPickupLog(String pickupLog) {
        this.pickupLog = pickupLog;
    }

    public String getDeliveryLat() {
        return deliveryLat;
    }

    public void setDeliveryLat(String deliveryLat) {
        this.deliveryLat = deliveryLat;
    }

    public String getDeliveryLog() {
        return deliveryLog;
    }

    public void setDeliveryLog(String deliveryLog) {
        this.deliveryLog = deliveryLog;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
