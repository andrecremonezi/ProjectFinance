package com.finance.projeto.projetofinance.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Expense implements Parcelable{
    private Long   id;
    private String description;
    private Double value;
    private String type;
    private Integer month;
    private String form;
    private Integer paid;

    public Expense(){
        super();
    }

    protected Expense(Parcel imp) {
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

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (id != null ? !id.equals(expense.id) : expense.id != null) return false;
        if (description != null ? !description.equals(expense.description) : expense.description != null)
            return false;
        if (value != null ? !value.equals(expense.value) : expense.value != null) return false;
        if (type != null ? !type.equals(expense.type) : expense.type != null) return false;
        if (month != null ? !month.equals(expense.month) : expense.month != null) return false;
        if (form != null ? !form.equals(expense.form) : expense.form != null) return false;
        return !(paid != null ? !paid.equals(expense.paid) : expense.paid != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (form != null ? form.hashCode() : 0);
        result = 31 * result + (paid != null ? paid.hashCode() : 0);
        return result;
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override

        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
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
        dest.writeInt(month);
        dest.writeString(form);
        dest.writeInt(paid);
    }

    public void readFromParcel(Parcel imp) {
        id           = imp.readLong();
        id           = id == -1 ? null : id;
        description  = imp.readString();
        value        = imp.readDouble();
        type         = imp.readString();
        month        = imp.readInt();
        form         = imp.readString();
        paid         = imp.readInt();
    }
}
