package com.finance.projeto.projetofinance.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
    private Long id;
    private String name;
    private Double limitValue;


    public Card() {
        super();
    }

    protected Card(Parcel imp) {
        super();
        readFromParcel(imp);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(Double limitValue) {
        this.limitValue = limitValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return !(id != null ? !id.equals(card.id) : card.id != null) && !(name != null ? !name.equals(card.name) : card.name != null) && !(limitValue != null ? !limitValue.equals(card.limitValue) : card.limitValue != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (limitValue != null ? limitValue.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeDouble(limitValue);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", limitValue=" + limitValue +
                '}';
    }

    public void readFromParcel(Parcel imp) {
        id           = imp.readLong();
        id           = id == -1 ? null : id;
        name         = imp.readString();
        limitValue   = imp.readDouble();
    }
}
