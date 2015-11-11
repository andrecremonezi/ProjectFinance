package com.finance.projeto.projetofinance.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Goal implements Parcelable{
    private Long   id;
    private Double value;
    private String type;

    public Goal() {
        super();
    }

    protected Goal(Parcel imp) {
        super();
        readFromParcel(imp);
    }

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goal goal = (Goal) o;

        return !(id != null ? !id.equals(goal.id) : goal.id != null) &&
                !(value != null ? !value.equals(goal.value) : goal.value != null) &&
                !(type != null ? !type.equals(goal.type) : goal.type != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", value=" + value +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeDouble(value);
        dest.writeString(type);
    }

    public void readFromParcel(Parcel imp) {
        id           = imp.readLong();
        id           = id == -1 ? null : id;
        value        = imp.readDouble();
        type         = imp.readString();
    }

}
