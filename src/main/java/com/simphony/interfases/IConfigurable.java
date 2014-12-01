package com.simphony.interfases;

import java.text.SimpleDateFormat;

public interface IConfigurable {

    final SimpleDateFormat _SDFCHART = new SimpleDateFormat("yyyyMM");
    final SimpleDateFormat _SDFCHARTLEGEND = new SimpleDateFormat("MMM yyyy");
    final SimpleDateFormat _SDF = new SimpleDateFormat("yyyyMMdd");
    final SimpleDateFormat _SHF = new SimpleDateFormat("hhmmss");
    //final SimpleDateFormat _SHM = new SimpleDateFormat("k:mm");
    final SimpleDateFormat _SHM = new SimpleDateFormat("HH:mm");
    final SimpleDateFormat _SHHM = new SimpleDateFormat("kk:mm");
    final SimpleDateFormat _SGF = new SimpleDateFormat("yyyyMMdd hhmmss");
    final SimpleDateFormat _DMA = new SimpleDateFormat("dd-MM-yyyy");
    final SimpleDateFormat _SDT = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    final String _BLANK = "";
    final String _ROOT_IMG = "ROOTIMG";

    final String _LOCAL = "L";
    final String _FOREING = "P";

    //Global status
    final String _ENABLED = "A";
    final String _DISABLE = "B";

    //Tipos usuario
    final String _USR_TYPE_ADMIN = "AD";
    final String _USR_TYPE_VENDOR = "VD";

    //Acciones 
    final String _ADD = "A";
    final String _MODIFY = "M";
    final String _RETURN = "R";

    final String _GUIDE_TYPE_OPEN = "OP";
    final String _GUIDE_TYPE_CLOSED = "CL";
    final String _GUIDE_TYPE_CANCEL = "CA";

    final String _SALE_TYPE_PUBLIC = "P";
    final String _SALE_TYPE_ASSOCIATE = "A";
    
    final String _SALE_SINGLE_TRAVEL = "S";
    final String _SALE_ROUNDED_TRAVEL = "R";

    // Simple Yes or NO
    final String _YES = "S";
    final String _NO = "N";
    
    final Double _RETIREE_DISCOUNT = 0.5;

    //Casos de navegación general
    final String _NAV_TO_INDEX = "toindex";
    
    final Integer _DEFAULT_SEAT_NUMBER = 40;
    
    final String _PASSENGER = "P";
    final String _RETIREE = "J";
    final String _NOMINAL_REFERENCE = "Nomina";
    
    //Status for sale
    final String _CANCELLED = "C";
    final String _SALED = "V";
    final String _RESERVATED = "R";
    final String _PENDING = "P";
    final String _ROUNDED = "SR";

    final String _REPORT_PATH = "C:\\JasperReportsSimphony\\";
    final String _REPORT_PDF = "application/pdf";
    final String _REPORT_HTML = "text/html";
    
    final String _TO_BACK = "B";
    final String _TO_ORIGIN = "O";
}
