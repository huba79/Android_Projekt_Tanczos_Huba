package com.example.feleves_projekt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Reservations for a selected stage on a certain interval
 */


public class Reservations implements Serializable {
    @SerializedName("id")
    @Expose
    private Long id = null;

    @SerializedName("pondId")
    @Expose
    private Long pondId = null;

    @SerializedName("stageId")
    @Expose
    private Long stageId = null;

    @SerializedName("userId")
    @Expose
    private Long userId = null;

    @SerializedName("status")
    @Expose
    private Integer status = null;

    @SerializedName("dateFrom")
    @Expose
    private OffsetDateTime dateFrom = null;

    @SerializedName("dateTo")
    @Expose
    private OffsetDateTime dateTo = null;

    public Reservations(Long pondId, Long stageId, Long userId, Integer status, OffsetDateTime dateFrom, OffsetDateTime dateTo) {
        this.pondId = pondId;
        this.stageId = stageId;
        this.userId = userId;
        this.status = status;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPondId() {
        return pondId;
    }

    public void setPondId(Long pondId) {
        this.pondId = pondId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OffsetDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(OffsetDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public OffsetDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(OffsetDateTime dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservations reservations = (Reservations) o;
        return Objects.equals(this.id, reservations.id) &&
                Objects.equals(this.pondId, reservations.pondId) &&
                Objects.equals(this.stageId, reservations.stageId) &&
                Objects.equals(this.userId, reservations.userId) &&
                Objects.equals(this.status, reservations.status) &&
                Objects.equals(this.dateFrom, reservations.dateFrom) &&
                Objects.equals(this.dateTo, reservations.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pondId, stageId, userId, status, dateFrom, dateTo);
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("class Reservations {\n");
//
//        sb.append("    id: ").append(toIndentedString(id)).append("\n");
//        sb.append("    pondId: ").append(toIndentedString(pondId)).append("\n");
//        sb.append("    stageId: ").append(toIndentedString(stageId)).append("\n");
//        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
//        sb.append("    status: ").append(toIndentedString(status)).append("\n");
//        sb.append("    dateFrom: ").append(toIndentedString(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(dateFrom) ) ).append("\n");
//        sb.append("    dateTo: ").append(toIndentedString(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(dateFrom))).append("\n");
//        sb.append("}");
//        return sb.toString();
//    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
