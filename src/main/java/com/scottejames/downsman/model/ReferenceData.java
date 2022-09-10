package com.scottejames.downsman.model;

import java.time.LocalDate;

public class ReferenceData {


    public static final String ACLASS = "A-Class";
    public static final String SCLASS = "S-Class";
    public static final String SCLASSWALK = "S-Class walk";
    public static final String VCLASS = "V-Class";
    public static final String ECLASS = "E-Class";
    public static final String BCLASS = "B-Class";
    public static final String OPENBIGORWASHINGTON = "Open, Bigor - Washington";
    public static final String OPENBIGNORSTEYNING = "Open, Bigor - Steyning";
    public static final String OPENPLUMPTONITFORD = "Open, Plumpton - Itford";
    public static final String OPENPLUMPTONFIRLE = "Open, Plumpton - Firle";
    public static final String OPENITFORDEASTBOURNE = "Open, Itford - Eastbourne";

    public static final String [] HIKE_CLASSES = {ACLASS,SCLASS,SCLASSWALK,VCLASS,ECLASS,BCLASS,OPENBIGORWASHINGTON,OPENBIGNORSTEYNING,OPENPLUMPTONITFORD,OPENPLUMPTONFIRLE,OPENITFORDEASTBOURNE};
    public static final LocalDate HIKE_DATE = LocalDate.of(2022,10,1);

    public static final String BANK_DETAILS = "A/C: XXX S/C: XXX";
    public static final int ENTRYCOST = 7;
}
