package models;

public class Supplier {

    private int supplierId;
    private String supplierName;
    private String partName;
    private String contactNumber;
    private String supplierEmail;
    private String country;


    public Supplier() {
    }

    public Supplier(int supplierId, String supplierName, String contactPerson, String contactNumber, String supplierEmail, String country) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.partName = contactPerson;
        this.contactNumber = contactNumber;
        this.supplierEmail = supplierEmail;
        this.country = country;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactPerson() {
        return partName;
    }

    public void setContactPerson(String contactPerson) {
        this.partName = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSupplierEmail(){
        return supplierEmail;
    }
    public void setSupplierEmail(String supplierEmail){
        this.supplierEmail = supplierEmail;
    }

    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }

    @Override
    public String toString() {
        return "supplierId=" + supplierId +
                ",  Name='" + supplierName + '\'' +
                ",  PartName='" + partName + '\'' +
                ",  contactNumber='" + contactNumber + '\''+
                ",  Email='" + supplierEmail +'\''+
                ",  Country='" + country +'\'';
    }
}
