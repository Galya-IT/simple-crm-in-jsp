package com.company.crm.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.company.crm.exceptions.ClientPropertyValidationException;

public class Client {
    private int id = 0;
    private String name = null;
    private String location = null;
    private String notes = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private Date contractDate = null;
    private String logoName = null;
    private boolean isDeleted = false;

    public Client(String name) throws ClientPropertyValidationException {
        this.setName(name);
    }

    public Client(String name, String location, String notes, String contractDate, String logoImageName, boolean isDeleted)
            throws ClientPropertyValidationException {
        this(name);

        this.setLocation(location);
        this.setNotes(notes);
        this.setContractDate(contractDate);
        this.setLogoFileName(logoImageName);
        this.setDeleted(isDeleted);
    }

    public Client(String name, String location, String notes, String contractDate, String logoImageName, boolean isDeleted, int id)
            throws ClientPropertyValidationException {
        this(name, location, notes, contractDate, logoImageName, isDeleted);

        this.id = id;
    }

    public void setId(String idAsString) throws NumberFormatException {
        int id = Integer.parseInt(idAsString);
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) throws ClientPropertyValidationException {
        if (name.length() <= 50) {
            this.name = name;
        } else {
            throw new ClientPropertyValidationException("The value of name is not correct.");
        }
    }

    public void setLocation(String location) throws ClientPropertyValidationException {
        if (location.length() <= 50) {
            this.location = location;
        } else {
            throw new ClientPropertyValidationException("The value of location is not correct.");
        }
    }

    public void setNotes(String notes) throws ClientPropertyValidationException {
        if (notes.length() <= 100_000) {
            this.notes = notes;
        } else {
            throw new ClientPropertyValidationException("Notes are too long.");
        }
    }

    public void setContractDate(String contractDate) throws ClientPropertyValidationException {
        try {
            SimpleDateFormat dateFormat = null;

            if (contractDate.indexOf("-") > -1) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            } else if (contractDate.indexOf(".") > -1) {
                dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            } else {
                throw new ClientPropertyValidationException("The format of date is not correct.");
            }

            Date parsedDate = dateFormat.parse(contractDate);
            this.contractDate = parsedDate;
        } catch (ParseException e) {
            throw new ClientPropertyValidationException("The value of date is not correct.");
        }
    }

    public void setLogoFileName(String logoNameName) {
        this.logoName = logoNameName;
    }

    private void setDeleted(boolean value) {
        this.isDeleted = value;
    }

    public int getId() {
        if (this.id == 0) {
        }
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getNotes() {
        return this.notes;
    }

    public String getContractDate() {
        String formattedDateString = this.dateFormat.format(this.contractDate);
        return formattedDateString;
    }

    public String getContractDate(String datePattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        String formattedDate = dateFormat.format(this.contractDate);

        return formattedDate;
    }

    public String getLogoFileName() {
        return this.logoName;
    }

    public boolean checkIfDeleted() {
        return this.isDeleted;
    }

    @Override
    public String toString() {
        // TO DO: Optimization, for loop (each field)
        StringBuilder resultBuilder = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        if (this.getId() > 0) {
            String id = String.format("Id: %s, ", this.id);
            resultBuilder.append(id);
        }

        if (this.name != null) {
            String name = String.format("Name: %s, ", this.name);
            resultBuilder.append(name);
        }

        if (this.location != null) {
            String location = String.format("Location: %s, ", this.location);
            resultBuilder.append(location);
        }

        if (this.notes != null) {
            String notes = String.format("Notes: %s, ", this.notes);
            resultBuilder.append(notes);
        }

        if (this.contractDate != null) {
            String contractDate = String.format("ContractDate: %s, ", this.contractDate);
            resultBuilder.append(contractDate);
        }

        if (this.logoName != null) {
            String logoName = String.format("Logo File Name: %s, ", this.logoName);
            resultBuilder.append(logoName);
        }

        String isDeleted = "Is Client Deleted: " + this.isDeleted;
        resultBuilder.append(isDeleted);
        resultBuilder.append(newLine);

        return resultBuilder.toString();
    }
}
