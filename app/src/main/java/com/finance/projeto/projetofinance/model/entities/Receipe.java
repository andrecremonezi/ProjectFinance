package com.finance.projeto.projetofinance.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Receipe implements Parcelable{
    private Long   id;
    private String description;
    private Double value;
    private String type;
    private String walletOrBank;
    public  Integer month;

    public Receipe(){
        super();
    }

    protected Receipe(Parcel imp) {
        super();
        readFromParcel(imp);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getWalletOrBank() {
        return walletOrBank;
    }

    public void setWalletOrBank(String setWalletOrBank) {
        this.walletOrBank = setWalletOrBank;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipe receipe = (Receipe) o;

        return !(id != null ? !id.equals(receipe.id) : receipe.id != null) &&
                !(description != null ? !description.equals(receipe.description) : receipe.description != null) &&
                !(value != null ? !value.equals(receipe.value) : receipe.value != null) &&
                !(type != null ? !type.equals(receipe.type) : receipe.type != null) &&
                !(walletOrBank != null ? !walletOrBank.equals(receipe.walletOrBank) : receipe.walletOrBank != null) &&
                !(month != null ? !month.equals(receipe.month) : receipe.month != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (walletOrBank != null ? walletOrBank.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        return result;
    }

    public static final Parcelable.Creator<Receipe> CREATOR = new Parcelable.Creator<Receipe>() {

        @Override
        public Receipe createFromParcel(Parcel in) {
            return new Receipe(in);
        }

        @Override
        public Receipe[] newArray(int size) {
            return new Receipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(description);
        dest.writeDouble(value);
        dest.writeString(type);
        dest.writeString(walletOrBank);
        dest.writeInt(month);
    }

    public void readFromParcel(Parcel imp) {
        id           = imp.readLong();
        id           = id == -1 ? null : id;
        description  = imp.readString();
        value        = imp.readDouble();
        type         = imp.readString();
        walletOrBank = imp.readString();
        month        = imp.readInt();
    }

}
